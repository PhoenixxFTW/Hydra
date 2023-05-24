import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXTreeViewPath;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

public class TreeViewDemo extends Application {

    private static final String SALES_DEPARTMENT = "Sales Depa";
    private static final String IT_SUPPORT = "IT adsfasdfasfdasdfsadfsadfasdf Support";
    private static final String ACCOUNTS_DEPARTMENT = "Accounts Department";

    private final List<Employee> employees = asList(new Employee("Ethan Williams", SALES_DEPARTMENT),
            new Employee("Emma Jones", SALES_DEPARTMENT),
            new Employee("Michael Brownasdfasdfsadfs", SALES_DEPARTMENT),
            new Employee("Anna Black", SALES_DEPARTMENT),
            new Employee("Roer York", SALES_DEPARTMENT),
            new Employee("Susan Collins", SALES_DEPARTMENT),
            new Employee("Miaaaake Graham", IT_SUPPORT),
            new Employee("Judy Mayer", IT_SUPPORT),
            new Employee("Gregy Smith", IT_SUPPORT),
            new Employee("Jacob Smith", ACCOUNTS_DEPARTMENT),
            new Employee("Isabella Johnson", ACCOUNTS_DEPARTMENT));

    private final FilterableTreeItem<String> rootNode = new FilterableTreeItem<>("MyCompany Human Resources");//, rootIcon);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        rootNode.setExpanded(true);
        final JFXTreeView<String> treeView = new JFXTreeView<>(rootNode);
        for (Employee employee : employees) {
            FilterableTreeItem<String> empLeaf = new FilterableTreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())) {
                    ((FilterableTreeItem)depNode).getInternalChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }

            if (!found) {
                FilterableTreeItem<String> depNode = new FilterableTreeItem<>(employee.getDepartment());
                rootNode.getInternalChildren().add(depNode);
                depNode.getInternalChildren().add(empLeaf);
            }
        }


        stage.setTitle("Tree View Sample");
        VBox box = new VBox();
        final Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);

        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new TreeCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);

                            if (getTreeItem().isLeaf()) {
                                HBox hBox = new HBox();
                                Line line = new Line(0, 10, 10, 10); // Adjust as necessary
                                line.setStroke(Color.GRAY);
                                hBox.getChildren().add(line);
                                hBox.getChildren().add(new Label(item));
                                setGraphic(hBox);
                            } else {
                                setGraphic(new Label(item));
                            }
                        }
                    }
                };
            }
        });


        treeView.setShowRoot(false);

        TextField filterField = new JFXTextField();

        rootNode.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            if (filterField.getText() == null || filterField.getText().isEmpty())
                return null;
            return TreeItemPredicate.create(actor -> actor.toString().contains(filterField.getText()));
        }, filterField.textProperty()));


        box.getChildren().addAll(new JFXTreeViewPath(treeView), treeView, filterField);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        stage.setScene(scene);
        stage.show();

    }

    @FunctionalInterface
    public interface TreeItemPredicate<T> {

        boolean test(TreeItem<T> parent, T value);

        static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
            return (parent, value) -> predicate.test(value);
        }

    }

    public class FilterableTreeItem<T> extends TreeItem<T> {
        final private ObservableList<TreeItem<T>> sourceList;
        private FilteredList<TreeItem<T>> filteredList;
        private ObjectProperty<TreeItemPredicate<T>> predicate = new SimpleObjectProperty<>();


        public FilterableTreeItem(T value) {
            super(value);
            this.sourceList = FXCollections.observableArrayList();
            this.filteredList = new FilteredList<>(this.sourceList);
            this.filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                return child -> {
                    // Set the predicate of child items to force filtering
                    if (child instanceof FilterableTreeItem) {
                        FilterableTreeItem<T> filterableChild = (FilterableTreeItem<T>) child;
                        filterableChild.setPredicate(this.predicate.get());
                    }
                    // If there is no predicate, keep this tree item
                    if (this.predicate.get() == null)
                        return true;
                    // If there are children, keep this tree item
                    if (child.getChildren().size() > 0)
                        return true;
                    // Otherwise ask the TreeItemPredicate
                    return this.predicate.get().test(this, child.getValue());
                };
            }, this.predicate));

            setHiddenFieldChildren(this.filteredList);
        }

        protected void setHiddenFieldChildren(ObservableList<TreeItem<T>> list) {
            try {
                Field childrenField = TreeItem.class.getDeclaredField("children"); //$NON-NLS-1$
                childrenField.setAccessible(true);
                childrenField.set(this, list);

                Field declaredField = TreeItem.class.getDeclaredField("childrenListener"); //$NON-NLS-1$
                declaredField.setAccessible(true);
                list.addListener((ListChangeListener<? super TreeItem<T>>) declaredField.get(this));
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Could not set TreeItem.children", e); //$NON-NLS-1$
            }
        }

        public ObservableList<TreeItem<T>> getInternalChildren() {
            return this.sourceList;
        }

        public void setPredicate(TreeItemPredicate<T> predicate) {
            this.predicate.set(predicate);
        }

        public TreeItemPredicate getPredicate() {
            return predicate.get();
        }

        public ObjectProperty<TreeItemPredicate<T>> predicateProperty() {
            return predicate;
        }
    }


    public static class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String firstName) {
            name.set(firstName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(String firstName) {
            department.set(firstName);
        }
    }
}
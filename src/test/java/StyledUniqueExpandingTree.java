import javafx.application.Application;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// https://stackoverflow.com/questions/30684308/javafx-treeview-css
public class StyledUniqueExpandingTree extends Application {

    @Override
    public void start(Stage primaryStage) {
        TreeView<String> tree = new TreeView<>();
        tree.setShowRoot(false);
        TreeItem<String> root = new TreeItem<>("");
        tree.setRoot(root);

        ChangeListener<Boolean> expandedListener = (obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                ReadOnlyProperty<?> expandedProperty = (ReadOnlyProperty<?>) obs ;
                Object itemThatWasJustExpanded = expandedProperty.getBean();
                for (TreeItem<String> item : tree.getRoot().getChildren()) {
                    if (item != itemThatWasJustExpanded) {
                        item.setExpanded(false);
                    }
                }
            }
        };

        for (int i=1; i<=4; i++) {
            TreeItem<String> item = new TreeItem<>("Top level "+i);
            item.expandedProperty().addListener(expandedListener);
            root.getChildren().add(item);
            for (int j=1; j<=4; j++) {
                TreeItem<String> subItem = new TreeItem<>("Sub item "+i+":"+j);
                item.getChildren().add(subItem);
            }
        }

        PseudoClass subElementPseudoClass = PseudoClass.getPseudoClass("sub-tree-item");

        tree.setCellFactory(tv -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisclosureNode(null);

                    if (empty) {
                        setText("");
                        setGraphic(null);
                    } else {
                        setText(item); // appropriate text for item
                    }
                }

            };
            cell.treeItemProperty().addListener((obs, oldTreeItem, newTreeItem) -> {
                cell.pseudoClassStateChanged(subElementPseudoClass,
                        newTreeItem != null && newTreeItem.getParent() != cell.getTreeView().getRoot());
            });
            return cell ;
        });

        BorderPane uiRoot = new BorderPane(tree);
        Scene scene = new Scene(uiRoot, 250, 400);
        scene.getStylesheets().add("styled-unique-expanded-tree.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
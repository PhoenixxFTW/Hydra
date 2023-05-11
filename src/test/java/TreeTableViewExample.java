import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TreeTableViewExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        JFXTreeTableView treeTableView = new JFXTreeTableView<>();

        // Create columns
        JFXTreeTableColumn<QueryObj, String> keyColumn = new JFXTreeTableColumn<>("Key");
        keyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<QueryObj, String> param) ->
                param.getValue().getValue().keyProperty());

        JFXTreeTableColumn<QueryObj, String> valueColumn = new JFXTreeTableColumn<>("Value");
        valueColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<QueryObj, String> param) ->
                param.getValue().getValue().valProperty());

        JFXTreeTableColumn<QueryObj, Boolean> enabledColumn = new JFXTreeTableColumn<>("Enabled");
        enabledColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<QueryObj, Boolean> param) ->
                param.getValue().getValue().enabledProperty().asObject());

        // Set root and columns
        treeTableView.getColumns().addAll(keyColumn, valueColumn, enabledColumn);

        // Create root item
        TreeItem<QueryObj> root = new TreeItem<>();

        // Create QueryObj objects
        QueryObj obj1 = new QueryObj("Key 1", "Value 1");
        obj1.setEnabled(true);

        QueryObj obj2 = new QueryObj("Key 2", "Value 2");
        obj2.setEnabled(false);

        // Add QueryObj objects to the root item
        root.getChildren().add(new TreeItem<>(obj1));
        root.getChildren().add(new TreeItem<>(obj2));

        // Set root item
        treeTableView.setRoot(root);

        // Create and set up the scene
        StackPane rootPane = new StackPane(treeTableView);
        Scene scene = new Scene(rootPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class QueryObj {
        private final String key;
        private String val;
        private boolean enabled;

        public QueryObj(String key, String val) {
            this.key = key;
            this.val = val;
        }

        public StringProperty keyProperty() {
            return new SimpleStringProperty(key);
        }

        public StringProperty valProperty() {
            return new SimpleStringProperty(val);
        }

        public BooleanProperty enabledProperty() {
            return new SimpleBooleanProperty(enabled);
        }

        public String getKey() {
            return key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}

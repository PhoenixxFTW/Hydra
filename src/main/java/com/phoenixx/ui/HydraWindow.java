package com.phoenixx.ui;

import com.phoenixx.ui.controllers.HydraWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:17 AM [19-04-2023]
 */
//TODO Rename this to Vugen Script selector or something
public class HydraWindow extends Application {
    private double xOffset = 0.0D, yOffset = 0.0D;

    private Boolean resizebottom = false;
    private double dx;
    private double dy;

    /*public static void main(String[] args) throws IOException {
        launch(args);
    }*/

    public HydraWindow() {
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/hydra/fxml/MainClient.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        HydraWindowController hydraWindowController = loader.getController();
        hydraWindowController.primaryStage = primaryStage;

        primaryStage.setScene(new Scene(root));
        //primaryStage.setTitle("Hydra");
        //primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.getIcons().add(new Image("/hydra/fxml/images/Logo_Dark.png"));
        primaryStage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });

        // TODO Implement the other corners as well
        // Used to change the cursor when the user hovers over the corners
        root.setOnMouseMoved(event -> {
            if (event.getX() > primaryStage.getWidth() - 10 && event.getX() < primaryStage.getWidth() + 10 && event.getY() > primaryStage.getHeight() - 10 && event.getY() < primaryStage.getHeight() + 10) {
                root.setCursor(Cursor.NW_RESIZE);
            } else {
                root.setCursor(null);
            }
        });

        // This allows us to scale the window from the bottom
        // https://stackoverflow.com/a/28215249
        root.setOnMousePressed(event -> {
            if (event.getX() > primaryStage.getWidth() - 10 && event.getX() < primaryStage.getWidth() + 10 && event.getY() > primaryStage.getHeight() - 10 && event.getY() < primaryStage.getHeight() + 10) {
                resizebottom = true;
                dx = primaryStage.getWidth() - event.getX();
                dy = primaryStage.getHeight() - event.getY();

                root.setCursor(Cursor.NW_RESIZE);
            } else {
                resizebottom = false;
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();

                root.setCursor(null);
            }
        });

        root.setOnMouseReleased(event -> {
            if(resizebottom) {
                root.setCursor(null);
            }
        });

        root.setOnMouseDragged(event -> {
            if (!resizebottom) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            } else {
                primaryStage.setWidth(event.getX() + dx);
                primaryStage.setHeight(event.getY() + dy);
            }
        });
    }
}

package com.phoenixx.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
    }
}

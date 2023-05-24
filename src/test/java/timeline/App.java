package timeline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 * <a href="https://github.com/FDelporte/JavaOnRaspberryPi/blob/master/Chapter_04_Java/javafx-timeline/src/main/java/be/webtechie/timeline/App.java">...</a>
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        int width = 900;
        int height = 500;

        Scene scene = new Scene(new JavaTimeline(width, height, 10, DataSet.JAVA_RELEASES), width + 50, height);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
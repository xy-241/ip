package chin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tutorial Part 1: minimal JavaFX window that shows a greeting label.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label hello = new Label("Hello from Chin!");
        Scene scene = new Scene(new StackPane(hello), 400, 240);
        stage.setTitle("Chin");
        stage.setScene(scene);
        stage.show();
    }
}

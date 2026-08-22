package chin;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Tutorial Part 2: add layout scaffolding with a scrollable message area,
 * a text input field, and a send button. Behaviour comes in Part 3.
 */
public class Main extends Application {
    private final VBox messages = new VBox(6);
    private final ScrollPane scroll = new ScrollPane(messages);
    private final TextField input = new TextField();
    private final Button send = new Button("Send");

    @Override
    public void start(Stage stage) {
        scroll.setFitToWidth(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        HBox bottom = new HBox(6, input, send);
        HBox.setHgrow(input, Priority.ALWAYS);
        bottom.setAlignment(Pos.CENTER);
        VBox root = new VBox(6, scroll, bottom);
        messages.getChildren().add(new Label("Chin: Hello! What can I do for you?"));

        Scene scene = new Scene(root, 400, 600);
        stage.setTitle("Chin");
        stage.setScene(scene);
        stage.show();
    }
}

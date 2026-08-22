package chin;

import java.nio.file.Path;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Tutorial Part 4: prettier chat with padded dialog boxes and coloured avatars.
 */
public class Main extends Application {
    private final Chin chin = new Chin(Path.of("data", "chin.txt"));
    private final VBox messages = new VBox(8);
    private final ScrollPane scroll = new ScrollPane(messages);
    private final TextField input = new TextField();
    private final Button send = new Button("Send");

    @Override
    public void start(Stage stage) {
        messages.setPadding(new Insets(10));
        messages.setStyle("-fx-background-color: #fafafa;");
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        input.setPromptText("Type a message...");
        send.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 6;");

        HBox bottom = new HBox(8, input, send);
        HBox.setHgrow(input, Priority.ALWAYS);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(8));

        VBox root = new VBox(scroll, bottom);
        messages.getChildren().add(DialogBox.forChin("Hello! I'm Chin. What can I do for you?"));

        send.setOnAction(e -> handleUserInput());
        input.setOnAction(e -> handleUserInput());

        Scene scene = new Scene(root, 420, 620);
        stage.setTitle("Chin");
        stage.setScene(scene);
        stage.show();
    }

    private void handleUserInput() {
        String text = input.getText();
        if (text.isBlank()) {
            return;
        }
        messages.getChildren().add(DialogBox.forUser(text));
        messages.getChildren().add(DialogBox.forChin(chin.getResponse(text)));
        input.clear();
        scroll.setVvalue(1.0);
    }
}

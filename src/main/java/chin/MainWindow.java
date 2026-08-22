package chin;

import java.nio.file.Path;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML controller for the chat window.
 */
public class MainWindow {
    @FXML private ScrollPane scroll;
    @FXML private VBox messages;
    @FXML private TextField input;

    private final Chin chin = new Chin(Path.of("data", "chin.txt"));

    @FXML
    public void initialize() {
        messages.getChildren().add(DialogBox.forChin("Hello! I'm Chin. What can I do for you?"));
    }

    @FXML
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

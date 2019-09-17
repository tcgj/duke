package daming.ui.javafx;

import daming.Daming;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Daming daming;

    private Image userImage = new Image(getClass().getResourceAsStream("/images/DaYT.png"));
    private Image damingImage = new Image(getClass().getResourceAsStream("/images/DaKM.png"));

    /**
     * Creates a new MainWindow for the javafx program.
     */
    public MainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the MainWindow instance.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getDamingDialog("Hello! I'm DA-MING\nWhat can I do for you?", damingImage)
        );
    }

    /**
     * Sets the Daming instance for this window.
     *
     * @param d the Daming instance to be set.
     */
    public void setDaming(Daming d) {
        daming = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Daming's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = daming.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDamingDialog(response, damingImage)
        );
        if (response.contains("Bye")) {
            Platform.exit();
        }
        userInput.clear();
    }
}
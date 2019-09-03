package duke;

import duke.command.Command;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.javafx.DialogBox;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.SimpleCliUi;
import duke.ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents a task list chatbot named Duke.
 *
 * @author Terence Chong Guang Jun
 */
public class Duke extends Application {
    /**
     * Specifies the date format used by <code>Duke</code> for receiving date input.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy HHmm");

    /**
     * Specifies that <code>Duke</code> should continue to read the next command.
     */
    public static final int CODE_CONTINUE = 0;

    /**
     * Specifies that <code>Duke</code> should say bye and exit.
     */
    public static final int CODE_EXIT = 1;

    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Creates a new <code>Duke</code> instance with a default filepath
     * set to data/duke.txt within the current working directory.
     */
    public Duke() {
        this("data/duke.txt");
    }

    /**
     * Creates a new <code>Duke</code> instance. The specified file path is used in
     * loading and saving the <code>TaskList</code> for this <code>Duke</code>.
     *
     * @param filePath Specifies the path where the <code>TaskList</code> is loaded from.
     */
    public Duke(String filePath) {
        this(new SimpleCliUi(), new Storage(filePath));
    }

    /**
     * Creates a new <code>Duke</code> instance with the given <code>Ui</code> and <code>Storage</code>.
     *
     * @param ui the ui for this <code>Duke</code>.
     *           Reads and prints replies to the user.
     * @param storage the storage for this <code>Duke</code>.
     *                Reads and writes the <code>TaskList</code> to file.
     */
    public Duke(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
    }

    private int executeMainFlow() throws IOException {
        String input = ui.getMessage();
        if (input == null) {
            return CODE_EXIT;
        }

        try {
            Command command = Parser.parse(input);
            return command.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.sendMessage(e.getMessage());
        }
        return CODE_CONTINUE;
    }

    /**
     * Starts up Duke. Only stops when a <code>bye</code> command is read
     * or when an <code>IOException</code> is thrown.
     */
    public void run() {
        try {
            taskList = storage.load();

            ui.sendMessage("Hello! I'm Duke",
                    "What can I do for you?");
            int returnCode = CODE_CONTINUE;
            while (returnCode != CODE_EXIT) {
                returnCode = executeMainFlow();
            }
            storage.save(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.close();
    }

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();

        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(this.getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
                new DialogBox(userText, new ImageView(user)),
                new DialogBox(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    private String getResponse(String input) {
        return "Duke heard: " + input;
    }

    public static void main(String[] args) {
        String path = "data/duke.txt";
        if (args.length > 0) {
            path = args[0];
        }
        Duke duke = new Duke(path);
        duke.run();
    }
}

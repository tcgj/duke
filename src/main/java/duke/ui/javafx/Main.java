package duke.ui.javafx;

import duke.Duke;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainWindow mw = new MainWindow();
        Scene scene = new Scene(mw);
        Duke duke = new Duke();

        mw.setDuke(duke);
        stage.setScene(scene);
        stage.show();
    }
}
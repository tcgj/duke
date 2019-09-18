package daming.ui.javafx;

import daming.Daming;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A GUI for Daming using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainWindow mw = new MainWindow();
        Scene scene = new Scene(mw);
        Daming daming = new Daming();

        mw.setDaming(daming);
        stage.setMinHeight(620.0);
        stage.setMinWidth(400.0);
        stage.setScene(scene);
        stage.setTitle("DA-MING");
        stage.show();
    }
}
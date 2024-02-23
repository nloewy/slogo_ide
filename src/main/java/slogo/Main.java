package slogo;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {

    public static final String DATA_FILE_EXTENSION = "*.slogo";
    // default to start in the data folder to make it easy on the user to find
    public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
    // internal configuration file

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Controller.openNewSession();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

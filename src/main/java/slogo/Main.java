package slogo;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    // default to start in the data folder to make it easy on the user to find
    public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
    // internal configuration file
    public static final String DATA_FILE_EXTENSION = "*.slogo";
    // default language for application
    public static final String DEFAULT_LANGUAGE = "English";

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        stage.setTitle("SLogo");

        try {
            Controller controller = new Controller(stage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        try {
//            controller.run();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

    }
}

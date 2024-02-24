package slogo;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("SLogo");

        Controller controller = new Controller(stage);
//        try {
//            controller.run();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}

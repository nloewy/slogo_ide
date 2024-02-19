package slogo;

import javafx.application.Application;
import javafx.stage.Stage;
import slogo.view.View;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        View view = new View(stage);
        view.run();
    } 
}

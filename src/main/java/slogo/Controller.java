package slogo;

import javafx.stage.Stage;
import slogo.view.View;

import java.io.FileNotFoundException;

public class Controller {
    private static Stage stage;
    private final View view;

    public Controller(Stage stage) {
        Controller.stage = stage;
        view = new View(stage);
    }

    //Wil require an XMLFile object parameter
    //This will initialize this window's view object as necessary.
    public static void openNewSession() throws FileNotFoundException {
        Stage newStage = new Stage();
        View view = new View(newStage);
        view.run();
    }
}

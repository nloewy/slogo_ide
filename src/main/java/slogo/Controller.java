package slogo;

import javafx.stage.Stage;
import slogo.model.api.TurtleRecord;
import slogo.view.View;

import java.io.FileNotFoundException;

public class Controller {
    private static Stage stage;
    private final View view;

    public Controller(Stage stage) {
        Controller.stage = stage;
        view = new View(stage);
    }

    /*
     * Willrequire an XMLFile object parameter
     * This will initialize this window's view object as necessary.
     * TODO Backend has to interact with this view object
     * to access the API for one IDE session
     */
    public static void openNewSession() throws FileNotFoundException {
        Stage newStage = new Stage();
        View view = new View(newStage);
        view.run();
    }
}

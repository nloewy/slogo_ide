package slogo;

import javafx.stage.Stage;
import slogo.view.View;

public class Controller {

    private Stage stage;
    private View view;

    public Controller(Stage stage) {
        this.stage = stage;
        view = new View(stage);
    }

    public void run() {
        view.run();
    }
}

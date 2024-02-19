package slogo;

import javafx.stage.Stage;
import slogo.view.View;

public class Controller {

    Stage mStage;
    View mView;

    public Controller(Stage stage) {
        mStage = stage;
        mView = new View(stage);
    }

    public void run() {
        mView.run();
    }
}

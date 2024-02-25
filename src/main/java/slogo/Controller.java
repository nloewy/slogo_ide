package slogo;

import java.io.FileNotFoundException;
import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.ViewInternal;
import slogo.view.pages.StartScreen;

public class Controller {
    private Stage stage;
    private View view;
    private String currentLanguage = "English";

    public Controller(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        this.view = new View(stage, this);
        openStartScreen();
    }

    public void openStartScreen() throws FileNotFoundException {
        StartScreen startScreen = new StartScreen(this);
        switchToScene(startScreen);
    }

    private void switchToScene(ViewInternal viewInternal) {
        viewInternal.initScene();
        stage.setScene(viewInternal.getScene());
        stage.show();
    }

    public void openNewSession() throws FileNotFoundException {
        Stage newStage = new Stage();
        View view = new View(newStage, this);
        view.run();
    }

    public void handleCommand(String command) {
//         Here, implement command handling logic
//        if ("MOVE UP".equalsIgnoreCase(command)) {
//            view.moveTurtleUp(); If it was in view
//        But for otherwise it run another class that will get from model side

//        }
    }

    public void setCurrentLanguage(String language) {
        this.currentLanguage = language;
        view.setLanguage(currentLanguage);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void loadSession() {
        System.out.println("To Be Implemented! Need to figure out how to move "
            + "file choosing from screen to controller");
    }

    public void openNewXMLSession() {
        System.out.println("To Be Implemented!");
    }

}

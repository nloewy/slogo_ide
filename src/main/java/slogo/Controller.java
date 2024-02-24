package slogo;

import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.pages.MainScreen;
import slogo.view.pages.StartScreen;

public class Controller {
    private Stage stage;
    private View view;
    private String currentLanguage = "English";

    public Controller(Stage stage) {
        this.stage = stage;
        this.view = new View(stage, this);
        openStartScreen();
    }

    public void openStartScreen() {
        StartScreen startScreen = null;
        try {
            startScreen = new StartScreen(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchToScene(startScreen);
    }

    public void openNewSession() {
        MainScreen mainScreen = new MainScreen(this, view);
        switchToScene(mainScreen);
    }

    public void loadSession() {

    }

    public void switchToScene(slogo.view.Scene scene) {
        scene.initScene();
        stage.setScene(scene.getScene());
        stage.show();
    }

    public void handleCommand(String command) {

    }

    public void setCurrentLanguage(String language) {
        this.currentLanguage = language;
        view.setLanguage(currentLanguage);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

}

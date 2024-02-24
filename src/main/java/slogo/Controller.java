package slogo;

import java.io.FileNotFoundException;
import javafx.stage.Stage;
import slogo.view.View;
import slogo.view.pages.MainScreen;
import slogo.view.pages.StartScreen;

public class Controller {
    private Stage stage;
    private View view;
    private String currentLanguage = "English"; // Default language setting

    public Controller(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        this.view = new View(stage, this);
        openStartScreen();
    }

    public void openStartScreen() throws FileNotFoundException {
        StartScreen startScreen = new StartScreen(this);
        switchToScene(startScreen);
    }

    public void openNewSession() throws FileNotFoundException {
        // Simply initialize MainScreen, which will create its own scene
        Stage newStage = new Stage();
        View view = new View(newStage, this);
        view.run();

    }

    private void switchToScene(slogo.view.Scene scene) {
        scene.initScene();
        stage.setScene(scene.getScene());
        stage.show();
    }

    public void handleCommand(String command) {
        // Here, implement command handling logic
        // For the sake of demonstration:
        // If the command is to move the turtle, you might do something like this:
//        if ("MOVE UP".equalsIgnoreCase(command)) { // This is a placeholder, adjust as needed
//            view.moveTurtleUp(); // Assuming such a method exists in View
//        }
        // Extend this method to handle other commands
    }

    public void setCurrentLanguage(String language) {
        this.currentLanguage = language;
        // Notify the view about the language change
        // This might involve refreshing UI text elements to the new language
        view.setLanguage(currentLanguage);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void loadSession() {
    }

    // Additional controller methods as needed...
}

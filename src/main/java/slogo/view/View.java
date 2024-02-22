package slogo.view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.model.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.pages.Screen;
import slogo.view.pages.StartScreen;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;

    private static Stage stage;
    private static String commandString;
    private final Map<String, Number> variables;
    private String lang;

    public View(Stage stage) {
        View.stage = stage;

        lang = "EG";
        commandString = "";
        variables = new HashMap<>();
    }

    public void run() throws FileNotFoundException {
        Screen page = new StartScreen(stage);
        page.setUp();

        Scene scene = new Scene(page.getGroup(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("LightMode.css")).toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /*
     * Handles the user switching languages.
     * //TODO
     */
    public void setLanguage(String lang) {
        this.lang = lang;
    }

    /*
     * Handles the user switching background color.
     */ //TODO Test
    public void setColor(Color color) {
        View.stage.getScene().setFill(color);
    }

    public boolean hasCommandString() {
        return !commandString.isEmpty();
    }

    /*
     * Returns the current text field body.
     */
    public String getCommandString() throws Exception {
        if (hasCommandString()) {
            String temp = commandString;
            commandString = "";
            return temp;
        }

        throw new Exception("No Command String Found!");
    }

    public static void setCommandString(String s) {
        commandString = s;
        System.out.println(commandString);
    }

    @Override
    public void onUpdateValue(String variableName) {

    }

    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState) {

    }

    @Override
    public void onResetTurtle(int id) {

    }

    @Override
    public void onReturn(double value) {

    }

//   /*
//    * Gets the current exception, and shows
//    * it as an alert.
//    */
//   public void displayErrorMessage();

//   /**
//    * Resets all panels in the view
//    */
//   public void resetView();
}


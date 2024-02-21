package slogo.view;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.model.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.pages.MainScreen;
import slogo.view.pages.Screen;

public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;

    private static Stage stage;

    private final Map<String, Number> variables;

    private String lang;
    private static String commandString;

    public View(Stage stage) {
        View.stage = stage;

        lang = "EG";
        commandString = "";
        variables = new HashMap<>();
    }

    public void run() throws FileNotFoundException {
        Screen page = new MainScreen(stage);
        page.setUp();

        Scene scene = new Scene(page.getGroup(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("LightMode.css")).toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void onUpdateVariable(String variableName, double value) {
        variables.remove(variableName);
        variables.put(variableName, value);
    }

    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState, double value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateTurtleState'");
    }

    public static void setCommandString(String s) {
        commandString = s;
        System.out.println(commandString);
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

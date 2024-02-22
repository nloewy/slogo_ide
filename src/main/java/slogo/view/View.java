package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.model.SlogoListener;
import slogo.model.api.TurtleRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View implements SlogoListener {

    private static Stage stage;
    private static List<FrontEndTurtle> turtles;
    private static List<String> commandList;
    private static String commandString;
    private final Map<String, Number> variables;
    private final Image defaultImage;
    private String lang;

    public View(Stage stage) {
        View.stage = stage;

        lang = "EG";
        commandString = "";

        variables = new HashMap<>();
        turtles = new ArrayList<>();
        commandList = new ArrayList<>();
        try {
            defaultImage = new Image(new FileInputStream("src/main/resources/DefaultTurtle.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<FrontEndTurtle> getTurtles() {
        return turtles;
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
            commandList.add(temp);
            return temp;
        }

        throw new Exception("No Command String Found!");
    }

    public static void setCommandString(String s) {
        commandString = s;
        System.out.println(commandString);
    }

    @Override
    public void onUpdateValue(String variableName, Number newValue) {
        variables.remove(variableName);
        variables.put(variableName, newValue);
    }

    //Backend should call this when adding a new turtle too. THis has to be called on initialization in the model.
    //Ready for multiple turtles
    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState) {
        for (FrontEndTurtle turtle : turtles) {
            if (turtle.getId() == turtleState.id()) {
                turtle.setIsPenDisplayed(turtleState.pen());
                turtle.setPosition(new double[]{turtleState.x(), turtleState.y()});
                turtle.setHeading(turtleState.heading());
            }
        }
        turtles.add(new FrontEndTurtle(turtleState.id(), defaultImage, Color.BLACK, new double[]{0, 0}));
    }

    @Override
    public void onResetTurtle(int id) {
        for (FrontEndTurtle turtle : turtles) {
            if (turtle.getId() == id) {
                turtle.setIsPenDisplayed(false);
                turtle.setPosition(new double[]{0, 0});
                turtle.setHeading(0);
                turtle.replaceImage(defaultImage);
            }
        }
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


package slogo.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Controller;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.pages.MainScreen;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;

/*
Represents one IDE session.
There should be one View per window open.
 */
public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;
    private final Map<String, Number> variables;
    private final Image defaultImage;
    private final Stage stage;
    private final List<FrontEndTurtle> turtles;
    private final List<String> commandList;
    private String commandString;
    private String lang;
    private Controller controller;
    private Consumer<String> parse;
    private static final Double[] CENTER = new Double[]{500.0, 300.0};

    public View(Controller controller, Stage stage) {
        this.stage = stage;
        this. controller = controller;

        lang = "EG";
        commandString = "";

        variables = new LinkedHashMap<>();
        turtles = new ArrayList<>();
        commandList = new ArrayList<>();

        try {
            defaultImage = new Image(new FileInputStream("src/main/resources/DefaultTurtle.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        turtles.add(new FrontEndTurtle(1, CENTER, Color.BLUE, true, 0, defaultImage));
    }

    public void run(Consumer<String> parseMethod) throws FileNotFoundException {
        MainScreen page = new MainScreen(this, stage, controller);
        parse = parseMethod;

        page.setUp();
        Scene scene = new Scene(page.getGroup(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("LightMode.css")).toExternalForm());

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public List<FrontEndTurtle> getTurtles() {
        return turtles;
    }

    public Map<String, Number> getVariables() {
        return variables;
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
        stage.getScene().setFill(color);
    }

    private boolean hasCommandString() {
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

    //TODO call the parse method here
    //this parse method should handle starting the backend too
    public void pushCommand(String s) {
        commandString = s;
        System.out.println(commandString);
        parse.accept(commandString);
    }

    @Override
    public void onUpdateValue(String variableName, Number newValue) {
        variables.put(variableName, newValue);
    }

    //Backend should call this when adding a new turtle too. THis has to be called on initialization in the model.
    //Ready for multiple turtles
    //TODO flip the y axis thing
    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState) {
        for (FrontEndTurtle turtle : getTurtles()) {
            if (turtle.getId() == turtleState.id()) {
                turtle.setIsPenDisplayed(turtleState.pen());
                turtle.setPosition(new Double[]{turtleState.x(), turtleState.y()});
                turtle.setHeading(turtleState.heading());
                return;
            }
        }
        turtles.add(new FrontEndTurtle(
            turtleState.id(), 
            new Double[]{turtleState.x(), turtleState.y()}, 
            Color.BLACK, 
            true, 
            turtleState.heading(), 
            defaultImage));
    }

    @Override
    public void onResetTurtle(int id) {
        for (FrontEndTurtle turtle : turtles) {
            if (turtle.getId() == id) {
                turtle.setIsPenDisplayed(false);
                turtle.setPosition(new Double[]{0.0, 0.0});
                turtle.setHeading(0);
                turtle.setImage(defaultImage);
            }
        }
    }

    //val returned by last command
    //add it to history
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


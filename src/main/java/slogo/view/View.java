package slogo.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.Controller;
import slogo.Main;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.view.pages.MainScreen;

/*
Represents one IDE session.
There should be one View per window open.
 */
public class View implements SlogoListener {

    private static final int height = 600;
    private static final int width = 1000;
    private final Map<String, List<String>> variableCommands;
    private final Map<String, Number> variableValues;
    private Image defaultImage;
    private final Stage stage;
    private final List<FrontEndTurtle> turtles;
    private final Stack<String> commandHistory;
    private final Stack<String> userDefinedCommandHistory;
    private String commandString;
    private String lang;
    private Controller controller;
    private Consumer<String> parse;
    private Scene scene;
    private MainScreen page;

    public static final Double[] ORIGIN = new Double[]{width/2.0, height/2.0};

    public View(Controller controller, Stage stage) {
        this.stage = stage;
        this. controller = controller;

        lang = "EG";
        commandString = "";

        variableCommands = new LinkedHashMap<>();
        variableValues = new LinkedHashMap<>();
        turtles = new ArrayList<>();
        commandHistory = new Stack<String>();
        userDefinedCommandHistory = new Stack<String>();

        try {
            defaultImage = new Image(new FileInputStream("src/main/resources/DefaultTurtle.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        turtles.add(new FrontEndTurtle(1, ORIGIN, Color.BLUE, true, 0, defaultImage));
    }

    public void run(Consumer<String> parseMethod) throws FileNotFoundException {
        page = new MainScreen(this, stage, controller);
        parse = parseMethod;
        page.setUp();
        scene = new Scene(page.getRegion(), width, height);
        controller.updateCurrentTheme(scene);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public void setTurtleImage(File f) {
        try {
            defaultImage = new Image(new FileInputStream(f));
            for (FrontEndTurtle t : getTurtles()) {
                t.setImage(defaultImage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<FrontEndTurtle> getTurtles() {
        return turtles;
    }

    public Map<String, List<String>> getVariableCommands() {
        return variableCommands;
    }
    public Map<String, Number> getVariableValues() {
        return variableValues;
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
            commandHistory.push(temp);
            return temp;
        }

        throw new Exception("No Command String Found!");
    }

    public void pushCommand(String s) {
        commandString = s;
        commandHistory.push(s);
        parse.accept(commandString);
        page.updateCommands();
    }

    public Stack<String> getCommandHistory() {
        return commandHistory;
    }

    public Stack<String> getUserDefinedCommandHistory() {
        return userDefinedCommandHistory;
    }

    @Override
    public void onUpdateValue(String variableName, Number newValue) {
        variableCommands.put(variableName, List.of(commandHistory.peek()));
        variableValues.put(variableName, newValue);
        page.updateVariables();
    }


    public void setPosition(FrontEndTurtle turtle, double x, double y, double newHeading) {
        double oldX = turtle.getX();
        double oldY = turtle.getY();
        double oldHeading = turtle.getHeading();
        Timeline animation = new Timeline();
        animation.setCycleCount(1);

        for (int i = 1; i <= 50; i++) {
            double intermediateX = oldX + (x - oldX) / 50 * i;
            double intermediateY = oldY + (y - oldY) / 50 * i;
            double intermediateHeading = oldHeading + (newHeading - oldHeading) / 50 * i;

            Line line = turtle.drawLine(oldX, oldY, intermediateX, intermediateY);

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.10),
                e -> {
                    turtle.getDisplay().setLayoutX(intermediateX);
                    turtle.getDisplay().setLayoutY(intermediateY);
                    turtle.getDisplay().setRotate(intermediateHeading);
                    page.addLine(line);
                });
            animation.getKeyFrames().add(keyFrame);
        }

        animation.play();
        turtle.setPosition(x, y, newHeading);
    }

    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState) {
        for (FrontEndTurtle turtle : getTurtles()) {
            if (turtle.getId() == turtleState.id()) {
                turtle.setIsPenDisplayed(turtleState.pen());
                setPosition(turtle, turtleState.x() + ORIGIN[0], turtleState.y() + ORIGIN[1], turtleState.heading());
                return;
            }
        }
        turtles.add(new FrontEndTurtle(
            turtleState.id(),
            new Double[]{turtleState.x() + ORIGIN[0], turtleState.y() + ORIGIN[1]},
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
                turtle.setPosition(ORIGIN[0], ORIGIN[1],0);
                turtle.setImage(defaultImage);
            }
        }
    }

    //val returned by last command
    //add it to history next to the command
    @Override
    public void onReturn(double value, String string) {

    }

    @Override
    public void onCommand(String string, boolean userDefined) {
        if (userDefined) {
            userDefinedCommandHistory.add(string);
        }
        page.updateCommands();
    }
}


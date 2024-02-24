package slogo.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.Controller;
import slogo.model.SlogoListener;
import slogo.model.api.TurtleRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View implements SlogoListener {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private Stage stage;
    private Controller controller;
    private Pane drawingPane;
    private Map<Integer, FrontEndTurtle> turtles;
    private Image defaultImage;
    private Map<String, Number> variables = new HashMap<>();
    private List<String> commandList = new ArrayList<>();
    private String commandString = "";
    private String lang = "EG";

    public View(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
        this.turtles = new HashMap<>();
        this.defaultImage = loadDefaultImage();
        initializeUI();
    }

    private Image loadDefaultImage() {
        try {
            return new Image(new FileInputStream("src/main/resources/DefaultTurtle.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Default turtle image file not found", e);
        }
    }

    private void initializeUI() {
        drawingPane = new Pane();
        drawingPane.setPrefSize(WIDTH, HEIGHT);
        Scene scene = new Scene(drawingPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("SLogo Interpreter");
    }

    @Override
    public void onUpdateValue(String variableName, Number newValue) {
        variables.put(variableName, newValue);
    }

    @Override
    public void onUpdateTurtleState(TurtleRecord turtleState) {
        FrontEndTurtle turtle = turtles.computeIfAbsent(turtleState.id(), id -> createTurtle(id, turtleState));
        turtle.setPosition(new double[]{turtleState.x(), turtleState.y()});
        turtle.setHeading(turtleState.heading());
        turtle.setIsPenDisplayed(turtleState.pen());
        // Update the drawing pane or scene as needed
    }

    private FrontEndTurtle createTurtle(int id, TurtleRecord turtleState) {
        // Initial position could be center or based on TurtleRecord state
        double[] startPosition = {turtleState.x(), turtleState.y()};
        FrontEndTurtle turtle = new FrontEndTurtle(id, defaultImage, Color.BLACK, startPosition);
        drawingPane.getChildren().add(turtle.getDisplay());
        return turtle;
    }

    @Override
    public void onResetTurtle(int id) {
        FrontEndTurtle turtle = turtles.get(id);
        if (turtle != null) {
            turtle.setPosition(new double[]{0.0, 0.0});
            turtle.setHeading(0);
            turtle.setIsPenDisplayed(false);
        }
    }

    public void setLanguage(String lang) {
        this.lang = lang;
    }

    public void setColor(Color color) {
        drawingPane.setBackground(new javafx.scene.layout.Background(
            new javafx.scene.layout.BackgroundFill(color, null, null)));
    }

    public void setCommandString(String command) {
        this.commandString = command;
        controller.handleCommand(command);
    }

    public boolean hasCommandString() {
        return !commandString.isEmpty();
    }

    public String getCommandString() throws Exception {
        if (hasCommandString()) {
            String temp = commandString;
            commandString = "";
            commandList.add(temp);
            return temp;
        }
        throw new Exception("No Command String Found!");
    }

    @Override
    public void onReturn(double value) {

    }
}

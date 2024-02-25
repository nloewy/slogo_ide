package slogo.view.pages;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.model.api.TurtleRecord;
import slogo.view.ButtonUtil;
import slogo.view.FrontEndTurtle;
import slogo.view.View;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/*
The View will already know the XMLFile data when
this is initialized. This will periodically
call View for updates and to schedule
animation keyframes.
 */
public class MainScreen extends Screen {

    private static final double FRAME_RATE = 4.0;
    private final Timeline animation = new Timeline();
    private final double speed = 0.75;
    Stage stage;
    Group root;
    View myView;
    TextField field;
    Button submitField;
    Button testButton;
    Map<FrontEndTurtle, Double[]> myTurtlePositions = new HashMap<>();

    // Add an XMLFile object to this when Model adds one
    // Controller calls this with an XML File
    public MainScreen(View view, Stage stage) throws FileNotFoundException {
        super();
        this.stage = stage;

        myView = view;

        field = new TextField();
        field.setId("field");

        submitField = ButtonUtil.generateButton("submit", 251, 100, event -> {
            view.setCommandString(field.getText());
            field.clear();
        });

        // testing
        testButton = ButtonUtil.generateButton("test", 400, 100, event -> {
            myView.onUpdateTurtleState(new TurtleRecord(0, 100, 100, true, true, 90));
        });

        root = new Group();
        root.getChildren().add(field);
        root.getChildren().add(submitField);
        root.getChildren().add(testButton);

        for (FrontEndTurtle turtle : myView.getTurtles()) {
            myTurtlePositions.put(turtle, turtle.getPosition());
            root.getChildren().add(turtle.getDisplay());
        }

        System.out.println("Testing");

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> update()));
    }

    public void update() {
        System.out.println(myView.getTurtles());
        Map<FrontEndTurtle, Double[]> deltaPositions = new HashMap<>();

        for (FrontEndTurtle turtle : myView.getTurtles()) {

            // this is here for testing, this should be replaced with animation
            turtle.getDisplay().setLayoutX(turtle.getPosition()[0]);
            turtle.getDisplay().setLayoutY(turtle.getPosition()[0]);

            // if (!myTurtlePositions.containsKey(turtle)) {
            // System.out
            // myTurtlePositions.put(turtle, turtle.getPosition());
            // }

            // deltaPositions.put(
            // turtle,
            // new Double[] {
            // turtle.getPosition()[0] - myTurtlePositions.get(turtle)[0],
            // turtle.getPosition()[1] - myTurtlePositions.get(turtle)[1]
            // });
        }
        // handleTurtleAnimation(deltaPositions);
        // syncTurtlesWithView();
    }

    public void handleTurtleAnimation(Map<FrontEndTurtle, Double[]> deltas) {
        animation.stop();

        Timeline localAnimation = new Timeline();
        localAnimation.setCycleCount(5);
        localAnimation.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> animateMovement(deltas)));

        animation.play();
    }

    public void animateMovement(Map<FrontEndTurtle, Double[]> deltas) {
        for (FrontEndTurtle turtle : deltas.keySet()) {
            double xStep = deltas.get(turtle)[0] / 5;
            double yStep = deltas.get(turtle)[1] / 5;

            turtle.getDisplay().setLayoutX(turtle.getPosition()[0] + xStep);
            turtle.getDisplay().setLayoutY(turtle.getPosition()[1] + yStep);
        }
    }

    public void syncTurtlesWithView() {
        for (FrontEndTurtle turtle : myView.getTurtles()) {
            myTurtlePositions.remove(turtle);
            myTurtlePositions.put(turtle, turtle.getPosition());
        }
    }

    @Override
    public void setUp() {
        field.setLayoutX(100);
        field.setLayoutY(100);
        animation.play();
    }

    @Override
    public Group getGroup() {
        return root;
    }

}
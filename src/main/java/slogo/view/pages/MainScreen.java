package slogo.view.pages;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.view.ButtonUtil;
import slogo.view.FrontEndTurtle;
import slogo.view.View;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    TextField field;
    Button submitField;
    List<FrontEndTurtle> myTurtles = new ArrayList<>();

    //Add an XMLFile object to this when Model adds one
    //Controller calls this with an XML File
    public MainScreen(View view, Stage stage) throws FileNotFoundException {
        super();
        this.stage = stage;

        field = new TextField();
        submitField = ButtonUtil.generateButton("submit", 251, 100, event -> {
            view.setCommandString(field.getText());
            field.clear();
        });

        root = new Group();
        root.getChildren().add(field);
        root.getChildren().add(submitField);

        for (FrontEndTurtle turtle : view.getTurtles()) {
            myTurtles.add(turtle);
            root.getChildren().add(turtle.getDisplay());
        }

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> update()));

    }

    public void update() {

    }

    @Override
    public void setUp() {
        field.setLayoutX(100);
        field.setLayoutY(100);
    }

    @Override
    public Group getGroup() {
        return root;
    }

}
package slogo.view;

import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class FrontEndTurtle {
    ImageView display;
    Image displayImage;
    Color penColor;

    Double[] myPosition;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    private static final double FRAME_RATE = 4.0;
    private final Timeline animation = new Timeline();
    private final double speed = 0.75;

    private Stack<Line> pathHistory = new Stack<Line>();

    public FrontEndTurtle(int id, Double[] position, Color color, boolean isPenVisible, double heading, Image image) {
        myId = id;
        displayImage = image;
        display = new ImageView(displayImage);
        display.setId("turtle"); // doing this for testing, did not work because it keeps making new
                                 // FrontEndTurtle objects
        display.setPreserveRatio(true);
        display.setFitWidth(50);
        display.setLayoutX(position[0]);
        display.setLayoutY(position[1]);
        penColor = color;
        isPenDisplayed = isPenVisible;
        this.heading = heading;
    }

    public int getId() {
        return myId;
    }

    // public Line getLastPath() {
    // return pathHistory.pop();
    // }

    public Color getPenColor() {
        return penColor;
    }

    public void setPenColor(Color newColor) {
        penColor = newColor;
    }

    public boolean isPenDisplayed() {
        return isPenDisplayed;
    }

    public void setIsPenDisplayed(boolean state) {
        isPenDisplayed = state;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double newValue) {
        display.setRotate(newValue);
    }

    public void setImage(Image newImage) {
        displayImage = newImage;
        display = new ImageView(displayImage);
        display.setPreserveRatio(true);
        display.setFitWidth(50);
    }

    public Double[] getPosition() {
        return myPosition;
    }

    public void setPosition(Double[] newPosition, double heading) {
        // Double[] oldPosition = {display.getLayoutX(), display.getLayoutY()};

        // Timeline animation = new Timeline();
        // animation.setCycleCount(100);
        // animation.getKeyFrames()
        // .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)), e -> stepTurtle(oldPosition, newPosition, display.getRotate(), heading)));
        // animation.play();

        display.setLayoutX(newPosition[0]);
        display.setLayoutY(newPosition[0]);
        display.setRotate(heading);
    }

    // public void stepTurtle(Double[] oldPosition, Double[] newPosition, double oldHeading, double newHeading) {
    //     double xStep = (newPosition[0] - oldPosition[0])/5;
    //     double yStep = (newPosition[1] - oldPosition[1])/5;
    //     double rotStep = (newHeading - oldHeading)/5;

    //     display.setLayoutX(display.getLayoutX() + xStep);
    //     display.setLayoutX(display.getLayoutY() + yStep);
    //     display.setRotate(display.getRotate() + rotStep);
    // }

    public ImageView getDisplay() {
        return display;
    }
}

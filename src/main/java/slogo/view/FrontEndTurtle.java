package slogo.view;

import java.util.Stack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import slogo.model.Turtle;

public class FrontEndTurtle {
    ImageView display;
    Image displayImage;
    Color penColor;

    Double[] myPosition;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    private Stack<Line> pathHistory = new Stack<Line>();

    public static Double[] ORIGIN = { 200.0, 200.0 };

    public FrontEndTurtle(int id, Double[] position, Color color, boolean isPenVisible, double heading, Image image) {
        myId = id;
        displayImage = image;
        display = new ImageView(displayImage);
        display.setId("turtle"); // doing this for testing, did not work because it keeps making new
                                 // FrontEndTurtle objects
        display.setPreserveRatio(true);
        display.setFitWidth(50);
        setPosition(position);
        penColor = color;
        isPenDisplayed = isPenVisible;
        this.heading = heading;
    }

    public int getId() {
        return myId;
    }

    public Line getLastPath() {
        return pathHistory.peek();
    }

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

    // Figure out a way
    // To handle animation here
    public void setPosition(Double[] newPosition) {
        Line line = new Line(
            //fix the line stuff its lagging behind
                display.getLayoutX() + 25,
                display.getLayoutY() + 25,
                ORIGIN[0] + newPosition[0] + 25,
                ORIGIN[1] + newPosition[1] + 25);
        line.setStroke(penColor);

        pathHistory.push(line);

        display.setLayoutX(ORIGIN[0] + newPosition[0]);
        display.setLayoutY(ORIGIN[1] + newPosition[1]);
    }

    public ImageView getDisplay() {
        return display;
    }
}

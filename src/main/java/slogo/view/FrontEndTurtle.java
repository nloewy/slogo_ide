package slogo.view;

import static slogo.view.View.ORIGIN;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class FrontEndTurtle {
    ImageView display;
    Image displayImage;
    Color penColor;

    double myX;
    double myHeading;
    double myY;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    private Timeline animation;

    private static final double FRAME_RATE = 4.0;
    private final double speed = 0.75;

    private Stack<Line> pathHistory = new Stack<Line>();

    public FrontEndTurtle(int id, Double[] position, Color color, boolean isPenVisible, double heading, Image image) {
        myId = id;
        myX = ORIGIN[0];
        myY = ORIGIN[1];
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

    public List<Line> getPathHistory() {
        return pathHistory;
    }

    public double getX() {
        return myX;
    }
    public double getY() {
        return myY;
    }
    public double getHeading() {
        return myHeading;
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

    public void setImage(Image newImage) {
        displayImage = newImage;
        display.setImage(displayImage);
        display.setPreserveRatio(true);
        display.setFitWidth(50);
    }


   // public Timeline getAnimation() {
     //   return animation;
  //  }

    public void setPosition(double x, double y, double newHeading) {
        double oldHeading = myHeading;

        myHeading = newHeading;

        display.setLayoutX(x);
        display.setLayoutY(y);
        display.setRotate(newHeading);

        Timeline animation = new Timeline();
        animation.setCycleCount(5);

        for (int i = 1; i <= 5; i++) {
            double intermediateX = myX + (x - myX) / 5 * i;
            double intermediateY = myY + (y - myY) / 5 * i;
            double intermediateHeading = oldHeading + (newHeading - oldHeading) / 5 * i;

            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 1.0),
                e -> {
                    stepTurtle(myX, x, myY, y, oldHeading, intermediateHeading, finalI);
                    drawLine(myX, myY, intermediateX, intermediateY);
                });
            animation.getKeyFrames().add(keyFrame);
        }
        animation.play();
        myX = x;
        myY = y;
    }

    public void stepTurtle(double oldX, double newX, double oldY,double  newY, double oldHeading, double newHeading , int stepNumber) {
        double intermediateX = oldX + (newX - oldX) / 5 * stepNumber;
        double intermediateY = oldY + (newY - oldY) / 5 * stepNumber;
        double intermediateHeading = oldHeading + (newHeading - oldHeading) / 5 * stepNumber;
        display.setLayoutX(intermediateX);
        display.setLayoutY(intermediateY);
        display.setRotate(intermediateHeading);
    }

    public Line drawLine(Double oldPosition, Double oldPosition2, Double newPosition, Double newPosition2) {
        Line line = new Line(oldPosition + 225, oldPosition2 + 125, newPosition + 225, newPosition2 + 125);
        line.setStroke(penColor);
        line.setVisible(isPenDisplayed);
        pathHistory.push(line);
        return line;
    }



    public ImageView getDisplay() {
        return display;
    }
}

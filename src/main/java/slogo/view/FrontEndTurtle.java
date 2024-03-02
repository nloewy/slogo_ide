package slogo.view;

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

    Double[] myPosition;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    private static final double FRAME_RATE = 4.0;
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

    public void setImage(Image newImage) {
        displayImage = newImage;
        display.setImage(displayImage);
        display.setPreserveRatio(true);
        display.setFitWidth(50);
    }

    public Double[] getPosition() {
        return myPosition;
    }

    public void setPosition(Double[] newPosition, double newHeading) {
        Double[] oldPosition = { display.getLayoutX(), display.getLayoutY() };
        double oldHeading = display.getRotate();

        Timeline animation = new Timeline();
        animation.setCycleCount(3);
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1.0 / (FRAME_RATE * speed)),
                        e -> stepTurtle(oldPosition, newPosition, oldHeading, newHeading)));
        animation.setOnFinished((event) -> {
            pathHistory.add(drawLine(oldPosition[0], oldPosition[1], newPosition[0], newPosition[1]));
        });

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                animation.play();
            });
        }).start();
    }

    public Line drawLine(Double oldPosition, Double oldPosition2, Double newPosition, Double newPosition2) {
        Line line = new Line(oldPosition + 225, oldPosition2 + 125, newPosition + 225, newPosition2 + 125);
        line.setStroke(penColor);
        line.setVisible(isPenDisplayed);
        return line;
    }

    public void stepTurtle(Double[] oldPosition, Double[] newPosition, double oldHeading, double newHeading) {

        double xStep = (newPosition[0] - oldPosition[0]) / 3;
        double yStep = (newPosition[1] - oldPosition[1]) / 3;
        double rotStep = (newHeading - oldHeading) / 3;

        display.setLayoutX(display.getLayoutX() + xStep);
        display.setLayoutY(display.getLayoutY() + yStep);
        display.setRotate(display.getRotate() + rotStep);
    }

    public ImageView getDisplay() {
        return display;
    }
}

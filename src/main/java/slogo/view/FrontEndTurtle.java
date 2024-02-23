package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class FrontEndTurtle {
    ImageView display;
    Image displayImage;
    Color penColor;

    double[] coords;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    public FrontEndTurtle(int id, Image image, Color color, double[] position) {
        myId = id;
        displayImage = image;
        display = new ImageView(displayImage);
        display.setPreserveRatio(true);
        display.setFitWidth(50);

        display.setLayoutX(position[0]);
        display.setLayoutY(position[1]);
    }

    public int getId() {
        return myId;
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
        heading = newValue;
    }

    public void setImage(Image newImage) {
        displayImage = newImage;
        display = new ImageView(displayImage);
        display.setPreserveRatio(true);
        display.setFitWidth(50);
    }

    public double[] getPosition() {
        return new double[]{display.getLayoutX(), display.getLayoutY()};
    }

    public void setPosition(double[] newPosition) {
        display.setLayoutX(newPosition[0]);
        display.setLayoutY(newPosition[1]);
    }

    public ImageView getDisplay() {
        return display;
    }
}

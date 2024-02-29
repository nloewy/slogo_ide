package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class FrontEndTurtle {
    ImageView display;
    Image displayImage;
    Color penColor;

    Double[] myPosition;
    double heading = 0;
    int myId;
    boolean isPenDisplayed = false;

    public FrontEndTurtle(int id, Double[] position, Color color, boolean isPenVisible, double heading, Image image) {
        myId = id;
        displayImage = image;
        display = new ImageView(displayImage);
        display.setId("turtle"); // doing this for testing, did not work because it keeps making new FrontEndTurtle objects
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
        heading = newValue;
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

    //Figure out a way
    //To handle animation here
    public void setPosition(Double[] newPosition) {
        myPosition = newPosition;
        
        display.setLayoutX(myPosition[0]);
        display.setLayoutY(myPosition[1]);
    }

    public ImageView getDisplay() {
        return display;
    }
}

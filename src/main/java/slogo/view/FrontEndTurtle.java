package slogo.view;



import java.util.List;
import java.util.Stack;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class FrontEndTurtle {

  private ImageView display;
  private Image displayImage;
  private Color penColor;
  private double myX;
  private double myHeading;
  private double myY;
  private double heading = 0;
  private int myId;
  private boolean isPenDisplayed = false;
  private Timeline animation;
  private final Stack<Line> pathHistory = new Stack<Line>();

  public FrontEndTurtle(int id, Double[] position, Color color, boolean isPenVisible,
      double heading, Image image) {
    myId = id;
    myX = position[0];
    myY = position[1];
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

  public Line drawLine(Double oldPosition, Double oldPosition2, Double newPosition,
      Double newPosition2) {
    Line line = new Line(oldPosition, oldPosition2, newPosition, newPosition2);
    line.setStroke(penColor);
    line.setVisible(isPenDisplayed);
    pathHistory.push(line);
    return line;
  }

  public ImageView getDisplay() {
    return display;
  }

  public void setPosition(double x, double y, double newHeading) {
    myX = x;
    myY = y;
    myHeading = newHeading;
  }
}

package slogo.view;



import java.awt.Insets;
import java.util.List;
import java.util.Stack;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import slogo.view.pages.MainScreen;

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
  private MainScreen view;
  private boolean isActive;
  private Stage infoPopup;
  private Label positionLabel;
  private Label headingLabel;
  private Label penStatusLabel;
  private Label penColorLabel;

  public FrontEndTurtle(int id, double x, double y, Color color, boolean isPenVisible,
      double heading, Image image, MainScreen view) {
    this.view = view;
    myId = id;
    myX = x;
    myY = y;
    displayImage = image;
    display = new ImageView(displayImage);
    display.setId("turtle"); // doing this for testing, did not work because it keeps making new
    // FrontEndTurtle objects
    display.setPreserveRatio(true);
    display.setFitWidth(50);
    display.setLayoutX(myX);
    display.setLayoutY(myY);
    penColor = color;
    isPenDisplayed = isPenVisible;
    this.heading = heading;

    initInfoPopup();

    display.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      view.pushCommand("TELL " + id);
      updateAndShowInfoPopup();
    });
  }

  private void initInfoPopup() {
    infoPopup = new Stage();
    infoPopup.initStyle(StageStyle.UTILITY);
    infoPopup.setAlwaysOnTop(true);

    VBox content = new VBox(10);

    positionLabel = new Label();
    headingLabel = new Label();
    penStatusLabel = new Label();
    penColorLabel = new Label();


    content.getChildren().addAll(positionLabel, headingLabel, penStatusLabel, penColorLabel);

    Scene scene = new Scene(content);
    infoPopup.setScene(scene);
  }

  private void updateAndShowInfoPopup() {
    if (infoPopup != null) {
      positionLabel.setText(String.format("ID: %d\nX: %.2f\nY: %.2f", myId, myX, myY));
      headingLabel.setText(String.format("Heading: %.2f", myHeading));
      penStatusLabel.setText(String.format("Pen: %s", isPenDisplayed ? "Down" : "Up"));
      penColorLabel.setText(String.format("Pen Color: %s", Color.valueOf(penColor.toString())));

      if (!infoPopup.isShowing()) {
        infoPopup.show();
      } else {
        infoPopup.toFront();
      }
    }
  }

  public void setIsActive(boolean state) {
    isActive = state;

    if (isActive) {
      display.setOpacity(1);
    }
    else {
      display.setOpacity(0.5);
    }
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
    display.setFitHeight(50);
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

  public void setPosition(double x, double y, double newHeading, boolean visble) {
    myX = x;
    myY = y;
    myHeading = newHeading;
    if (infoPopup != null && infoPopup.isShowing()) {
      updateAndShowInfoPopup();
    }
    setVisible(visble);
  }


  private void setVisible(boolean visible) {
    display.setVisible(visible);
  }
}

package slogo.view;



import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import slogo.view.pages.MainScreen;

public class FrontEndTurtle {

  private ImageView display;
  private Image displayImage;
  private Color penColor;

  private Queue<Animation> myAnimation;

  private double myX;
  private double myHeading;
  private double myY;
  private double heading = 0;
  private int myId;
  private boolean isPenDisplayed = false;
  private Timeline animation;
  private final Stack<Line> pathHistory = new Stack<Line>();
  private boolean isActive;
  private Duration pausedTime;
  private boolean paused ;
  private Animation currAnimation;
  private boolean animationPlaying;

  public FrontEndTurtle(int id, double x, double y, Color color, boolean isPenVisible,
      double heading, Image image, MainScreen view) {
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
    paused = false;

    display.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      view.pushCommand("TELL " + id);
    });

    myAnimation = new ArrayDeque<>();
    this.heading = heading;
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

  public Queue<Animation> getAnimationQueue() {
    return myAnimation;
  }

  private void finishCurrAnimation() {
    if (currAnimation != null) {
      currAnimation.play();
      paused = true;
    }
  }

  private void playSingleAnimation() {
    if (!myAnimation.isEmpty()) {
      Animation animation = myAnimation.poll();
      animation.setOnFinished(event -> {
        animationPlaying = false;
        playAnimation(); // Continue playing other animations after finishing this one
      });
      animationPlaying = true;
      animation.play();
    }
  }

  public void playAnimation() {
    if (!animationPlaying && !myAnimation.isEmpty() && !paused) {
      currAnimation = myAnimation.poll();
      currAnimation.setOnFinished(event -> {
        animationPlaying = false;
        currAnimation = null;
        playAnimation();
      });
      animationPlaying = true;
      currAnimation.play();
    } else if (animationPlaying && paused) {
      if (currAnimation != null) {
        pausedTime = currAnimation.getCurrentTime(); // Store the current time when animation is paused
        currAnimation.pause();
      }
    } else if (animationPlaying && !paused && pausedTime != null) {
      // If animation was paused and now unpaused, resume from the paused time
      if (currAnimation != null) {
        currAnimation.playFrom(pausedTime);
        pausedTime = null; // Reset paused time
      }
    }
  }

  public void handleStep() {
    if (currAnimation == null) {
      playSingleAnimation();
    } else {
      finishCurrAnimation();
    }
  }

  public void handlePause(boolean b) {
    paused = b;
  }
}

package slogo.model;

import slogo.model.api.TurtleRecord;

/**
 * Represents the backend's model of a Turtle object
 *
 * @author Noah Loewy
 */
public class Turtle {

  private final int myId;
  private boolean myPen;
  private final boolean active;
  private double myX;
  private double myY;
  private boolean myVisible;
  private double myHeading; //range [0.0,360.0)

  /**
   * Constructs a Turtle object with specified ID at the origin with the pen down, facing 0 degrees
   *
   * @param id The unique identifier for the turtle.
   */
  public Turtle(int id) {
    myId = id;
    myX = 0.0;
    myY = 0.0;
    myPen = true;
    myVisible = true;
    active = true;
    myHeading = 0.0;
  }

  /**
   * Constructs a Turtle object with the specified properties.
   *
   * @param id      The unique identifier for the turtle.
   * @param x       The x-coordinate of the turtle's position.
   * @param y       The y-coordinate of the turtle's position.
   * @param pen     The pen status (true for down, false for up).
   * @param visible The visibility status (true for visible, false for hidden).
   * @param heading The heading angle of the turtle.
   */

  public Turtle(int id, double x, double y, boolean pen, boolean visible, double heading) {
    myId = id;
    myX = x;
    myY = y;
    myPen = pen;
    myVisible = visible;
    myHeading = heading;
    active = true;
  }

  /**
   * Retrieves the y-coordinate of the turtle's position.
   *
   * @return The y-coordinate.
   */

  public double getY() {
    return myY;
  }

  /**
   * Sets the y-coordinate of the turtle's position.
   *
   * @param myY The new y-coordinate.
   */

  public void setY(double myY) {
    this.myY = myY;
  }

  /**
   * Retrieves the x-coordinate of the turtle's position.
   *
   * @return The x-coordinate.
   */

  public double getX() {
    return myX;
  }

  /**
   * Sets the x-coordinate of the turtle's position.
   *
   * @param myX The new x-coordinate.
   */

  public void setX(double myX) {
    this.myX = myX;
  }

  /**
   * Retrieves the heading of the turtle (direction it is facing).
   *
   * @return The heading.
   */

  public double getHeading() {
    return myHeading;
  }

  /**
   * Sets the heading of the turtle (direction it is facing).
   *
   * @param myHeading The new heading.
   */

  public void setHeading(double myHeading) {
    this.myHeading = (myHeading % 360 + 360) % 360; // Ensures positive modulus
  }

  /**
   * Retrieves an immutable record representing the current properties of the turtle.
   *
   * @return A TurtleRecord object representing the turtle's state.
   */

  public TurtleRecord getImmutableTurtle() {
    return new TurtleRecord(myId, myX, myY, myPen, myVisible, myHeading);
  }

  /**
   * Retrieves whether pen is up or down.
   *
   * @return true iff pen is down
   */

  public boolean getPen() {
    return myPen;
  }

  /**
   * Sets the pen status of the turtle.
   *
   * @param b The new pen status (true for down, false for up).
   */

  public void setPen(boolean b) {
    myPen = b;
  }

  /**
   * Retrieves whether the turtle is visible.
   *
   * @return true iff turtle is visible
   */

  public boolean getVisible() {
    return myVisible;
  }

  /**
   * Sets the visibility status of the turtle.
   *
   * @param b The new visibility status
   */

  public void setVisible(boolean b) {
    myVisible = b;
  }

  /**
   * Retrieves the id of the turtle.
   *
   * @return id The id of the turtle
   */

  public int getId() {
    return myId;
  }


}

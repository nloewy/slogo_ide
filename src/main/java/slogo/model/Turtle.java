package slogo.model;

import slogo.model.api.TurtleRecord;

/**
 * Represents the backend's model of a Turtle object
 * @author Noah Loewy
 */
public class Turtle {

  private int myId;
  private double myX;
  private double myY;
  private boolean myPen;
  private double myHeading; //range [0.0,360.0)


  public Turtle(int id) {
    myId = id;
    myX = 0.0;
    myY = 0.0;
    myPen = true;
    myHeading = 0.0;
  }

  public Turtle(int id, double x, double y, boolean pen, double heading) {
    myId = id;
    myX = x;
    myY = y;
    myPen = pen;
    myHeading = heading;
  }

  public double getY() {
    return myY;
  }

  public void setY(double myY) {
    this.myY = myY;
  }

  public double getX() {
    return myX;
  }

  public void setX(double myX) {
    this.myX = myX;
  }

  public void setHeading(double myHeading) {
    this.myHeading = myHeading;
  }
  public double getHeading() {
    return myHeading;
  }

  public TurtleRecord getImmutableTurtle() {
    return new TurtleRecord(myId, myX, myY, myPen, myHeading);
  }
}

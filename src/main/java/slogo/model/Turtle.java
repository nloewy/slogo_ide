package slogo.model;

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



}

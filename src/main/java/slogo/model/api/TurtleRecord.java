package slogo.model.api;

/**
 * Represents an immutable turtle object that the model passes to the view upon each state update.
 *
 * @param id            turtle unique id
 * @param x             current x coordinate of turtle
 * @param y             current y coordinate of turtle
 * @param pen           true if pen is down, false if up
 * @param visible       true if pen is visible, false if up
 * @param heading       orientation of turtle (0 is "north")
 * @param bgIndex       index representing background color of graphics window
 * @param penColorIndex index representing pen for turtle
 */
public record TurtleRecord(int id, double x, double y, boolean pen, boolean visible, double heading,
                           int bgIndex, int penColorIndex) {
  /**
   * @deprecated This constructor is deprecated because it doesn't provide the background color and pen color index.
   * Use the constructor with all parameters instead.
   */
  @Deprecated
  public TurtleRecord(int id, double x, double y, boolean pen, boolean visible, double heading) {
    this(id, x, y, pen, visible, heading, 0, 0); // Default values for bgIndex and penColorIndex
  }
}



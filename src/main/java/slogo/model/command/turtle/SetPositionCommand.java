package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The SetPositionCommand class represents the command to set the turtle's position to a specified
 * coordinate. It sets the turtle's position to the specified coordinates and calculates the
 * distance traveled by the turtle.
 *
 * @author Noah Loewy
 */

public class SetPositionCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 2;
  private static final int NEW_X_INDEX = 0;
  private static final int NEW_Y_INDEX = 1;
  private final SlogoListener listener;

  /**
   * Constructs a SetPositionCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public SetPositionCommand(ModelState modelState, SlogoListener listener) {
    this.listener = listener;
  }

  /**
   * Executes the SetPositionCommand, setting the turtle's position to the specified coordinates.
   *
   * @param arguments a list of nodes representing the arguments for this command (containing two
   *                  nodes with the x and y coordinates)
   * @param turtle    the id of the turtle currently active
   * @return the distance traveled by the turtle
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    double newX = arguments.get(NEW_X_INDEX).evaluate();
    double newY = arguments.get(NEW_Y_INDEX).evaluate();
    double currentX = turtle.getX();
    double currentY = turtle.getY();
    turtle.setX(newX);
    turtle.setY(newY);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return Math.sqrt(
        Math.pow((turtle.getX() - currentX), 2) + Math.pow((turtle.getY() - currentY), 2));
  }
}

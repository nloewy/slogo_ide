package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
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

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a SetPositionCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public SetPositionCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the SetPositionCommand, setting the turtle's position to the specified coordinates.
   *
   * @param arguments a list of nodes representing the arguments for this command (containing two
   *                  nodes with the x and y coordinates)
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the distance traveled by the turtle
   */
  @Override
  public double execute(List<Node> arguments, int index)
       {
    modelState.setOuter(false);
    double newX = arguments.get(0).evaluate();
    double newY = arguments.get(1).evaluate();
    Turtle turtle = modelState.getTurtles().get(index);
    double currentX = turtle.getX();
    double currentY = turtle.getY();
    turtle.setX(newX);
    turtle.setY(newY);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return MathUtils.dist(turtle.getX(), turtle.getY(), currentX, currentY);
  }
}

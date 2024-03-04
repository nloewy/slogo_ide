package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The LeftCommand class represents the command to rotate the turtle to the left by a specified
 * number of degrees. It sets the turtle's heading to the left of its current heading by the
 * specified angle.
 *
 * @author Noah Loewy
 */
public class LeftCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a LeftCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public LeftCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the LeftCommand, rotating the turtle to the left by the specified number of degrees.
   *
   * @param arguments a list of nodes representing the arguments for this command
   * @return the number of degrees the turtle turned left by
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double degrees = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(1);
    turtle.setHeading(turtle.getHeading() - degrees);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return degrees;
  }
}

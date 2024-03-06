package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The SetHeadingCommand class represents the command to set the turtle's heading to a specified
 * angle. It sets the turtle's heading to the given angle and calculates the minimum angle of
 * rotation to reach the new heading.
 *
 * @author Noah Loewy
 */
public class SetHeadingCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a SetHeadingCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public SetHeadingCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the SetHeadingCommand, setting the turtle's heading to the specified angle.
   *
   * @param arguments a list of nodes representing the arguments for this command (containing one
   *                  node with the angle to set)
   * @param turtleId  the id of the turtle currently active
   * @return the minimum angle of rotation needed to reach the new heading
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.setOuter(false);
    double newHeading = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(turtleId);
    double oldHeading = turtle.getHeading();
    turtle.setHeading(newHeading);
    double clockwiseTurn = (newHeading - oldHeading + 360) % 360;
    double counterclockwiseTurn = (oldHeading - newHeading + 360) % 360;
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return Math.min(Math.abs(clockwiseTurn), Math.abs(counterclockwiseTurn));
  }
}

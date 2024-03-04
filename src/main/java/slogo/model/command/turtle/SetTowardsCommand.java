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
 * The SetTowardsCommand class represents the command to set the turtle's heading towards a specified position.
 * It sets the turtle's heading towards the specified position and calculates the minimum angle of rotation needed to reach that heading.
 *
 * <p>This command requires two arguments: the x-coordinate and the y-coordinate of the target position.</p>
 *
 * @author Noah Loewy
 */
public class SetTowardsCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 2;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a SetTowardsCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener the listener for state change events
   */
  public SetTowardsCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the SetTowardsCommand, setting the turtle's heading towards the specified position.
   *
   * @param arguments a list of nodes representing the arguments for this command (containing two nodes with the x and y coordinates of the target position)
   * @return the minimum angle of rotation needed to reach the new heading
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double locationX = arguments.get(0).evaluate();
    double locationY = arguments.get(1).evaluate();
    Turtle turtle = modelState.getTurtles().get(0);
    double dx = locationX - turtle.getX();
    double dy = locationY - turtle.getY();
    double targetHeading = MathUtils.toDegrees(Math.atan2(dx, dy));
    double currentHeading = turtle.getHeading();
    double clockwiseTurn = Math.abs((targetHeading - currentHeading + 360) % 360);
    double counterclockwiseTurn = Math.abs((currentHeading - targetHeading + 360) % 360);
    double minTurn = Math.min(clockwiseTurn, counterclockwiseTurn);
    turtle.setHeading(targetHeading);
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return minTurn;
  }
}

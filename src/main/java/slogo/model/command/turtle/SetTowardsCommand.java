package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The SetTowardsCommand class represents the command to set the turtle's heading towards a
 * specified position. It sets the turtle's heading towards the specified position and calculates
 * the minimum angle of rotation needed to reach that heading.
 *
 * @author Noah Loewy
 */
public class SetTowardsCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 2;
  private final SlogoListener listener;

  /**
   * Constructs a SetTowardsCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public SetTowardsCommand(ModelState modelState, SlogoListener listener) {
    this.listener = listener;
  }

  /**
   * Executes the SetTowardsCommand, setting the turtle's heading towards the specified position.
   *
   * @param arguments a list of nodes representing the arguments for this command (containing two
   *                  nodes with the x and y coordinates of the target position)
   * @param turtle    the id of the turtle currently active   * @return the minimum angle of
   *                  rotation needed to reach the new heading
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {

    double locationX = arguments.get(0).evaluate();
    double locationY = arguments.get(1).evaluate();

    double dx = locationX - turtle.getX();
    double dy = locationY - turtle.getY();
    double targetHeading = Math.atan2(dx, dy) * 180 / Math.PI;
    double currentHeading = turtle.getHeading();
    double clockwiseTurn = Math.abs((targetHeading - currentHeading + 360) % 360);
    double counterclockwiseTurn = Math.abs((currentHeading - targetHeading + 360) % 360);
    double minTurn = Math.min(clockwiseTurn, counterclockwiseTurn);
    turtle.setHeading(((targetHeading % 360) + 360) % 360);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return minTurn;
  }
}

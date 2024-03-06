package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The ForwardCommand class represents the forward movement command for the turtle. It moves the
 * turtle forward by a specified number of pixels. The movement is based on the current heading of
 * the turtle. The new position is calculated using trigonometric functions.
 *
 * @author Noah Loewy
 */
public class ForwardCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs an instance of ForwardCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public ForwardCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the forward movement command for the turtle.
   *
   * @param arguments a list containing a single node representing the number of pixels to move
   *                  forward
   * @param turtleId  the id of the turtle currently active
   * @return the number of pixels moved forward
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.setOuter(false);
    double pixels = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(turtleId);
    turtle.setX(turtle.getX() + pixels * Math.sin(MathUtils.toRadians(turtle.getHeading())));
    turtle.setY(turtle.getY() - pixels * Math.cos(MathUtils.toRadians(turtle.getHeading())));
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return pixels;
  }
}

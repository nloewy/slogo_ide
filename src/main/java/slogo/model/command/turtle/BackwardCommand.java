package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The BackwardCommand class represents a command that moves the turtle backward by a specified
 * number of pixels. It calculates the new position of the turtle based on its current heading and
 * updates the turtle's state accordingly.
 *
 * @author Noah Loewy
 */
public class BackwardCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs an instance of BackwardCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public BackwardCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Moves the turtle backward by the specified number of pixels.
   *
   * @param arguments a list of nodes representing arguments. The one node is the distance moved
   *                  backwards.
   * @param turtleId  the id of the turtle currently active
   * @return the number of pixels moved backward
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.setOuter(false);
    double pixels = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(turtleId);
    double newX = turtle.getX() - pixels * Math.sin(Math.toRadians(turtle.getHeading()));
    double newY = turtle.getY() + pixels * Math.cos(Math.toRadians(turtle.getHeading()));
    turtle.setX(newX);
    turtle.setY(newY);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return pixels;
  }
}

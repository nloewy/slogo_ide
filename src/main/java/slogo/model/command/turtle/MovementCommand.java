package slogo.model.command.turtle;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;


/**
 * The MovementCommand class allows the turtle to move forward or backwards.
 *
 * @author Noah Loewy
 */

public abstract class MovementCommand implements Command {

  private final ModelState modelState;
  private final SlogoListener listener;
  private final boolean fwd;

  /**
   * Constructs a new MovementCommand.
   *
   * @param modelState The model state containing information about the turtle.
   * @param listener   The listener to notify about the updated turtle state.
   * @param fwd        A boolean value indicating whether the movement is forward (true) or backward
   *                   (false).
   */

  public MovementCommand(ModelState modelState, SlogoListener listener, boolean fwd) {
    this.modelState = modelState;
    this.listener = listener;
    this.fwd = fwd;
  }

  /**
   * Executes the movement command.
   *
   * @param arguments A list of arguments for the movement command.
   * @param turtleId  The ID of the turtle executing the command.
   * @return The number of pixels moved.
   */

  @Override
  public double execute(List<Node> arguments, int turtleId) {

    double pixels = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(turtleId);
    turtle.setX(
        turtle.getX() + (fwd ? 1 : -1) * pixels * Math.sin(Math.toRadians(turtle.getHeading())));
    turtle.setY(
        turtle.getY() + (fwd ? -1 : 1) * pixels * Math.cos(Math.toRadians(turtle.getHeading())));
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return pixels;
  }
}

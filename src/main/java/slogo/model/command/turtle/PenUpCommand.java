package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The PenUpCommand class represents the command to lift the turtle's pen up. It sets the turtle's
 * pen to the up position, disabling drawing when it moves.
 *
 * @author Noah Loewy
 */
public class PenUpCommand implements Command {

  /**
   * The number of arguments this command expects.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a PenUpCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public PenUpCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the PenUpCommand, lifting the turtle's pen up.
   *
   * @param arguments a list of nodes representing the arguments for this command (empty for this
   *                  command)
   * @return 0.0 to indicate that the pen is up and successful execution
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    Turtle turtle = modelState.getTurtles().get(1);
    turtle.setPen(false);
    listener.onUpdateTurtleState(modelState.getTurtles().get(1).getImmutableTurtle());
    return 0.0;
  }
}

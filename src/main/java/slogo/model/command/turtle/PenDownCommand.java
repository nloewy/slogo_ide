package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The PenDownCommand class represents the command to put the turtle's pen down. It sets the
 * turtle's pen to the down position, enabling it to draw when it moves.
 *
 * @author Noah Loewy
 */
public class PenDownCommand implements Command {

  /**
   * The number of arguments this command expects.
   */

  public static final int NUM_ARGS = 0;

  private final ModelState modelState;
  private final SlogoListener listener;

  /**
   * Constructs a PenDownCommand with the given model state and listener.
   *
   * @param modelState the model state containing information about the turtle
   * @param listener   the listener for state change events
   */
  public PenDownCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  /**
   * Executes the PenDownCommand, putting the turtle's pen down.
   *
   * @param arguments a list of nodes representing the arguments for this command (empty for this
   *                  command)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return 1.0 to indicate that the pen is down and successful execution
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    Turtle turtle = modelState.getTurtles().get(index);
    turtle.setPen(true);
    listener.onUpdateTurtleState(turtle.getImmutableTurtle());
    return 1.0;
  }
}

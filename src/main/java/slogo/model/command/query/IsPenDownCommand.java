package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IsPenDownCommand class represents a command that checks whether the pen of the requested
 * turtle is down. It returns 1 if the pen of the requested turtle is down, and 0 otherwise.
 *
 * @author Noah Loewy
 */
public class IsPenDownCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  /**
   * Constructs an instance of IsPenDownCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public IsPenDownCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Checks whether the pen of the requested turtle is down.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return 1 if the pen of the requested turtle is down, 0 otherwise
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    Turtle turtle = modelState.getTurtles().get(index);
    if (turtle.getPen()) {
      return 1.0;
    }
    return 0.0;
  }
}

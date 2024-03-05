package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The HeadingCommand class represents a command that retrieves the heading of the requested turtle.
 * It returns the heading of the requested turtle in the model state.
 *
 * @author Noah Loewy
 */
public class HeadingCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  /**
   * Constructs an instance of HeadingCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public HeadingCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Retrieves the heading of the requested turtle.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the heading of the active turtle
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    return modelState.getTurtles().get(index).getHeading();
  }
}

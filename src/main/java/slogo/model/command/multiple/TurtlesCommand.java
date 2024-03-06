package slogo.model.command.multiple;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The TurtlesCommand class represents a command that retrieves the number of turtles in the current
 * workspace.
 *
 * @author Noah Loewy
 */
public class TurtlesCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  /**
   * Constructs an instance of TurtlesCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public TurtlesCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Retrieves the number of active turtles in the current workspace.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the number of active turtles in the current workspace
    */
  @Override
  public double execute(List<Node> arguments, int index) {
    return modelState.getTurtles().size();
  }
}

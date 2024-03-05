package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The NotCommand class represents the logical NOT operation. It evaluates a node and returns 1.0 if
 * the node's value is 0, otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class NotCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;
  private ModelState modelState;

  /**
   * Constructs an instance of NotCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public NotCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;

  }

  /**
   * Executes the logical NOT operation on the provided node.
   *
   * @param arguments a list containing a single node to be evaluated
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return 1.0 if the node's value is 0, otherwise returns 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
      return (arguments.get(0).evaluate()==0) ? 1.0 : 0.0;
  }
}

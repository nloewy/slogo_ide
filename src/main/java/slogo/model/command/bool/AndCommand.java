package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The AndCommand class represents the logical AND operation. It evaluates two nodes and returns 1.0
 * if both are true (non-zero), otherwise returns 0.0.
 *
 * @author Noah Loewy
 */

public class AndCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  /**
   * Constructs an instance of AndCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public AndCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the logical AND operation on the provided boolean expressions.
   *
   * @param arguments a list of nodes, which can each be evaluated
   * @return 1.0 if both expressions are non-zero, otherwise returns 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return (!(arguments.get(0).evaluate() == 0) && !(arguments.get(1).evaluate() == 0)) ? 1.0 : 0.0;
  }
}

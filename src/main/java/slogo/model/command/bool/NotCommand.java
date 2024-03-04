package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The NotCommand class represents the logical NOT operation.
 * It evaluates a node and returns 1.0 if the node's value is 0, otherwise returns 0.0.
 *
 * Constructs an instance of NotCommand with the given model state and listener. This constructor
 * does not actually do anything, and exists for the sake of consistency across commands.
 *
 * @author Noah Loewy
 */
public class NotCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of NotCommand with the given model state and listener.
   */
  public NotCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the logical NOT operation on the provided node.
   *
   * @param arguments a list containing a single node to be evaluated
   * @return 1.0 if the node's value is 0, otherwise returns 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    return (arg1 == 0) ? 1.0 : 0.0;
  }
}

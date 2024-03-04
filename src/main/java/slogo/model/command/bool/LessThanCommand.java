package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The LessThanCommand class represents the less than comparison operation.
 * It evaluates two nodes and returns 1.0 if the first node's value is less than the second node's value,
 * otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class LessThanCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  /**
   * Constructs an instance of LessThan with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   */
  public LessThanCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the less than comparison operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @return 1.0 if the first node's value is less than the second node's value,
   *         otherwise returns 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    return (arg1 < arg2) ? 1.0 : 0.0;
  }
}

package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The PowerCommand class represents the power mathematical operation.
 * It calculates the result of raising the first argument to the power of the second argument.
 *
 * @author Noah Loewy
 */
public class PowerCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  /**
   * Constructs an instance of PowerCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener the listener for state change events
   */
  public PowerCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the power mathematical operation.
   *
   * @param arguments a list containing two nodes which evaluate to the base and exponent
   * @return the result of raising the base to the power of the exponent
   * @throws InvalidOperandException if the result of the operation is undefined
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    double result = Math.pow(arg1, arg2);
    if (Double.isNaN(result) || result == Double.NEGATIVE_INFINITY ||
        result == Double.POSITIVE_INFINITY) {
      throw new InvalidOperandException("Result is operation is undefined");
    }
    return result;
  }
}

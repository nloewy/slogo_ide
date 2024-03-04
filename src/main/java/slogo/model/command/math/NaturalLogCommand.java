package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The NaturalLogCommand class represents the natural logarithm mathematical operation.
 * It calculates the natural logarithm of a given number.
 *
 * @author Noah Loewy
 */
public class NaturalLogCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of NaturalLogCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener the listener for state change events
   */
  public NaturalLogCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the natural logarithm mathematical operation.
   *
   * @param arguments a list containing a single node representing the number to calculate the natural logarithm of
   * @return the natural logarithm of the input number
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   * @throws IllegalArgumentException if the input number is non-positive
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    if (arg1 <= 0) {
      throw new InvalidOperandException("Input to log function must be non-negative");
    }
    return Math.log(arg1);
  }
}

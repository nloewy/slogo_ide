package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The QuotientCommand class represents the quotient mathematical operation. It calculates the
 * quotient of two given numbers.
 *
 * @author Noah Loewy
 */
public class QuotientCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  /**
   * Constructs an instance of QuotientCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public QuotientCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the quotient mathematical operation.
   *
   * @param arguments a list containing two nodes representing the dividend and divisor
   * @param index
   * @return the quotient of the two numbers
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   * @throws InvalidOperandException   if the divisor is zero
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException, InvalidOperandException {
    if (arguments.get(1).evaluate() == 0) {
      throw new InvalidOperandException("Divisor must be non-zero");
    }
    return arguments.get(0).evaluate() / arguments.get(1).evaluate();
  }
}

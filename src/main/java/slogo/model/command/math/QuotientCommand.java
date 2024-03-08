package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.DivideByZeroException;
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
    //DO NOTHING
  }

  /**
   * Executes the quotient mathematical operation.
   *
   * @param arguments a list containing two nodes representing the dividend and divisor
   * @param turtle    the id of the turtle currently active
   * @return the quotient of the two numbers
   * @throws DivideByZeroException if the divisor is zero
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) throws DivideByZeroException {
    if (arguments.get(1).evaluate() == 0) {
      throw new DivideByZeroException("Divisor must be non-zero");
    }
    return arguments.get(0).evaluate() / arguments.get(1).evaluate();
  }
}

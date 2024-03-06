package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.command.exceptions.UndefinedExponentException;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The PowerCommand class represents the power mathematical operation. It calculates the result of
 * raising the first argument to the power of the second argument.
 *
 * @author Noah Loewy
 */
public class PowerCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of PowerCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public PowerCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the power mathematical operation.
   *
   * @param arguments a list containing two nodes which evaluate to the base and exponent
   * @param turtleId  the id of the turtle currently active
   * @return the result of raising the base to the power of the exponent
   * @throws UndefinedExponentException if the result of the operation is undefined
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) throws UndefinedExponentException {
    modelState.setOuter(false);
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    double result = Math.pow(arg1, arg2);
    if (Double.isNaN(result) || result == Double.NEGATIVE_INFINITY ||
        result == Double.POSITIVE_INFINITY) {
      throw new UndefinedExponentException("Undefined Power Operation: Denominator of Power Function Evaluates to Zero");
    }
    return result;
  }
}

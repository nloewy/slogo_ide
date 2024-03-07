package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.DivideByZeroException;
import slogo.model.node.Node;

/**
 * The RemainderCommand class represents the remainder mathematical operation. It calculates the
 * remainder of the division of two numbers.
 *
 * @author Noah Loewy
 */
public class RemainderCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of RemainderCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public RemainderCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the remainder mathematical operation.
   *
   * @param arguments a list containing two nodes representing the dividend and divisor
   * @param turtleId  the id of the turtle currently active
   * @return the remainder of the division of the dividend by the divisor
   * @throws DivideByZeroException if the divisor is zero
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) throws DivideByZeroException {
    modelState.setOuter(false);
    if (arguments.get(1).evaluate() == 0) {
      throw new DivideByZeroException("Divisor must be Non-Zero");
    }
    return arguments.get(0).evaluate() % arguments.get(1).evaluate();
  }
}

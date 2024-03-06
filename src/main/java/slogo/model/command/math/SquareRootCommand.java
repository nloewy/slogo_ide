package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The SquareRootCommand class represents the square root mathematical operation. It calculates the
 * square root of a given number.
 *
 * @author Noah Loewy
 */
public class SquareRootCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;
  private final ModelState modelState;

  /**
   * Constructs an instance of SquareRootCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public SquareRootCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the square root mathematical operation.
   *
   * @param arguments a list containing a single node representing the number to calculate the
   *                  square root of
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the square root of the input number
   */
  @Override
  public double execute(List<Node> arguments, int index) {
    modelState.setOuter(false);
    double arg1 = arguments.get(0).evaluate();
    if (arg1 < 0) {
      throw new InvalidOperandException("Operand must be non-negative");
    }
    return Math.sqrt(arg1);
  }
}

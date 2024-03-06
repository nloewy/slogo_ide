package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The RandomRangeCommand class represents the random range mathematical operation. It generates a
 * random number within a specified range.
 *
 * @author Noah Loewy
 */
public class RandomRangeCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of RandomRangeCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public RandomRangeCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the random range mathematical operation.
   *
   * @param arguments a list containing two nodes representing the minimum and maximum values of the
   *                  range
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return a random number within the specified range
    * @throws InvalidOperandException   if the minimum value is greater than the maximum value
   */
  @Override
  public double execute(List<Node> arguments, int index) throws InvalidOperandException {
    modelState.outer = false;
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    if (arg1 > arg2) {
      throw new InvalidOperandException("Min must be less than Max");
    }
    return arg1 + (arg2 - arg1) * Math.random();
  }
}

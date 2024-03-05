package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.Node;

/**
 * The RandomCommand class represents the random mathematical operation. It generates a random
 * number between 0 (inclusive) and a specified maximum value (exclusive).
 *
 * @author Noah Loewy
 */
public class RandomCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of RandomCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public RandomCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the random mathematical operation.
   *
   * @param arguments a list containing a single node representing the maximum value
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return a random number between 0 (inclusive) and the specified maximum value (exclusive)
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   * @throws InvalidOperandException   if the maximum value is negative
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException, InvalidOperandException {
    double arg1 = arguments.get(0).evaluate();
    if (arg1 < 0) {
      throw new InvalidOperandException("Max must be positive");
    }
    return Math.random() * arg1;
  }
}

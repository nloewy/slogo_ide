package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidRandomInputException;
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
  private final ModelState modelState;

  /**
   * Constructs an instance of RandomCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public RandomCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the random mathematical operation.
   *
   * @param arguments a list containing a single node representing the maximum value
   * @param turtleId  the id of the turtle currently active
   * @return a random number between 0 (inclusive) and the specified maximum value (exclusive)
   * @throws InvalidRandomInputException if the maximum value is negative
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) throws InvalidRandomInputException {
    modelState.setOuter(false);
    double arg1 = arguments.get(0).evaluate();
    if (arg1 < 0) {
      throw new InvalidRandomInputException("Input to Random Function must be non-negative");
    }
    return Math.random() * arg1;
  }
}

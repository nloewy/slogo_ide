package slogo.model.command.math;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The SumCommand class represents the sum mathematical operation. It calculates the sum of two
 * given numbers.
 *
 * @author Noah Loewy
 */
public class SumCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  private final ModelState modelState;

  /**
   * Constructs an instance of SumCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public SumCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the sum mathematical operation.
   *
   * @param arguments a list containing two nodes representing the numbers to be added
   * @param turtleId  the id of the turtle currently active
   * @return the sum of the two input numbers
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.outer = false;
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    return arg1 + arg2;
  }
}

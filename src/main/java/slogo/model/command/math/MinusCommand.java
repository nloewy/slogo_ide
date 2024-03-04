package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The MinusCommand class represents the unary minus mathematical operation.
 * It calculates the negation of a given number.
 *
 * @author Noah Loewy
 */
public class MinusCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of MinusCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener the listener for state change events
   */
  public MinusCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the unary minus mathematical operation.
   *
   * @param arguments a list containing a single node representing the number to negate
   * @return the negation of the input number
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).evaluate();
    return -arg1;
  }
}

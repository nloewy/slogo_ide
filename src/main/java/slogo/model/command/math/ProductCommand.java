package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The ProductCommand class represents the product mathematical operation. It calculates the product
 * of two given numbers.
 *
 * @author Noah Loewy
 */
public class ProductCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of ProductCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public ProductCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the product mathematical operation.
   *
   * @param arguments a list containing two nodes representing the numbers to be multiplied
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the product of the two numbers
   */
  @Override
  public double execute(List<Node> arguments, int index)
       {
    modelState.setOuter(false);
    double arg1 = arguments.get(0).evaluate();
    double arg2 = arguments.get(1).evaluate();
    return arg1 * arg2;
  }
}

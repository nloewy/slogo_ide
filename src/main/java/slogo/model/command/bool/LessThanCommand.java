package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The LessThanCommand class represents the less than comparison operation. It evaluates two nodes
 * and returns 1.0 if the first node's value is less than the second node's value, otherwise returns
 * 0.0.
 *
 * @author Noah Loewy
 */
public class LessThanCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final double TOLERANCE = .001;

  private ModelState modelState;

  /**
   * Constructs an instance of LessThanCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public LessThanCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;

  }

  /**
   * Executes the less than comparison operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return 1.0 if the first node's value is less than the second node's value, otherwise returns
   * 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    return (arguments.get(0).evaluate() - TOLERANCE < arguments.get(1).evaluate()) ? 1.0 : 0.0;
  }
}

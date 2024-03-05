package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IfCommand class represents the "if" control structure.
 * <p>
 * It evaluates a condition and executes a set of commands if the condition is true.
 *
 * @author Noah Loewy
 */
public class IfCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private ModelState modelState;

  /**
   * Constructs an instance of IfCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public IfCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the "if" control structure.
   *
   * @param arguments a list containing two nodes: the first node contains the condition to
   *                  evaluate, and the second node contains the command nodes to execute if the
   *                  condition is true
   * @param index the index of the turtle in the list at the top of getActiveTurtles() stack

   * @return the result of the last evaluated command if the condition is true, otherwise 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */

  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    double arg1 = arguments.get(0).evaluate();
    Node toExecute = arguments.get(1);
    if (arg1 != 0) {
      return toExecute.evaluate();
    }
    return 0.0;
  }

}
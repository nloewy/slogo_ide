package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IfElseCommand class represents the "ifelse" control structure.
 * <p>
 * It evaluates a condition and executes a set of commands if the condition is true, otherwise, it
 * executes a different set of commands.
 *
 * @author Noah Loewy
 */
public class IfElseCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 3;
  private final ModelState modelState;

  /**
   * Constructs an instance of IfElseCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public IfElseCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the "ifelse" control structure.
   *
   * @param arguments a list containing three nodes: the first node contains the condition to
   *                  evaluate, the second node contains the command nodes to execute if the
   *                  condition is true, and the third node contains the command nodes to execute if
   *                  the condition is false
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the result of the last evaluated command if the condition is true, otherwise the result
   * of the last evaluated command in the else branch, or 0.0 if there's no else branch
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    double condition = arguments.get(0).evaluate();
    Node toExecuteIfTrue = arguments.get(1);
    Node toExecuteIfFalse = arguments.get(2);
    if (condition != 0.0) {
      return toExecuteIfTrue.evaluate();
    } else {
      return toExecuteIfFalse.evaluate();
    }
  }
}

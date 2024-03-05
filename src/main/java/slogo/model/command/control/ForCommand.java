package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The ForCommand class represents the "for" control structure.
 * <p>
 * It iterates over a range of values, executing a set of commands for each value.
 *
 * @author Noah Loewy
 */
public class ForCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of ForCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public ForCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the "for" control structure.
   *
   * @param arguments a list containing two nodes: the first list node contains the variable name,
   *                  start value, end value, and increment value, and the second list node contains
   *                  the command nodes to execute
   * @param index
   * @return the result of the last evaluated command in the loop
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    String variableName = arguments.get(0).getChildren().get(0).getToken();
    double start = arguments.get(0).getChildren().get(1).evaluate();
    double end = arguments.get(0).getChildren().get(2).evaluate();
    double increment = arguments.get(0).getChildren().get(3).evaluate();
    Node commands = arguments.get(1);
    double res = 0.0;
    for (double i = start; i <= end; i += increment) {
      modelState.getVariables().put(variableName, i);
      res = commands.evaluate();
      modelState.getVariables().remove(variableName);
    }
    return res;
  }
}

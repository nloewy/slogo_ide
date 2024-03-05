package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The DoTimesCommand class represents the "do.times" control structure.
 * <p>
 * It repeats a set of commands a specified number of times, each time setting a variable to the
 * current iteration count.
 *
 * @author Noah Loewy
 */
public class DoTimesCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;

  /**
   * Constructs an instance of DoTimesCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public DoTimesCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the "do.times" control structure.
   *
   * @param arguments a list containing two nodes: the first list node contains the variable name
   *                  and the number of iterations, and the second list node contains the command
   *                  nodes that should be executed
   * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
   * @return the result of the last evaluated command in the loop
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    modelState.outer = false;
    String variableName = arguments.get(0).getChildren().get(0).getToken();
    double end = arguments.get(0).getChildren().get(1).evaluate();
    Node commands = arguments.get(1);
    double res = 0.0;
    for (double i = 1; i <= end; i += 1) {
      modelState.getVariables().put(variableName, i);
      res = commands.evaluate();
    }
    return res;
  }
}

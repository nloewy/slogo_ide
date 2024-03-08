package slogo.model.command.control;

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
  private static final int VARIABLE_NAME_INDEX = 0;
  private static final int NUM_TIMES_INDEX = 1;

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
   * @param turtleId  the id of the turtle currently active
   * @return the result of the last evaluated command in the loop
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    Node loopData = arguments.get(0);
    Node commands = arguments.get(1);
    String variableName = loopData.getChildren().get(VARIABLE_NAME_INDEX).getToken();
    double val = 0.0;
    for (double idx = 1; idx <= loopData.getChildren().get(NUM_TIMES_INDEX).evaluate(); idx += 1) {
      modelState.getVariables().put(variableName, idx);
      val = commands.evaluate();
    }
    return val;
  }
}

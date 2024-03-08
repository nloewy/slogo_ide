package slogo.model.command.control;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The RepeatCommand class represents the "repeat" control structure. It repeats a set of commands a
 * specified number of times.
 *
 * @author Noah Loewy
 */
public class RepeatCommand extends LoopCommand implements Command  {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  private final ModelState modelState;
  private static final int NUM_TIMES_INDEX = 0;
  private static final int COMMAND_INDEX = 1;
  private static final String VARIABLE_NAME = ":repcount";

  /**
   * Constructs an instance of RepeatCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public RepeatCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  /**
   * Executes the "repeat" control structure, each time setting a special variable ":repcount" to
   * the current iteration count.
   *
   * @param arguments a list containing two nodes: the first node represents the number of times to
   *                  repeat, and the second node represents the commands to repeat.
   * @param turtleId  the id of the turtle currently active
   * @return the result of the last evaluated command in the loop
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    double end = arguments.get(NUM_TIMES_INDEX).evaluate();
    Node commands = arguments.get(COMMAND_INDEX);
    return super.runLoop(1, end, 1, commands, modelState, VARIABLE_NAME);
  }
}


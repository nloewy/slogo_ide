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
public class RepeatCommand implements Command  {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  private final ModelState modelState;
  private final LoopCommandHandler loopHandler;

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
    loopHandler = new LoopCommandHandler();
  }

  /**
   * Executes the "repeat" control structure, then calling the loopHandler to execute the logic
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
    loopHandler.setLoopParameters(end);
    return loopHandler.runLoop(commands, modelState, VARIABLE_NAME);
  }
}


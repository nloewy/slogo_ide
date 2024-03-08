package slogo.model.command.control;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
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
  private static final int VARIABLE_NAME_INDEX = 0;
  private static final int START_INDEX = 1;
  private static final int END_INDEX = 2;
  private static final int INCREMENT_INDEX = 3;
  private final ModelState modelState;
  private final LoopCommandHandler loopHandler;

  /**
   * Constructs an instance of ForCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public ForCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    loopHandler = new LoopCommandHandler();
  }

  /**
   * Executes the "for" control structure, then calling the loopHandler to execute the logic
   *
   * @param arguments a list containing two nodes: the first list node contains the variable name,
   *                  start value, end value, and increment value, and the second list node contains
   *                  the command nodes to execute
   * @param turtle    the id of the turtle currently active
   * @return the result of the last evaluated command in the loop
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    Node loopData = arguments.get(0);
    Node commands = arguments.get(1);
    String variableName = loopData.getChildren().get(VARIABLE_NAME_INDEX).getToken();
    double start = loopData.getChildren().get(START_INDEX).evaluate();
    double end = loopData.getChildren().get(END_INDEX).evaluate();
    double increment = loopData.getChildren().get(INCREMENT_INDEX).evaluate();
    loopHandler.setLoopParameters(start, end, increment);
    return loopHandler.runLoop(commands, modelState, variableName);
  }
}

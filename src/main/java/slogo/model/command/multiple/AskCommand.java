package slogo.model.command.multiple;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The AskCommand class represents a command that given a turtle id(s), marks them as active for a
 * sequence of commands, then goes back to the previously active turtles.
 *
 * @author Noah Loewy
 */
public class AskCommand extends InformCommand {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final int ASK_IDS_INDEX = 0;
  private static final int COMMANDS_INDEX = 1;

  private final SlogoListener myListener;
  private final ModelState modelState;

  /**
   * Constructs an instance of AskCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public AskCommand(ModelState modelState, SlogoListener listener) {
    super();
    this.modelState = modelState;
    myListener = listener;
  }

  /**
   * Executes the command to activate turtles with specified IDs, creating new turtles if needed,
   * runs the given commands, and then goes back to previously active turtle.
   *
   * @param arguments a list of nodes representing the arguments passed to the command
   * @param turtle    the id of the turtle currently active
   * @return result of last command run by the last turtle.
   */

  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    List<Integer> askedTurtles = super.informTurtles(arguments.get(ASK_IDS_INDEX),
        modelState, myListener);
    modelState.getActiveTurtles().add(askedTurtles);
    double val = arguments.get(COMMANDS_INDEX).evaluate();
    modelState.getActiveTurtles().pop();
    return val;

  }

}



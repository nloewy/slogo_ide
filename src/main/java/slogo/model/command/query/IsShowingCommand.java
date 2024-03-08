package slogo.model.command.query;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IsShowingCommand class represents a command that checks whether the requested turtle is
 * showing. It returns 1 if the requested turtle is showing, and 0 otherwise.
 *
 * @author Noah Loewy
 */
public class IsShowingCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  /**
   * Constructs an instance of IsShowing with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public IsShowingCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Checks whether the requested turtle is showing.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param turtle    the id of the turtle currently active
   * @return 1 if the requested turtle is showing, 0 otherwise
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    if (turtle.getVisible()) {
      return 1.0;
    }
    return 0.0;
  }
}

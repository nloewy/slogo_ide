package slogo.model.command.bool;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The OrCommand class represents the logical OR operation. It evaluates two nodes and returns 1.0
 * if at least one of the nodes' values is non-zero, otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class OrCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final double TOLERANCE = .001;

  /**
   * Constructs an instance of OrCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public OrCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the logical OR operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @param turtle    the id of the turtle currently active
   * @return 1.0 if at least one of the nodes' values is non-zero, otherwise returns 0.0
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {

    return (!(Math.abs(arguments.get(0).evaluate()) <= TOLERANCE) ||
        !(Math.abs(arguments.get(1).evaluate()) <= TOLERANCE)) ? 1.0 : 0.0;
  }
}

package slogo.model.command.bool;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The GreaterEqualCommand class represents the greater than or equal to comparison operation. It
 * evaluates two nodes and returns 1.0 if the first node's value is greater than or equal to the
 * second node's value, otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class GreaterEqualCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final double TOLERANCE = .001;

  /**
   * Constructs an instance of GreaterEqualCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public GreaterEqualCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the greater than or equal to comparison operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @param turtleId  the id of the turtle currently active
   * @return 1.0 if the first node's value is greater than or equal to the second node's value,
   * otherwise returns 0.0
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    return (arguments.get(0).evaluate() + TOLERANCE >= arguments.get(1).evaluate()) ? 1.0 : 0.0;
  }
}


package slogo.model.command.bool;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The LessEqualCommand class represents the less than or equal to comparison operation. It
 * evaluates two nodes and returns 1.0 if the first node's value is less than or equal to the second
 * node's value, otherwise returns 0.0.
 *
 * @author Noah Loewy
 */
public class LessEqualCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final double TOLERANCE = .001;
  private final ModelState modelState;


  /**
   * Constructs an instance of LessEqualCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public LessEqualCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;

  }

  /**
   * Executes the less than or equal to comparison operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @param turtleId  the id of the turtle currently active
   * @return 1.0 if the first node's value is less than or equal to the second node's value,
   * otherwise returns 0.0
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.outer = false;
    return (arguments.get(0).evaluate() - TOLERANCE <= arguments.get(1).evaluate()) ? 1.0 : 0.0;
  }
}

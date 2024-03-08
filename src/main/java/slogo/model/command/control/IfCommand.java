package slogo.model.command.control;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IfCommand class represents the "if" control structure.
 * <p>
 * It evaluates a condition and executes a set of commands if the condition is true.
 *
 * @author Noah Loewy
 */
public class IfCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;
  private static final int CONDITION_INDEX = 0;
  private static final int TRUE_BRANCH_INDEX = 1;

  /**
   * Constructs an instance of IfCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public IfCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the "if" control structure.
   *
   * @param arguments a list containing two nodes: the first node contains the condition to
   *                  evaluate, and the second node contains the command nodes to execute if the
   *                  condition is true
   * @param turtle    the id of the turtle currently active
   * @return the result of the last evaluated command if the condition is true, otherwise 0.0
   */

  @Override
  public double execute(List<Node> arguments, Turtle turtle) {

    double evaluatedCondition = arguments.get(CONDITION_INDEX).evaluate();
    Node trueBranch = arguments.get(TRUE_BRANCH_INDEX);
    if (evaluatedCondition != 0) {
      return trueBranch.evaluate();
    }
    return 0.0;
  }

}

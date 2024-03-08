package slogo.model.command.control;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The IfElseCommand class represents the "ifelse" control structure.
 * <p>
 * It evaluates a condition and executes a set of commands if the condition is true, otherwise, it
 * executes a different set of commands.
 *
 * @author Noah Loewy
 */
public class IfElseCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 3;
  private static final int CONDITION_INDEX = 0;
  private static final int TRUE_BRANCH_INDEX = 1;
  private static final int FALSE_BRANCH_INDEX = 2;


  /**
   * Constructs an instance of IfElseCommand with the given model state and listener. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public IfElseCommand(ModelState modelState, SlogoListener listener) {
    //DO NOTHING
  }

  /**
   * Executes the "ifelse" control structure.
   *
   * @param arguments a list containing three nodes: the first node contains the condition to
   *                  evaluate, the second node contains the command nodes to execute if the
   *                  condition is true, and the third node contains the command nodes to execute if
   *                  the condition is false
   * @param turtle    the id of the turtle currently active
   * @return the result of the last evaluated command if the condition is true, otherwise the result
   * of the last evaluated command in the else branch, or 0.0 if there's no else branch
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    double evaluatedCondition = arguments.get(CONDITION_INDEX).evaluate();
    Node trueBranch = arguments.get(TRUE_BRANCH_INDEX);
    Node falseBranch = arguments.get(FALSE_BRANCH_INDEX);
    return (evaluatedCondition != 0.0) ? trueBranch.evaluate() : falseBranch.evaluate();
  }

}


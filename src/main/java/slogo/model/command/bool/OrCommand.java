package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The OrCommand class represents the logical OR operation.
 * It evaluates two nodes and returns 1.0 if at least one of the nodes' values is non-zero,
 * otherwise returns 0.0.
 *
 * Constructs an instance of OrCommand with the given model state and listener. This constructor
 * does not actually do anything, and exists for the sake of consistency across commands.
 *
 * @author Noah Loewy
 */
public class OrCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  /**
   * Constructs an instance of OrCommand with the given model state and listener.
   */
  public OrCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the logical OR operation on the provided nodes.
   *
   * @param arguments a list of nodes representing values to be compared
   * @return 1.0 if at least one of the nodes' values is non-zero, otherwise returns 0.0
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return (arguments.get(0).evaluate() != 0) || (arguments.get(1).evaluate() != 0) ? 1.0 : 0.0;
  }
}

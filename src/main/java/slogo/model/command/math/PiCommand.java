package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The PiCommand class represents the mathematical constant pi. It returns the value of pi when
 * executed.
 *
 * @author Noah Loewy
 */
public class PiCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  /**
   * Constructs an instance of PiCommand with the given model state and listener. This constructor
   * does not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public PiCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Executes the PiCommand, returning the mathematical constant pi.
   *
   * @param arguments a list containing no nodes, as PiCommand requires no arguments
   * @param index
   * @return the value of pi
   * @throws InvocationTargetException if an error occurs during execution
   * @throws IllegalAccessException    if access is denied during execution
   */
  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {
    return Math.PI;
  }
}

package slogo.model.command.turtle;

import slogo.model.ModelState;
import slogo.model.api.SlogoListener;

/**
 * The ForwardCommand class represents the forward movement command for the turtle. It informs the
 * MovementCommand object that the turtle is moving forward, and then the MovementCommand's (its
 * parent) version of execute is used.
 *
 * @author Noah Loewy
 */
public class ForwardCommand extends MovementCommand {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 1;

  /**
   * Constructs an instance of ForwardCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public ForwardCommand(ModelState modelState, SlogoListener listener) {
    super(modelState, listener, true);
  }
}

package slogo.model.command.query;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The XcoordinateCommand class represents a command that retrieves the X coordinate of the
 * requested turtle. It returns the X coordinate of the requested turtle.
 *
 * @author Noah Loewy
 */
public class XcoordinateCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 0;

  /**
   * Constructs an instance of XcoordinateCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public XcoordinateCommand(ModelState modelState, SlogoListener listener) {
  }

  /**
   * Retrieves the X coordinate of the requested turtle.
   *
   * @param arguments a list of nodes representing arguments (not used in this command)
   * @param turtle    the id of the turtle currently active
   * @return the X coordinate of the requested turtle
   */
  @Override
  public double execute(List<Node> arguments, Turtle turtle) {
    return turtle.getX();
  }
}

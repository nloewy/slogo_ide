package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetBackgroundCommand implements Command {

  /** The SetBackgroundCommand represents a command that alters the background index of the
   * workspace. It returns the heading of the requested turtle in the model state.
   *
   * @author Noah Loewy
   */

    /**
     * The number of arguments this command requires.
     */
    public static final int NUM_ARGS = 1;

    private final ModelState modelState;

    /**
     * Constructs an instance of SetBackgroundCommand with the given model state and listener.
     *
     * @param modelState the model state
     * @param listener   the listener for state change events
     */
    public SetBackgroundCommand(ModelState modelState, SlogoListener listener) {
      this.modelState = modelState;
    }

    /**
     * Updates the background color of the workspace.
     *
     * @param arguments a list of nodes representing arguments (not used in this command)
     * @param index     the index of the turtle in the list at the top of getActiveTurtles() stack
     * @return the heading of the active turtle
     */
    @Override
    public double execute(List<Node> arguments, int index) {
      modelState.outer = false;
      for(Turtle turtle : modelState.getTurtles().values()) {
        turtle.setBg(arguments.get(0).evaluate());
      }
      return modelState.getTurtles().get(index).getHeading();
    }
  }


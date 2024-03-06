package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetPaletteCommand implements Command {

  /** The SetPaletteCommand represents a command that adds a new indexed color to the workspace.
   *
   * @author Noah Loewy
   */

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 4;

  private final ModelState modelState;
  private final SlogoListener myListener;

  /**
   * Constructs an instance of SetPenCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */
  public SetPaletteCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;
  }

  /**
   * Updates the Background color of the workspace.
   *
   * @param arguments a list of nodes representing arguments
   * @param turtleId  the id of the turtle currently active
   * @return the background color of the workspace
   */
  @Override
  public double execute(List<Node> arguments, int turtleId) {
    modelState.setOuter(false);
    int index = (int) Math.round(arguments.get(0).evaluate());
    int red = (int) Math.round(arguments.get(1).evaluate());
    int green = (int) Math.round(arguments.get(2).evaluate());
    int blue = (int) Math.round(arguments.get(3).evaluate());
    modelState.getPalette().put(index, List.of(new Integer[]{red, green, blue}));
    myListener.onUpdatePalette(modelState.getPalette());
    return index;
  }
}


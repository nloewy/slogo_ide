package slogo.model.command.display;

import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.exceptions.InvalidRgbValueException;
import slogo.model.node.Node;

/**
 * The SetPaletteCommand represents a command that adds a new indexed color to the workspace.
 *
 * @author Noah Loewy
 */

public class SetPaletteCommand implements Command {


  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 4;

  private final ModelState modelState;
  private final SlogoListener myListener;
  private static final int PALETTE_INDEX = 0;
  private static final int RED_INDEX = 1;
  private static final int GREEN_INDEX = 2;
  private static final int BLUE_INDEX = 3;

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

    int index = (int) Math.round(arguments.get(PALETTE_INDEX).evaluate());
    int red = validateRgb((int) Math.round(arguments.get(RED_INDEX).evaluate()));
    int green = validateRgb((int) Math.round(arguments.get(GREEN_INDEX).evaluate()));
    int blue = validateRgb((int) Math.round(arguments.get(BLUE_INDEX).evaluate()));
    modelState.getPalette().put(index, List.of(new Integer[]{red, green, blue}));
    myListener.onUpdatePalette(modelState.getPalette());
    return index;
  }

  private int validateRgb(int val) throws InvalidRgbValueException {
    if (val >= 0 && val <= 255) {
      return val;
    }
    throw new InvalidRgbValueException("RGB Val Not in Range", Integer.toString(val));
  }
}


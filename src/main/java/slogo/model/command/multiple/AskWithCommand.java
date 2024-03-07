package slogo.model.command.multiple;

import java.util.ArrayList;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
 * The AskWithCommand class represents a command that given an expression marks only turtles that
 * satisfy the expression as temporarily active for a sequence of commands, then goes back to the
 * previously active turtles.
 *
 * @author Noah Loewy
 */

public class AskWithCommand implements Command {

  /**
   * The number of arguments this command requires.
   */
  public static final int NUM_ARGS = 2;

  private final SlogoListener myListener;
  private final ModelState modelState;

  /**
   * Constructs an instance of AskWithCommand with the given model state and listener.
   *
   * @param modelState the model state
   * @param listener   the listener for state change events
   */

  public AskWithCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    myListener = listener;
  }

  /**
   * Executes the command to activate turtles with specified condition, creating new turtles if
   * needed, runs the given commands, and then goes back to previously active turtle.
   *
   * @param arguments a list of nodes representing the arguments passed to the command
   * @param turtleId  the id of the turtle currently active
   * @return result of last command run by the last turtle.
   */

  @Override
  public double execute(List<Node> arguments, int turtleId) {
    List<Integer> tempList = new ArrayList<>();
    for (int i : modelState.getTurtles().keySet()) {
      modelState.setCurrTurtle(i);
      modelState.setOuter(false);
      int id = (int) Math.round(arguments.get(0).evaluate());
      if (id != 0) {
        tempList.add(i);
      }
    }
    modelState.getActiveTurtles().add(tempList);
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    double val = 0.0;
    for (Node node : arguments.get(1).getChildren()) {
      for (int i : tempList) {
        modelState.setOuter(false);
        modelState.setCurrTurtle(i);
        val = node.evaluate();
      }
    }

    modelState.getActiveTurtles().pop();
    myListener.onSetActiveTurtles(modelState.getActiveTurtles().peek());
    modelState.setOuter(false);
    return val;

  }
}


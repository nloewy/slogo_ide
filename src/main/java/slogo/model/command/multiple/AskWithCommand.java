package slogo.model.command.multiple;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

/**
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
   * @param index     the index of the command in the list of commands
   * @return result of last command run by the last turtle.
   * @throws InvocationTargetException if an error occurs during command execution
   * @throws IllegalAccessException    if access to the method or field is denied
   */

  @Override
  public double execute(List<Node> arguments, int index)
      throws InvocationTargetException, IllegalAccessException {

    modelState.outer = false;
    List<Integer> tempList = new ArrayList<>();
    for (Integer id : modelState.getTurtles().keySet()) {
      List<Integer> oneNodeList = new ArrayList<>();
      oneNodeList.add(id);
      modelState.getActiveTurtles().add(oneNodeList);
      if (arguments.get(0).evaluate() != 0) {
        tempList.add(id);
      }
      modelState.getActiveTurtles().pop();
    }

    double val = 0;
    if (index == modelState.getActiveTurtles().peek().size() - 1) {

      modelState.getActiveTurtles().add(tempList);

      for (int i = 0; i < modelState.getActiveTurtles().peek().size(); i++) {
        for (Node node : arguments.get(1).getChildren()) {
          List<Integer> oneNodeList = new ArrayList<>();
          oneNodeList.add(modelState.getActiveTurtles().peek().get(i));
          modelState.getActiveTurtles().add(oneNodeList);
          val = node.evaluate();
          modelState.getActiveTurtles().pop();
        }
      }
      modelState.getActiveTurtles().pop();
      return val;
    }

    return 0;
  }
}


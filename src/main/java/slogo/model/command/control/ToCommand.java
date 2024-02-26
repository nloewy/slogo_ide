package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ToCommand extends Command {

  public static double NUM_ARGS = 3;
  private final ModelState modelState;
  private final SlogoListener listener;

  public ToCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    String name = arguments.get(0).getToken();
    List<Node> variableNodes = arguments.get(0).getChildren();
      modelState.getUserDefinedCommands().put(name, arguments);
      return 1.0;
  }

  @Override
  public int getNumArgs() {
    return 3;
  }

  public void notifyListener(SlogoListener listener, double value) {

    //super.notifyListener(listener, value);
  }
}

//parse
//r there any user defined commands
//
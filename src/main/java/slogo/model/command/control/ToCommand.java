package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class ToCommand extends Command {


  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    Node userDefinedNode = arguments.get(0);
    return modelState -> {
      modelState.getUserDefinedCommands().put(userDefinedNode.getToken(), arguments);
      return 1.0;
    };
  }


  public void notifyListener(SlogoListener listener, double value) {

    //super.notifyListener(listener, value);
  }
}

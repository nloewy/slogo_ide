package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class UserCommand extends Command {



  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    Node listNode = arguments.get(0);
    return modelState -> {
      return 1.0;
    };
  }

  @Override
  public int getNumArgs() {
    return -1;
  }

  public void notifyListener(SlogoListener listener, double value) {

    //super.notifyListener(listener, value);
  }
}

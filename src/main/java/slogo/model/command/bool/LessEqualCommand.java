package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.command.Command;

public class LessEqualCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    double arg2 = arguments.get(1).getValue();
    return modelState -> {
      return (arg1 <= arg2) ? 1.0 : 0.0;
    };
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  }
   */

}

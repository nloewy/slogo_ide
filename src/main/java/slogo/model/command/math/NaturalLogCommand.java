package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class NaturalLogCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    return modelState -> {
      if (arg1 <= 0) {
        throw new IllegalArgumentException("expr must be non-negative");
      }
      return Math.log(arg1);
    };
  }

  @Override
  public int getNumArgs() {
    return 1;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  }
   */

}

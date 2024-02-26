package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class RandomRangeCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    double arg2 = arguments.get(1).getValue();
    return modelState -> {
      if (arg1 > arg2) {
        throw new IllegalArgumentException("Min must be less than Max");
      }
      return arg1 + (arg2 - arg1) * Math.random();
    };
  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  }
   */

}

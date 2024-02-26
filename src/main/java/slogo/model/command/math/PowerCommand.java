package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.command.Command;

public class PowerCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    double arg2 = arguments.get(1).getValue();
    return modelState -> {
      double result = Math.pow(arg1, arg2);
      if (Double.isNaN(result) || result == Double.NEGATIVE_INFINITY ||
          result == Double.POSITIVE_INFINITY) {
        throw new IllegalArgumentException("Result is operation is undefined");
      }
      return result;
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

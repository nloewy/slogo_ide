package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.command.Command;

public class TangentCommand extends Command {

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    return modelState -> {
      if (Math.abs(arg1 % 180) == 90) {
        throw new ArithmeticException("Illegal Value for Tangent Function");
      }
      return Math.tan(MathUtils.toRadians(arg1));
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

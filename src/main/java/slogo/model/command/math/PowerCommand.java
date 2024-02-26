package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class PowerCommand extends Command {
  public static final int NUM_ARGS = 2;
  private final ModelState modelState;
  private final SlogoListener listener;

  public PowerCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    double arg2 = arguments.get(1).getValue();
    double result = Math.pow(arg1, arg2);
    if (Double.isNaN(result) || result == Double.NEGATIVE_INFINITY ||
        result == Double.POSITIVE_INFINITY) {
      throw new IllegalArgumentException("Result is operation is undefined");
    }
    return result;
  }
}

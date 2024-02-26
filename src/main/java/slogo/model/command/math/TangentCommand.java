package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class TangentCommand implements Command {
  public static final int NUM_ARGS = 1;
  private final ModelState modelState;
  private final SlogoListener listener;

  public TangentCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    if (Math.abs(arg1 % 180) == 90) {
      throw new ArithmeticException("Illegal Value for Tangent Function");
    }
    return Math.tan(MathUtils.toRadians(arg1));
  }
}

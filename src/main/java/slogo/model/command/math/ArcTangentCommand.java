package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ArcTangentCommand implements Command {

  public static final int NUM_ARGS = 1;
  public ArcTangentCommand(ModelState modelState, SlogoListener listener) {}

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double arg1 = arguments.get(0).getValue();
    return MathUtils.toDegrees(Math.atan(arg1));
  }
}

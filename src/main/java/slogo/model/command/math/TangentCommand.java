package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class TangentCommand extends Command {

  private final Turtle myTurtle;

  public TangentCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double angle = MathUtils.toRadians(arguments.get(0).getValue());
    if (Math.abs(arguments.get(0).getValue() % Math.PI) == Math.PI / 2) {
      return 0.0;
      //throw new IllegalArgumentException("Illegal Value for Tangent Function");
      //if (arguments.get(0) % (2 * Math.PI) > Math.PI) {
      //  return Double.MIN_VALUE;
      //} else {
      //  return Double.MAX_VALUE;
      //}
      //}
    }
    return Math.tan(angle);
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

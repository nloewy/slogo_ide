package slogo.model.command.math;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class TangentCommand extends Command {

  public double execute(List<Double> arguments) {
    double angle = MathUtils.toRadians(arguments.get(0));
    if (Math.abs(arguments.get(0) % Math.PI) == Math.PI / 2) {
      //throw new IllegalArgumentException("Illegal Value for Tangent Function");
      if (arguments.get(0) % (2 * Math.PI) > Math.PI) {
        return Double.MIN_VALUE;
      } else {
        return Double.MAX_VALUE;
      }
    }
    return Math.tan(angle);
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

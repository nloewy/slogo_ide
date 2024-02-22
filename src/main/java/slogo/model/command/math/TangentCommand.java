package slogo.model.command.math;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class TangentCommand extends Command {


  public double execute(List<Double> arguments) {
    return Math.tan(MathUtils.toRadians(arguments.get(0)));

  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

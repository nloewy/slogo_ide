package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class PowerCommand extends Command {

  public double execute(List<Double> arguments) {
    double base = arguments.get(0);
    double exp = arguments.get(1);
    return Math.pow(base, exp);

  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

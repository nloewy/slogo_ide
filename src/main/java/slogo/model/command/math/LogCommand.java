package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class LogCommand extends Command {


  public double execute(List<Double> arguments) {
    double val = arguments.get(0);
    if (val <= 0) {
      return 0.0;
      //    throw new IllegalArgumentException("expr must be non-negative");

    }
    return Math.log(val);
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

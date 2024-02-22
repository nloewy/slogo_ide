package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class QuotientCommand extends Command {


  public double execute(List<Double> arguments) {
    if (arguments.get(1) == 0) {
      return 0;
      //  throw new ArithmeticException("Divisor must be non-zero");
      //  return 0.0
      //}
    }
    return arguments.get(0) / arguments.get(1);
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

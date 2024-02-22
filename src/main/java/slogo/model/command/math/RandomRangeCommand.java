package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class RandomRangeCommand extends Command {

  public double execute(List<Double> arguments) {
    if (arguments.get(0) > arguments.get(1)) {
      return 0.0;
      //    throw new IllegalArgumentException("Min must be less than Max");
    }
    return arguments.get(0) + (arguments.get(1) - arguments.get(0)) * Math.random();
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

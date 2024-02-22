package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class SquareRootCommand extends Command {


  public double execute(List<Double> arguments) {
    double val = Math.max(arguments.get(0), 0);
    //if (val<0) {
    //    throw new IllegalArgumentException("expr must be non-negative");
    //  }
    return Math.sqrt(val);
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

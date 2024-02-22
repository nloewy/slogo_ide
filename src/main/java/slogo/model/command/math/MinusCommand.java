package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class MinusCommand extends Command {


  public double execute(List<Double> arguments) {
    return -1 * arguments.get(0);
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class DifferenceCommand extends Command {


  public double execute(List<Double> arguments) {
    return arguments.get(0) - arguments.get(1);

  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.command.Command;

public class RandomCommand extends Command {


  public double execute(List<Double> arguments) {
    double rand = arguments.get(0);
    if (rand<0) {return 0.0;}
    return Math.random()*rand;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

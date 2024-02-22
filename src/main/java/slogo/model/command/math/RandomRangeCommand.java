package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RandomRangeCommand extends Command {

  private final Turtle myTurtle;

  public RandomRangeCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Double> arguments) {
    if (arguments.get(0) > arguments.get(1)) {
      return 0.0;
      //    throw new IllegalArgumentException("Min must be less than Max");
    }
    return arguments.get(0) + (arguments.get(1) - arguments.get(0)) * Math.random();
  }
  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

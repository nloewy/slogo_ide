package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class DifferenceCommand extends Command {

  private final Turtle myTurtle;

  public DifferenceCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Double> arguments) {
    return arguments.get(0) - arguments.get(1);

  }

  public int getNumberOfArgs() {
    return 2;
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

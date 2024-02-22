package slogo.model.command.math;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SquareRootCommand extends Command {

  private final Turtle myTurtle;

  public SquareRootCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Double> arguments) {
    double val = Math.max(arguments.get(0), 0);
    //if (val<0) {
    //    throw new IllegalArgumentException("expr must be non-negative");
    //  }
    return Math.sqrt(val);
  }

  public int getNumberOfArgs() {
    return 1;
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

package slogo.model.command.math;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class NaturalLogCommand extends Command {

  private final Turtle myTurtle;

  public NaturalLogCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Node> arguments) {
    double val = arguments.get(0);
    if (val <= 0) {
      return 0.0;
      //    throw new IllegalArgumentException("expr must be non-negative");

    }
    return Math.log(val);
  }
  public int getNumberOfArgs() {
    return 1;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

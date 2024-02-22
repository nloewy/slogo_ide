package slogo.model.command.math;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SumCommand extends Command {

  private final Turtle myTurtle;

  public SumCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Node> arguments) {
    return arguments.get(0).getValue() + arguments.get(1).getValue();

  }

  public int getNumberOfArgs() {
    return 2;
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

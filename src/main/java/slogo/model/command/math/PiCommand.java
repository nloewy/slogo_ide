package slogo.model.command.math;

import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PiCommand extends Command {

  private final Turtle myTurtle;

  public PiCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments) {
    return Math.PI;

  }

  public int getNumberOfArgs() {
    return 0;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

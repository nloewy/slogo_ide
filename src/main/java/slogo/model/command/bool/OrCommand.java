package slogo.model.command.bool;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class OrCommand extends Command {

  private final Turtle myTurtle;

  public OrCommand(Turtle turtle) {
    myTurtle = turtle;
  }
  public double execute(List<Double> arguments) {
    return (!(arguments.get(0) == 0) || !(arguments.get(1) == 0)) ? 1 : 0;

  }

  public int getNumberOfArgs() {
    return 2;
  }
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

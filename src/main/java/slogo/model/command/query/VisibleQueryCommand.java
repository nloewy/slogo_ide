package slogo.model.command.query;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class VisibleQueryCommand extends Command {

  private Turtle myTurtle;

  public VisibleQueryCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Double> arguments) {
    if (myTurtle.getVisible()) {return 1.0;}
    return 0.0;

  }
  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}

package slogo.model.command.query;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PenDownQueryCommand extends Command {

  private final Turtle myTurtle;

  public PenDownQueryCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Double> arguments) {
    if (myTurtle.getPen()) {
      return 1.0;
    }
    return 0.0;

  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}

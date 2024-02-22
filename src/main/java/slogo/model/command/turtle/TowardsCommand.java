package slogo.model.command.turtle;

import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class TowardsCommand extends Command {

  private Turtle myTurtle;

  public TowardsCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Double> arguments) {
    double dx = arguments.get(0) - myTurtle.getX();
    double dy = arguments.get(1) - myTurtle.getY();
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(Math.toDegrees(Math.atan2(dy, dx)) - 90);
    return (myTurtle.getHeading() - oldHeading + 360) % 360;
  }
  public void notifyListener(SlogoListener listener, double value) {
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle(), value);
  }


}

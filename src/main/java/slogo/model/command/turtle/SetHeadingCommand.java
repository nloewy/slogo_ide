package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetHeadingCommand extends Command {

  private final Turtle myTurtle;

  public SetHeadingCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newHeading = arguments.get(0).getValue();
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(newHeading);
    double clockwiseTurn = (newHeading - oldHeading + 360) % 360;
    double counterclockwiseTurn = (oldHeading - newHeading + 360) % 360;
    return Math.min(Math.abs(clockwiseTurn), Math.abs(counterclockwiseTurn));

  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }


}

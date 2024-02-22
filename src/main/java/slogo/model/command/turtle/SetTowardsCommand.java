package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetTowardsCommand extends Command {

  private final Turtle myTurtle;

  public SetTowardsCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double dx = arguments.get(0).getValue() - myTurtle.getX();
    double dy = arguments.get(1).getValue() - myTurtle.getY();
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(Math.toDegrees(Math.atan2(dy, dx)) - 90);
    return (myTurtle.getHeading() - oldHeading + 360) % 360;
  }

  public int getNumberOfArgs() {
    return 2;
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }


}

package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
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
    double targetHeading = MathUtils.toDegrees(Math.atan2(dx, dy));
    double currentHeading = myTurtle.getHeading();
    double clockwiseTurn = Math.abs((targetHeading - currentHeading + 360) % 360);
    double counterclockwiseTurn = Math.abs((currentHeading - targetHeading + 360) % 360);
    double minTurn = Math.min(clockwiseTurn, counterclockwiseTurn);
    myTurtle.setHeading(targetHeading);
    return minTurn;
  }


  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }


}

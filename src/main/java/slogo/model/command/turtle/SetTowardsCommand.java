package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetTowardsCommand extends Command {

  private final ModelState modelState;
  private final SlogoListener listener;

  public SetTowardsCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double xPt = arguments.get(0).getValue();
    double yPt = arguments.get(1).getValue();
    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      double dx = xPt - turtle.getX();
      double dy = yPt - turtle.getY();
      double targetHeading = MathUtils.toDegrees(Math.atan2(dx, dy));
      double currentHeading = turtle.getHeading();
      double clockwiseTurn = Math.abs((targetHeading - currentHeading + 360) % 360);
      double counterclockwiseTurn = Math.abs((currentHeading - targetHeading + 360) % 360);
      double minTurn = Math.min(clockwiseTurn, counterclockwiseTurn);
      turtle.setHeading(targetHeading);
      return minTurn;
    };
  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  /**@Override public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
  listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
   */

}

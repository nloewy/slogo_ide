package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetTowardsCommand implements Command {

  public static final int NUM_ARGS = 2;
  private final ModelState modelState;
  private final SlogoListener listener;

  public SetTowardsCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double locationX = arguments.get(0).getValue();
    double locationY = arguments.get(1).getValue();
    Turtle turtle = modelState.getTurtles().get(0);
    double dx = locationX - turtle.getX();
    double dy = locationY - turtle.getY();
    double targetHeading = MathUtils.toDegrees(Math.atan2(dx, dy));
    double currentHeading = turtle.getHeading();
    double clockwiseTurn = Math.abs((targetHeading - currentHeading + 360) % 360);
    double counterclockwiseTurn = Math.abs((currentHeading - targetHeading + 360) % 360);
    double minTurn = Math.min(clockwiseTurn, counterclockwiseTurn);
    turtle.setHeading(targetHeading);
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return minTurn;
  }
}

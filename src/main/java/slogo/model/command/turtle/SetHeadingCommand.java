package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetHeadingCommand implements Command {

  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  public SetHeadingCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newHeading = arguments.get(0).getValue();
    Turtle turtle = modelState.getTurtles().get(0);
    double oldHeading = turtle.getHeading();
    turtle.setHeading(newHeading);
    double clockwiseTurn = (newHeading - oldHeading + 360) % 360;
    double counterclockwiseTurn = (oldHeading - newHeading + 360) % 360;
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return Math.min(Math.abs(clockwiseTurn), Math.abs(counterclockwiseTurn));
  }
}

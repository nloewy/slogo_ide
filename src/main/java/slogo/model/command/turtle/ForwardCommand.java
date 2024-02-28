package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ForwardCommand implements Command {

  public static final int NUM_ARGS = 1;
  private final ModelState modelState;
  private final SlogoListener listener;

  public ForwardCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double pixels = arguments.get(0).getValue();
    Turtle turtle = modelState.getTurtles().get(0);
    turtle.setX(turtle.getX() + pixels * Math.sin(MathUtils.toRadians(turtle.getHeading())));
    turtle.setY(turtle.getY() + pixels * Math.cos(MathUtils.toRadians(turtle.getHeading())));
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return pixels;
  }
}

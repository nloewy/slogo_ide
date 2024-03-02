package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class SetPositionCommand implements Command {

  public static final int NUM_ARGS = 2;
  private final ModelState modelState;
  private final SlogoListener listener;

  public SetPositionCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newX = arguments.get(0).evaluate();
    double newY = arguments.get(1).evaluate();
    Turtle turtle = modelState.getTurtles().get(0);
    double currentX = turtle.getX();
    double currentY = turtle.getY();
    turtle.setX(newX);
    turtle.setY(newY);
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return MathUtils.dist(turtle.getX(), turtle.getY(), currentX, currentY);
  }
}

package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class BackwardCommand implements Command {

  public static final int NUM_ARGS = 1;

  private final ModelState modelState;
  private final SlogoListener listener;

  public BackwardCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double pixels = arguments.get(0).evaluate();
    Turtle turtle = modelState.getTurtles().get(0);
    double newX = turtle.getX() - pixels * Math.sin(Math.toRadians(turtle.getHeading()));
    double newY = turtle.getY() + pixels * Math.cos(Math.toRadians(turtle.getHeading()));
    turtle.setX(newX);
    turtle.setY(newY);
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return pixels;
  }
}

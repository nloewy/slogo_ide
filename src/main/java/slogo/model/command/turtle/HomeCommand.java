package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class HomeCommand implements Command {

  public static final int NUM_ARGS = 0;
  private final ModelState modelState;
  private final SlogoListener listener;

  public HomeCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments) {
    Turtle turtle = modelState.getTurtles().get(0);
    double currentX = turtle.getX();
    double currentY = turtle.getY();
    turtle.setX(0);
    turtle.setY(0);
    listener.onUpdateTurtleState(modelState.getTurtles().get(0).getImmutableTurtle());
    return MathUtils.dist(0, 0, currentX, currentY);
  }
}

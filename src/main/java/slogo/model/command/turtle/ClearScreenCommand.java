package slogo.model.command.turtle;

import java.util.List;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ClearScreenCommand implements Command {

  public static final int NUM_ARGS = 0;
  private final ModelState modelState;
  private final SlogoListener listener;

  public ClearScreenCommand(ModelState modelState, SlogoListener listener) {
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
    listener.onResetTurtle(modelState.getTurtles().get(0).getImmutableTurtle().id());
    return MathUtils.dist(0, 0, currentX, currentY);
  }
}




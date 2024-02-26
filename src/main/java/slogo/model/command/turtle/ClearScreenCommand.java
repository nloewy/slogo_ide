package slogo.model.command.turtle;

import java.util.List;
import java.util.function.Function;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class ClearScreenCommand extends Command {

  private final ModelState modelState;
  private final SlogoListener listener;

  public ClearScreenCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments) {
    return modelState -> {
      Turtle turtle = modelState.getTurtles().get(0);
      double currentX = turtle.getX();
      double currentY = turtle.getY();
      turtle.setX(0);
      turtle.setY(0);
      return MathUtils.dist(0, 0, currentX, currentY);
    };
  }

  @Override
  public int getNumArgs() {
    return 0;
  }

  /**
   @Override public void notifyListener(SlogoListener listener, double value) {
   super.notifyListener(listener, value);
   TurtleRecord turtleRecord = myTurtle.getImmutableTurtle();
   listener.onUpdateTurtleState(turtleRecord);
   listener.onResetTurtle(turtleRecord.id());
   }
   */
}




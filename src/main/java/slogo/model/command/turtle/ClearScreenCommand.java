package slogo.model.command.turtle;

import java.util.List;
import java.util.function.Function;
import slogo.mathutils.MathUtils;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class ClearScreenCommand extends Command {


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




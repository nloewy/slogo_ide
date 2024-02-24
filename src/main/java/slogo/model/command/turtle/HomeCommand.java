package slogo.model.command.turtle;

import java.util.List;
import java.util.Map;
import slogo.mathutils.MathUtils;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class HomeCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public HomeCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  @Override
  public double execute(List<Node> arguments) {
    double currentX = myTurtle.getX();
    double currentY = myTurtle.getY();
    myTurtle.setX(0);
    myTurtle.setY(0);
    return MathUtils.dist(0, 0, currentX, currentY);
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}

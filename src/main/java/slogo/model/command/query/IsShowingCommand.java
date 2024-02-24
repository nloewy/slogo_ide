package slogo.model.command.query;

import java.util.List;
import java.util.Map;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class IsShowingCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public IsShowingCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  public double execute(List<Node> arguments) {
    if (myTurtle.getVisible()) {
      return 1.0;
    }
    return 0.0;

  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }

}

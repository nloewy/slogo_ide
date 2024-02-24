package slogo.model.command.turtle;

import java.util.List;
import java.util.Map;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class HideTurtleCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public HideTurtleCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  @Override
  public double execute(List<Node> arguments) {
    myTurtle.setVisible(false);
    return 0.0;
  }

  @Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }


}

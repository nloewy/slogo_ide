package slogo.model.command.turtle;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PenDownCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public PenDownCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments) {
    myTurtle.setPen(true);
    return 1.0;
  }

  /**@Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
*/
}

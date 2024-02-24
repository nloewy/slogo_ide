package slogo.model.command.math;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class PiCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public PiCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  public Function<ModelState, Double> execute(List<Node> arguments) {
    return Math.PI;

  }

    /**@Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }
*/

}

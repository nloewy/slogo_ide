package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class SetHeadingCommand extends Command {

  private final Turtle myTurtle;
  private final Map<String, Double> myVariables;

  public SetHeadingCommand(Turtle turtle, Map<String, Double> variables) {
    myTurtle = turtle;
    myVariables = variables;
  }

  @Override
  public Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double newHeading = arguments.get(0).getValue();
    double oldHeading = myTurtle.getHeading();
    myTurtle.setHeading(newHeading);
    double clockwiseTurn = (newHeading - oldHeading + 360) % 360;
    double counterclockwiseTurn = (oldHeading - newHeading + 360) % 360;
    return Math.min(Math.abs(clockwiseTurn), Math.abs(counterclockwiseTurn));

  }

  /**@Override
  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
    listener.onUpdateTurtleState(myTurtle.getImmutableTurtle());
  }
*/

}

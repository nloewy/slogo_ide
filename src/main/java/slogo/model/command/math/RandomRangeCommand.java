package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RandomRangeCommand extends Command {

  private final Turtle myTurtle;

  public RandomRangeCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    if (arguments.get(0).getValue() > arguments.get(1).getValue()) {
      return 0.0;
      //    throw new IllegalArgumentException("Min must be less than Max");
    }
    return arguments.get(0).getValue() + (arguments.get(1).getValue() -
        arguments.get(0).getValue()) * Math.random();
  }

  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

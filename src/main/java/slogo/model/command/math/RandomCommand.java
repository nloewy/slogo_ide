package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RandomCommand extends Command {

  private final Turtle myTurtle;

  public RandomCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    double rand = Math.max(arguments.get(0).getValue(), 0);
    //if (rand<0) {
    //    throw new IllegalArgumentException("Max must be positive");
    //  }
    return Math.random() * rand;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class RepeatCommand extends Command {

  private final Turtle myTurtle;

  public RepeatCommand(Turtle turtle) {
    myTurtle = turtle;
  }

  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    for (int i = 0; i < arguments.get(0).getValue(); i++) {
      //arguments.get(1).getValue();

    }
    return 0;
  }

  public int getNumberOfArgs() {
    return 2;
  }

  public void notifyListener(SlogoListener listener, double value) {
    super.notifyListener(listener, value);
  }

}

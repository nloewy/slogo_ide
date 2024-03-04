package slogo.model.command.bool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class AndCommand implements Command {

  public static final int NUM_ARGS = 2;


  public AndCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return (!(arguments.get(0).evaluate() == 0) && !(arguments.get(1).evaluate() == 0)) ? 1.0 : 0.0;

  }
}

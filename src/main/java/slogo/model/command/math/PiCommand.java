package slogo.model.command.math;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class PiCommand implements Command {

  public static final int NUM_ARGS = 0;

  public PiCommand(ModelState modelState, SlogoListener listener) {
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return Math.PI;
  }
}

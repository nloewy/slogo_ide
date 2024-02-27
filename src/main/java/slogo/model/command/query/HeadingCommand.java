package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class HeadingCommand implements Command {

  public static final int NUM_ARGS = 0;
  private final ModelState modelState;
  private final SlogoListener listener;

  public HeadingCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.listener = listener;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return modelState.getTurtles().get(0).getHeading();
  }
}

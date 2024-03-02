package slogo.model.command.query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.node.Node;

public class XcoordinateCommand implements Command {

  public static final int NUM_ARGS = 0;

  private final ModelState modelState;

  public XcoordinateCommand(ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
  }

  @Override
  public double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException {
    return modelState.getTurtles().get(0).getX();

  }
}

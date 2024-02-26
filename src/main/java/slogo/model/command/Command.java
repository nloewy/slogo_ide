package slogo.model.command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.node.Node;

public abstract class Command {

  public abstract Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException;

  public abstract int getNumArgs();

  public void notifyListener(SlogoListener listener, double value) {
    listener.onReturn(value);
  }
}

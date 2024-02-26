package slogo.model.command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.SlogoListener;
import slogo.model.node.Node;

public abstract class Command {

  public abstract double execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException;

  public void notifyListener(SlogoListener listener, double value) {
    listener.onReturn(value);
  }
}

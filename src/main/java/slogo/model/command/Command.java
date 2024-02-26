package slogo.model.command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.node.Node;

public interface Command {
  double execute(List<Node> arguments) throws InvocationTargetException, IllegalAccessException;
}

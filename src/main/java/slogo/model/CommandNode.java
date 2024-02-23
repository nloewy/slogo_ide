package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import slogo.model.command.Command;

public class CommandNode extends Node {

  private final Command command;
  private final Method m;


  public CommandNode(String token, Turtle turtle)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(token);
    command = (Command) clazz.getDeclaredConstructor(Turtle.class).newInstance(turtle);
    m = clazz.getDeclaredMethod("execute", List.class);
  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    return (double) m.invoke(command, children);
  }
}

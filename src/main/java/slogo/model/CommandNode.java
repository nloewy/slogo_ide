package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import slogo.model.command.Command;
import slogo.model.command.turtle.LeftCommand;

public class CommandNode extends Node {
  private Object o;
  private Method m;
  public CommandNode(String token, Turtle turtle)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(token);
    o = clazz.getDeclaredConstructor(Turtle.class).newInstance(turtle);
    m = clazz.getDeclaredMethod("execute");  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    return (double) m.invoke(o, getChildren());
  }

}

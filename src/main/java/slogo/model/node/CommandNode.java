package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
import slogo.model.SlogoModel;
import slogo.model.command.Command;

public class CommandNode extends Node {

  private final Command command;
  private final Method m;
  private final ModelState myModelState;
  private final String myToken;

  private SlogoListener myListener;

  public CommandNode(String token, ModelState modelState, SlogoListener listener)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    super();

    myListener = listener;
    myToken = "slogo.model.command." + token + "Command";
    Class<?> clazz = Class.forName(myToken);
    command = (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class).newInstance(modelState, listener);
    m = clazz.getDeclaredMethod("execute", List.class);
    myModelState = modelState;
  }

@Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    return (double) m.invoke(command, children);
  }

  @Override
  public int getNumArgs()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(myToken);
    Command command = (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class).newInstance(myModelState, myListener);
    Method method = clazz.getDeclaredMethod("getNumArgs");
    return (int) method.invoke(command);
  }

}

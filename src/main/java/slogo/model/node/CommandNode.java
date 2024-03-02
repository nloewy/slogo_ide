package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.IncompleteClassException;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;

public class CommandNode extends Node {

  private Command command;
  private Method m;
  private final String myToken;
  private ModelState myModelState;

  public CommandNode(String token, ModelState modelState)
      throws ClassNotFoundException {
    super();
    myToken = "slogo.model.command." + token + "Command";
    myModelState = modelState;
  }


  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    try {
      Class<?> clazz = Class.forName(myToken);
      command = (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class)
          .newInstance(myModelState, getListener());
      m = clazz.getDeclaredMethod("execute", List.class);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("Command Class Not Found");
    }
    List<Node> children = getChildren();
    try {
      return (double) m.invoke(command, children);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new InvalidOperandException(e.getCause().getMessage());
    }
  }

  public int getNumArgs() throws IncompleteClassException {
    try {
      return (int) Class.forName(myToken).getField("NUM_ARGS").get(null);
    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      throw new IncompleteClassException(
          "Error getting number of arguments. Field NUM_ARGS not found for " + myToken);
    }
  }

  public String getToken() {
    return myToken;
  }
}

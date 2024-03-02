package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.IncompleteClassException;
import slogo.model.api.InsufficientArgumentsException;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;

public class CommandNode extends Node {

  private Command command;
  private Method m;
  private final String myToken;
  private ModelState myModelState;
  private String myFullToken;

  private static final String BASE_PACKAGE = "slogo.model.command.";
  public CommandNode(String token, ModelState modelState)
      throws ClassNotFoundException {
    super();
    myToken =  token ;
    myModelState = modelState;
  }


  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    try {
      Class<?> clazz = Class.forName(BASE_PACKAGE + myToken + "Command");
      command = (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class)
          .newInstance(myModelState, getListener());
      m = clazz.getDeclaredMethod("execute", List.class);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("Command Class Not Found. Previous Commands successfully executed");
    }
    List<Node> children = getChildren();
    if(getNumArgs() != getChildren().size()) {
      throw new InsufficientArgumentsException(getToken() + " expected " + getNumArgs() + " arguments. Previous (non-nested) commands executed successfully");
    }
    try {
      return (double) m.invoke(command, children);

    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      Class<? extends RuntimeException> causeClass = (Class<? extends RuntimeException>) e.getCause().getClass();
      if (causeClass.equals(InsufficientArgumentsException.class)) {
        throw new InsufficientArgumentsException(e.getCause().getMessage());
      }
      throw new InvalidOperandException(e.getCause().getMessage());
    }
  }

  public int getNumArgs() throws IncompleteClassException {
    try {
      return (int) Class.forName(BASE_PACKAGE + myToken + "Command").getField("NUM_ARGS").get(null);
    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      throw new IncompleteClassException(
          "Error getting number of arguments. Field NUM_ARGS not found for " + myToken);
    }
  }

  public String getToken() {
    return myToken;
  }
}

package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import slogo.model.command.Command;
import slogo.model.command.control.ToCommand;
import slogo.model.command.control.UserCommand;

public class UserCommandNode extends Node {

  private String myToken;
  private ModelState myModelState;


  public UserCommandNode(String token, ModelState modelState)   {
    super();
    myToken = token;
    myModelState = modelState;
  }

  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    Command commandMaker = new UserCommand();
    return myModelState.applyCommandToModelState(commandMaker.execute(children));
  }

  public String getToken() {
    return myToken;
  }
}

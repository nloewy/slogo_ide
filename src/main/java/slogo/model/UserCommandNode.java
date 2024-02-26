package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.api.Node;

public class UserCommandNode extends Node {

  private String myToken;
  private ModelState myModelState;


  public UserCommandNode(String token, ModelState modelState)   {
    super();
    myToken = token;
    myModelState = modelState;
  }

  public double getValue() throws InvocationTargetException {
    List<Node> children = getChildren();
    List<Node> arguments =  myModelState.getUserDefinedCommands().get(children.get(0).getToken());
    return 5.0;
  }

  public String getToken() {
    return myToken;
  }
}

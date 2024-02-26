package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.node.Node;

public class UserCommandNode extends Node {

  private String myToken;
  private ModelState myModelState;

  public UserCommandNode(String token, ModelState modelState)   {
    super();
    myToken = token;
    myModelState = modelState;
  }

  @Override
  public double getValue() throws InvocationTargetException {
    List<Node> children = getChildren();
    List<Node> arguments =  myModelState.getUserDefinedCommands().get(children.get(0).getToken());
    return 5.0;
  }

  @Override
  public String getToken() {
    return myToken;
  }
}

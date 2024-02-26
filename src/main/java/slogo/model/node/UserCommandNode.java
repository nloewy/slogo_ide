package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.node.Node;

public class UserCommandNode extends Node {

  private String myToken;

  public UserCommandNode(String token, ModelState modelState)   {
    super();
    myToken = token;
  }

  @Override
  public double getValue() throws InvocationTargetException {
    return 0.0; //NEVER CALLED
  }

  @Override
  public String getToken() {
    return myToken;
  }
}

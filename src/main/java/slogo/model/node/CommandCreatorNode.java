package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;

public class CommandCreatorNode extends Node {

  private final String myToken;
  private final ModelState myModelState;
  private final int myNumArgs;
  private SlogoListener myListener;

  public CommandCreatorNode(String token, ModelState modelState, SlogoListener listener,
      int numArgs) {
    super();
    myModelState = modelState;
    myToken = token;
    myNumArgs = numArgs;
    myModelState.getUserDefinedCommands().put(myToken, myNumArgs);
    myListener = listener;
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    myModelState.getUserDefinedCommandNodes().put(myToken, children);
    System.out.println(this.toString());
    myListener.onCommand(this.toString(), true);
    return 1.0;
  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  public String getToken() {
    return myToken;
  }
}
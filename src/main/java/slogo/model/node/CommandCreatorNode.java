package slogo.model.node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import slogo.model.ModelState;
import slogo.model.SlogoListener;
public class CommandCreatorNode extends Node {

  private ModelState myModelState;
  private final String myToken;

  private SlogoListener myListener;
  private int myNumArgs;

  public CommandCreatorNode(String token, ModelState modelState, SlogoListener listener, int numArgs) {
    super();
    myListener = listener;
    myModelState = modelState;
    myToken = token;
    myNumArgs = numArgs;
    myModelState.getUserDefinedCommands().put(myToken, myNumArgs);
  }

  @Override
  public double getValue() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    myModelState.getUserDefinedCommandNodes().put(myToken, children);
    return 1.0;
  }

  @Override
  public int getNumArgs()   {
    return 2;
  }

  public String getToken() {
    return myToken;
  }
}
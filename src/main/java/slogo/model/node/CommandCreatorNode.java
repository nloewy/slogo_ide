package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.ModelState;

public class CommandCreatorNode extends Node {

  private final String myToken;
  private final ModelState myModelState;
  private final int myNumArgs;

  public CommandCreatorNode(String token, ModelState modelState, int numArgs) {
    super();
    myModelState = modelState;
    myToken = token;
    myNumArgs = numArgs;
    myModelState.getUserDefinedCommands().put(myToken, myNumArgs);
  }

  @Override
  public double evaluate() throws InvocationTargetException, IllegalAccessException {
    List<Node> children = getChildren();
    myModelState.getUserDefinedCommandNodes().put(myToken, children);
    getListener().onCommand(toString(), true);
    return 1.0;
  }

  @Override
  public int getNumArgs() {
    return 2;
  }

  public String getToken() {
    return "to " + myToken;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getToken());
    if (!getChildren().isEmpty()) {
      sb.append("\t").append(getChildren().get(0).getToken());
      for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
        sb.append("\t").append(getChildren().get(0).getChildren().get(i).getToken());
      }
      sb.append(toStringHelper(this, 0, true));

    }
    return sb.toString();
  }

  private String toStringHelper(Node node, int indent, boolean firstLevel) {
    StringBuilder sb = new StringBuilder();
    if (!firstLevel) {
      sb.append("\n");
      sb.append("\t".repeat(indent)).append(node.getToken());
    }
    for (int i = 0; i < node.getChildren().size(); i++) {
      if (firstLevel && i == 0) {
        continue;
      }
      sb.append(toStringHelper(node.getChildren().get(i), indent + 1, false));
    }
    return sb.toString();
  }
}
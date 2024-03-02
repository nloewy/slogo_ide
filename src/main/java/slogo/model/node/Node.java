package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import slogo.model.api.IncompleteClassException;

public abstract class Node {

  private final List<Node> myChildren = new ArrayList<>();

  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;

  public List<Node> getChildren() {
    return myChildren;
  }

  public void addChild(Node node) {
    myChildren.add(node);
  }

  public abstract String getToken();

  public int getNumArgs() throws IncompleteClassException {return 0;}

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Node child : getChildren()) {
      sb.append(toStringHelper(child, 0));
    }
    return sb.toString();
  }

  private String toStringHelper(Node node, int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append("\t".repeat(indent)).append(node.getToken());
    for (int i = 0; i < node.getChildren().size(); i++) {
      sb.append(toStringHelper(node.getChildren().get(i), indent + 1));
    }
    return sb.toString();
  }


}



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
    if(!firstLevel) {
      sb.append("\n");
      sb.append("\t".repeat(indent)).append(node.getToken());
    }
    for (int i = 0; i < node.getChildren().size(); i++) {
      if(firstLevel && i==0) {continue;}
      sb.append(toStringHelper(node.getChildren().get(i), indent + 1, false));
    }
    return sb.toString();
  }

}



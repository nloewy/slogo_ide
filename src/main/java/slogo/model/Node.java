package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Node {

  private List<Node> myChildren;

  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;

  public List<Node> getChildren() {
    return myChildren;
  }

  public void addChildren(Node node) {
    myChildren.add(node);
  }

}



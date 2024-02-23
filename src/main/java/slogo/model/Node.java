package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {

  private final List<Node> myChildren = new ArrayList<>();


  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;

  public List<Node> getChildren() {
    return myChildren;
  }

  public void addChildren(Node node) {
    myChildren.add(node);
  }

}



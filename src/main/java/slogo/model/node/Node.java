package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {

  private final List<Node> myChildren = new ArrayList<>();

  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;

  public List<Node> getChildren() {
    return myChildren;
  }

  public void addChild(Node node) {
    myChildren.add(node);
  }

  public String getToken() {
    return "";
  }

  public int getNumArgs()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    return 0;
  }
}


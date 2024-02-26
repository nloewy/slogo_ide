public abstract class Node {
  public abstract double getValue() throws InvocationTargetException, IllegalAccessException;
  public List<Node> getChildren() {
    return myChildren;
  }
  public void addChild(Node node) {
    myChildren.add(node);
  }
  public String getToken() {return null;}
}
public abstract class Command {
  public abstract Function<ModelState, Double> execute(List<Node> arguments)
      throws InvocationTargetException, IllegalAccessException;
  public abstract int getNumArgs();
  public void notifyListener(SlogoListener listener, double value) {
    listener.onReturn(value);
  }
}
public interface ModelListener {
  double applyCommandToModelState(Function<ModelState, Double> action);
}
public class ModelState implements ModelListener {
  public List<Turtle> getTurtles();
  public Map<String, Double> getVariables();
  public Map<String, List<Node>> getUserDefinedCommands();
  @Override
  public double applyCommandToModelState(Function<ModelState, Double> action);
}
public class Turtle {
  public double getY();
  public void setY(double myY);
  public double getX();
  public void setX(double myX);
  public double getHeading();
  public void setHeading(double myHeading);
  public TurtleRecord getImmutableTurtle();
  public boolean getPen();
  public void setPen(boolean b);
  public boolean getVisible();
  public void setVisible(boolean b);
  public int getId();
}




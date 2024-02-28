package slogo.model;

import java.lang.reflect.InvocationTargetException;
import slogo.model.api.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class Tester {
  public static void main (String [] args)
      throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
    Turtle myTurtle = null;
    ModelState model = new ModelState();
    SlogoListener myListener = new SlogoListener() {
      @Override
      public void onUpdateValue(String variableName, Number newValue) {

      }

      @Override
      public void onUpdateTurtleState(TurtleRecord turtleState) {

      }

      @Override
      public void onResetTurtle(int id) {

      }

      @Override
      public void onReturn(double value) {
      }
    };
    Node node = new CommandNode("math.RandomRange", model, myListener);
    node.addChild(new ConstantNode("-90.00000", null, myListener));
    node.addChild(new ConstantNode("-90.00100", null, myListener));
    try { node.getValue();}
    catch (InvalidOperandException e) {
      System.out.println(e.getMessage());
    }


  }

}

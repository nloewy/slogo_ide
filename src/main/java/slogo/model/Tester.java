package slogo.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import slogo.model.api.InvalidOperandException;
import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class Tester {

  public static void main(String[] args)
      throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, IOException {
    Turtle myTurtle = null;

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
      public void onReturn(double value, String string) {
      }

      @Override
      public void onCommand(String string, boolean userDefined) {

      }
    };
    SlogoModel myModel = new SlogoModel(myListener);
    myModel.parse("MAKE :CLASS 10 RANDOMTEXT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
    myModel.getModelstate().getTurtles().add(new Turtle(0));
    System.out.println(myModel.getModelstate().getVariables().get(":class"));


  }

}

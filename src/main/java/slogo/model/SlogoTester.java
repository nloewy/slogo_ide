package slogo.model;

import java.lang.reflect.InvocationTargetException;
import slogo.model.api.Model;
import slogo.model.api.TurtleRecord;

public class SlogoTester {

  public static void main(String[] args)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Model s = new SlogoModel(new SlogoListener() {
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
    });
    s.parse("FORWARD #LOL IFELSE PENUP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
  }
}

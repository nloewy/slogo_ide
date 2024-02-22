package slogo.model;

import slogo.model.api.Model;
import slogo.model.api.TurtleRecord;

public class SlogoTester {

  public static void main(String[] args) {
    Model s = new SlogoModel(new SlogoListener() {
      @Override
      public void onUpdateValue(String variableName) {

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
    s.parse("RIGHT IFELSE PENUP PLUS 50 30 PRODUCT 30 PLUS 1 1 FD 50 END END");
  }
}

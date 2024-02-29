package slogo.model.command;

import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;

public class CommandTest {

  protected SlogoListener myListener = new SlogoListener() {
    @Override
    public void onUpdateValue(String variableName, Number newValue) {
    }

    public void onUpdateTurtleState(TurtleRecord turtleState) {
    }

    public void onResetTurtle(int id) {
    }

    public void onReturn(double value) {
    }

    @Override
    public void onCommand(String string, boolean userDefined) {

    }
  };

}

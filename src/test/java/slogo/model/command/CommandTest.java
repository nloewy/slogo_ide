package slogo.model.command;

import slogo.model.api.SlogoListener;
import slogo.model.api.TurtleRecord;
import slogo.model.node.Node;

public class CommandTest {

  protected SlogoListener myListener = new SlogoListener() {
    @Override
    public void onUpdateValue(String variableName, Number newValue) {
    }

    public void onUpdateTurtleState(TurtleRecord turtleState) {
    }

    public void onResetTurtle(int id) {
    }

    public void onReturn(double value, String string) {
    }

    @Override
    public void onUserDefinedCommand(String string) {

    }
  };


  protected void dfsAddListener(Node node) {
    if(node != null) {
      node.addListener(myListener);
      for(Node child : node.getChildren()) {
        dfsAddListener(child);
      }
    }
  }

}

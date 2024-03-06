package slogo.model.command;

import java.util.List;
import java.util.Map;
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

    @Override
    public void onSetActiveTurtles(List<Integer> ids) {

    }

    @Override
    public void onUpdatePalette(Map<Integer, List<Integer>> pallette) {

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

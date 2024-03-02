package slogo.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.Model;
import slogo.model.api.SlogoListener;
import slogo.model.node.Node;


public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private final Parser parser;
  private ModelState modelstate;
  private Stack<String> myCommands;
  public SlogoModel(SlogoListener listener) throws IOException {
    modelstate = new ModelState();
    modelstate.getTurtles().add(new Turtle(1));
    myListener = listener;
    parser = new Parser(modelstate);
    myCommands = new Stack<>();

  }

  @Override
  public void parse(String input)
      throws InvocationTargetException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    Node root = parser.parse(input);
    dfsAddListener(root);
    double val = root.getValue();
    myCommands.push(input);
    myListener.onReturn(val, root.toString());
  }

  private void dfsAddListener(Node node) {
    if(node != null) {
      node.addListener(myListener);
      for(Node child : node.getChildren()) {
        dfsAddListener(child);
     }
    }
   }
  @Override
  public File loadXml(String path) {
    return null;
  }

  @Override
  public File saveXml(String path) {
    return null;
  }

  @Override
  public void resetModel() {
    modelstate = new ModelState();
  }

  //FOR TESTING PURPOSES ONLY
  ModelState getModelstate() {
    return modelstate;
  }

}






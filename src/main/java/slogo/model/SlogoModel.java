package slogo.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;
import slogo.model.api.InsufficientArgumentsException;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.Model;
import slogo.model.api.SlogoListener;
import slogo.model.node.ListNode;
import slogo.model.node.Node;


public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private final Parser parser;
  private ModelState modelState;
  private Stack<String> myCommands;
  public SlogoModel(SlogoListener listener) throws IOException {
    modelState = new ModelState();
    modelState.getTurtles().add(new Turtle(1));
    myListener = listener;
    parser = new Parser(modelState);
    myCommands = new Stack<>();

  }

  @Override
  public void parse(String input)
      throws InvocationTargetException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    Node root = new ListNode("[", modelState);
    try { parser.parse(input, root); }
    catch (InvalidCommandException | InvalidTokenException | InsufficientArgumentsException e ) {
      handleParseResult(input, root);
      throw e;
    }
    handleParseResult(input, root);
  }

  private void handleParseResult(String input, Node root)
      throws InvocationTargetException, IllegalAccessException {
    dfsAddListener(root);
    double val = -1;
    for(Node node : root.getChildren()) {
      val = node.evaluate();
    }
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
    modelState = new ModelState();
  }

  //FOR TESTING PURPOSES ONLY
  ModelState getModelstate() {
    return modelState;
  }

}






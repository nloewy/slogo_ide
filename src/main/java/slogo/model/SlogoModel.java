package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Stack;
import slogo.model.api.Model;
import slogo.model.api.SlogoException;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.InsufficientArgumentsException;
import slogo.model.exceptions.InvalidCommandException;
import slogo.model.exceptions.InvalidTokenException;
import slogo.model.node.ListNode;
import slogo.model.node.Node;


public class SlogoModel implements Model {

  public static final String RESOURCE_PATH = "src/main/resources/slogo/example/languages/";
  private final SlogoListener myListener;
  private final Parser parser;
  private final Stack<String> myCommands;
  private ModelState modelState;
  private final Properties prop;

  public SlogoModel(SlogoListener listener, String currentLanguage) throws IOException {
    modelState = new ModelState();
    modelState.getTurtles().add(new Turtle(1));
    myListener = listener;
    parser = new Parser(modelState, currentLanguage);
    myCommands = new Stack<>();
    prop = new Properties();
    File file = new File(RESOURCE_PATH + currentLanguage + ".properties");
    prop.load(new FileInputStream(file));


  }

  @Override
  public void parse(String input)
      throws InvocationTargetException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    if (input.isEmpty()) {
      return;
    }
    Node root = new ListNode("[", modelState);
    try {
      parser.parse(input, root);
    } catch (InvalidCommandException | InvalidTokenException | InsufficientArgumentsException e) {
      handleParseResult(input, root);
      SlogoException e2 = e;
      String template = (String) prop.getOrDefault(e.getClass().getSimpleName(), e.getMessage());
      String message = String.format(template, e2.getToken());
      throw new SlogoException(message, "");
    }
    handleParseResult(input, root);
  }

  private void handleParseResult(String input, Node root)
      throws InvocationTargetException, IllegalAccessException {
    dfsAddListener(root);
    double val = -1;
    for (Node node : root.getChildren()) {
      val = node.evaluate();
    }
    myCommands.push(input);
    myListener.onReturn(val, root.toString());

  }

  private void dfsAddListener(Node node) {
    if (node != null) {
      node.addListener(myListener);
      for (Node child : node.getChildren()) {
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






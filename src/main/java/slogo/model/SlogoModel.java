package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import slogo.model.api.Model;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;
import slogo.model.node.UserCommandNode;
import slogo.model.node.VariableNode;


public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private ModelState modelstate;

  private Map<String, String> commandMap;
  private Node currentNode;
  private int myIndex;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    modelstate.getTurtles().add(new Turtle(1));
    myListener = listener;
    myIndex = 0;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");
  }


  public void parse(String input)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    myIndex = 0;
    Stack<Node> nodeStack = new Stack<>();
    Node rootNode = new ListNode("[", modelstate, myListener); // Create a root node
    nodeStack.push(rootNode);
    while (myIndex < tokens.size()) {
      String token = tokens.get(myIndex);
      System.out.println(token);
      if (token.isEmpty() || token.startsWith("#")) {
        myIndex++;
        continue;
      }
      if (token.equals("[")) {
        while (!nodeStack.peek().equals(rootNode)
            && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
          nodeStack.pop();
        }
        Node newNode = new ListNode("[", modelstate, myListener);
        nodeStack.peek().addChild(newNode);
        nodeStack.push(newNode);
      } else if (token.equals("]")) {
        while (!nodeStack.peek().getToken().equals("[")) {
          nodeStack.peek().getToken();
          nodeStack.pop();
        }
        if (nodeStack.size() > 1) {
          nodeStack.pop();
        }
      } else {
        createNode(tokens);
        if (!nodeStack.peek().getToken().equals("[")
            && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
          while (!nodeStack.peek().equals(rootNode) && !nodeStack.peek().getToken().equals("[") &&
              nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
            nodeStack.pop();
          }
        }
        nodeStack.peek().addChild(currentNode);
        nodeStack.push(currentNode);
      }
      myIndex++;
    }
    while (!nodeStack.isEmpty() && nodeStack.peek().getNumArgs() <= nodeStack.peek().getChildren()
        .size()) {
      nodeStack.pop();
    }
    System.out.println(nodeStack);
  //  if (!nodeStack.isEmpty()) {
   //   throw new IllegalArgumentException("Unmatched '['");
   // }
    double value = rootNode.getValue();
    myListener.onReturn(value);
  }


  //JUST FOR TESTING ==> WE USE A LISTENER
  public ModelState getModelstate() {
    return modelstate;
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

  private void createNode(List<String> tokens)
      throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String token = tokens.get(myIndex);
    if (token.matches("-?[0-9]+\\.?[0-9]*")) {
      currentNode = new ConstantNode(token, modelstate, myListener);
    } else if (token.matches(":[a-zA-Z]+")) {
      currentNode = new VariableNode(token, modelstate, myListener);
    } else if (token.matches("[a-zA-Z_]+(\\?)?")) {
      token = token.toLowerCase();
      System.out.println(modelstate.getUserDefinedCommands());
      if (commandMap.containsKey(token)) {
        /**
        if(commandMap.get(token).equals("control.To")) {
          int index = myIndex+2;
          while(index < tokens.size() && !tokens.get(index).equals("]")) {
            index++;
          }
          modelstate.getUserDefinedCommands().put(tokens.get(myIndex+1), index-myIndex+1);
        }
         */
        currentNode = new CommandNode(commandMap.get(token), modelstate, myListener);

      } else if (modelstate.getUserDefinedCommands().containsKey(token)) {
        currentNode = new UserCommandNode(token, modelstate, myListener);
        myIndex++;
      }
    } else {
      throw new IllegalArgumentException("Invalid token: " + token);
    }
  }

  private Map<String, String> loadCommandMap(String filePath) {
    Properties properties = new Properties();
    commandMap = new HashMap<>();
    try {
      File file = new File(filePath);
      properties.load(new FileInputStream(file));
      for (String commandName : properties.stringPropertyNames()) {
        String[] aliases = properties.getProperty(commandName).split("\\|");
        for (String alias : aliases) {
          commandMap.put(alias, commandName);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return commandMap;
  }
}






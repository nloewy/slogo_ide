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
import slogo.model.node.VariableNode;


public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private ModelState modelstate;

  private Map<String, String> commandMap;
  private Node currentNode;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    modelstate.getTurtles().add(new Turtle(1));
    myListener = listener;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");
  }

  public void parse(String input)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    Stack<Node> nodeStack = new Stack<>();
    Node rootNode = new ListNode("[", modelstate); // Create a root node
    nodeStack.push(rootNode);
    for (String token : tokens) {
      if (token.isEmpty() || token.startsWith("#")) {
        continue;
      }
      if (token.equals("[")) {
        nodeStack.push(new ListNode("", modelstate));
      } else if (token.equals("]")) {
        while(!nodeStack.isEmpty()) {
          nodeStack.peek().getToken();
          nodeStack.pop();
        }
      } else {
        createNode(token);
        if (nodeStack.peek().equals(rootNode)) {
          rootNode.addChild(currentNode);
          nodeStack.push(currentNode);
        } else if (nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
          while(!nodeStack.peek().equals(rootNode) && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
            nodeStack.pop();
          }
          nodeStack.peek().addChild(currentNode);
          nodeStack.push(currentNode);
        } else {
          nodeStack.peek().addChild(currentNode);
          nodeStack.push(currentNode);
        }
      }
    }
    while(!nodeStack.isEmpty()  && nodeStack.peek().getNumArgs() <= nodeStack.peek().getChildren().size()) {
      nodeStack.pop();
    }

    if (!nodeStack.isEmpty()) {
      throw new IllegalArgumentException("Unmatched '['");
    }

    rootNode.getValue();
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
    //modelstate.getTurtles().add(new Turtle(1));
  }

  private void createNode(String token)
        throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    if (token.matches("-?[0-9]+\\.?[0-9]*")) {
        currentNode = new ConstantNode(token, modelstate);
      } else if (token.matches(":[a-zA-Z]+")) {
        currentNode = new VariableNode(token.substring(1), modelstate); // Removing ":" from the variable name
      } else if (token.matches("[a-zA-Z_]+(\\?)?")) {
        token = token.toLowerCase();
        currentNode = new CommandNode(commandMap.get(token), modelstate);
      } else if (token.equals("[") || token.equals("]")) {
    }
    else {
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






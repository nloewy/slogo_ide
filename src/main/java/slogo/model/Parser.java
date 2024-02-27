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
import slogo.model.node.CommandCreatorNode;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;
import slogo.model.node.UserCommandNode;
import slogo.model.node.VariableNode;

public class Parser {
  private ModelState modelState;
  private SlogoListener myListener;
  private Map<String, String> commandMap;
  private static final String OPEN_BRACKET = "[";
  private static final String CLOSED_BRACKET = "]";
  private Node currentNode;
  private int myIndex;


  public Parser (ModelState modelState, SlogoListener listener) {
    this.modelState = modelState;
    this.myListener = listener;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");

  }


  public Node parse(String input)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    myIndex = 0;
    Stack<Node> nodeStack = new Stack<>();
    Node rootNode = new ListNode(OPEN_BRACKET, modelState, myListener); // Create a root node
    nodeStack.push(rootNode);
    while (myIndex < tokens.size()) {
      handleNextToken(tokens, nodeStack, rootNode);
    }
    while (!nodeStack.isEmpty() && nodeStack.peek().getNumArgs() <= nodeStack.peek().getChildren()
        .size()) {
      nodeStack.pop();
    }
    return rootNode;
  }

  private void handleNextToken(List<String> tokens, Stack<Node> nodeStack, Node rootNode)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    String token = retrieveNextToken(tokens);
    if (token.equals(OPEN_BRACKET)) {
      handleOpenBracket(nodeStack, rootNode);
    } else if (token.equals(CLOSED_BRACKET)) {
      handleClosedBracket(nodeStack);
    } else {
      createTokenAndUpdateStack(tokens, nodeStack);
    }
    myIndex++;
  }

  private void createTokenAndUpdateStack(List<String> tokens, Stack<Node> nodeStack)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    createNode(tokens);
    if (!nodeStack.peek().getToken().equals(OPEN_BRACKET)
        && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
      while (!nodeStack.peek().getToken().equals(OPEN_BRACKET) &&
          nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
        nodeStack.pop();
      }
    }
    nodeStack.peek().addChild(currentNode);
    nodeStack.push(currentNode);
  }

  private void handleClosedBracket(Stack<Node> nodeStack) {
    while (!nodeStack.peek().getToken().equals(OPEN_BRACKET)) {
      nodeStack.pop();
    }
    if (nodeStack.size() > 1) {
      nodeStack.pop();
    }
  }

  private void handleOpenBracket(Stack<Node> nodeStack, Node rootNode)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    while (!nodeStack.peek().equals(rootNode)
        && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
      nodeStack.pop();
    }
    Node newNode = new ListNode(OPEN_BRACKET, modelState, myListener);
    nodeStack.peek().addChild(newNode);
    nodeStack.push(newNode);
  }

  private String retrieveNextToken(List<String> tokens) {
    while (tokens.get(myIndex).isEmpty() || tokens.get(myIndex).startsWith("#")) {
      myIndex++;
    }
    return tokens.get(myIndex);
  }


  private void createNode(List<String> tokens)
      throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String token = tokens.get(myIndex);
    if (token.matches("-?[0-9]+\\.?[0-9]*")) {
      currentNode = new ConstantNode(token, modelState, myListener);
    } else if (token.matches(":[a-zA-Z]+")) {
      currentNode = new VariableNode(token, modelState, myListener);
    } else if (token.matches("[a-zA-Z_]+(\\?)?")) {
      token = token.toLowerCase();
      if (commandMap.containsKey(token)) {
        if(commandMap.get(token).equals("control.To")) {
          makeCommandCreatorNode(tokens);
        }
        else {
          currentNode = new CommandNode(commandMap.get(token), modelState, myListener);
        }

      } else if (modelState.getUserDefinedCommands().containsKey(token)) {
        currentNode = new UserCommandNode(token, modelState, myListener);
      }
    } else {
      throw new IllegalArgumentException("Invalid token: " + token);
    }
  }

  private void makeCommandCreatorNode(List<String> tokens) {
    int index = myIndex+2;
    while(index < tokens.size() && !tokens.get(index).equals(CLOSED_BRACKET)) {
      index++;
    }
    currentNode = new CommandCreatorNode(tokens.get(myIndex+1).toLowerCase(), modelState, myListener, index-myIndex-1);
    myIndex++;
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

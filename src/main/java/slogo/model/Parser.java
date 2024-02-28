package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.function.Consumer;
import slogo.model.node.CommandCreatorNode;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import java.util.function.Predicate;
import slogo.model.node.ListNode;
import slogo.model.node.Node;
import slogo.model.node.UserCommandNode;
import slogo.model.node.VariableNode;

public class Parser {

  private static final String OPEN_BRACKET = "[";
  private static final String CLOSED_BRACKET = "]";
  private final ModelState modelState;
  private final SlogoListener myListener;
  private Map<String, String> commandMap;
  private Node currentNode;
  private int myIndex;
  private final PatternLoader patternLoader;

  private List<Map.Entry<Predicate<String>, Consumer<List<String>>>> actionList;



  public Parser(ModelState modelState, SlogoListener listener) throws IOException {
    this.modelState = modelState;
    this.myListener = listener;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");
    patternLoader = new PatternLoader(
        "src/main/resources/slogo/example/languages/Syntax.properties");
    initializeActionList();
  }



  public Node parse(String input)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidCommandException, InvalidTokenException {
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    myIndex = 0;
    currentNode = null;
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
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidCommandException, InvalidTokenException {
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
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidTokenException {
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
      throws IllegalArgumentException, InvalidTokenException {
    String token = tokens.get(myIndex);
    boolean flag = false;
    for (Map.Entry<Predicate<String>, Consumer<List<String>>> entry : actionList) {
      if (entry.getKey().test(token)) {
        entry.getValue().accept(tokens);
        flag = true;
        break;
      }
    }
    if (!flag) {
      throwException(token);
    }
  }

  private void throwException(String token) {
    if (token.matches(patternLoader.getPattern("Command"))) {
      throw new InvalidCommandException("Command " + " '" + token + "' does not exist");
    }
    else {
      throw new InvalidTokenException("Invalid Token : " + "'" + token + "'");
    }
  }

  private boolean matchesPattern(String token, String pattern) {
    return token.matches(pattern);
  }

  private void makeCommandCreatorNode(List<String> tokens) {
    int index = myIndex + 2;
    while (index < tokens.size() && !tokens.get(index).equals(CLOSED_BRACKET)) {
      index++;
    }
    currentNode = new CommandCreatorNode(tokens.get(myIndex + 1).toLowerCase(), modelState,
        myListener, index - myIndex - 1);
    myIndex++;
  }

  private Map<String, String> loadCommandMap(String filePath) throws FileNotFoundException {
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
      throw new FileNotFoundException("Cannot find file " + filePath);
    }
    return commandMap;
  }

  private void initializeActionList() {
    actionList = new ArrayList<>();
    actionList.add(new SimpleEntry<>(
        token -> matchesPattern(token, patternLoader.getPattern("Constant")),
        tokens -> currentNode = new ConstantNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));

    actionList.add(new SimpleEntry<>(
        token -> matchesPattern(token, patternLoader.getPattern("Variable")),
        tokens -> currentNode = new VariableNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));

    actionList.add(new SimpleEntry<>(
        token -> matchesPattern(token, patternLoader.getPattern("Command")) && commandMap.containsKey(token.toLowerCase()) && commandMap.get(token.toLowerCase()).equals("control.To"),
        this::makeCommandCreatorNode));

    actionList.add(new SimpleEntry<>(
        token -> matchesPattern(token, patternLoader.getPattern("Command")) && commandMap.containsKey(token.toLowerCase()) && !commandMap.get(token.toLowerCase()).equals("control.To"),
        tokens -> {
          try {
            currentNode = new CommandNode(commandMap.get(tokens.get(myIndex).toLowerCase()), modelState,
                myListener);
          } catch (ClassNotFoundException e) {
            throw new InvalidCommandException("Command Not Found");
          }
        }));

    actionList.add(new SimpleEntry<>(
        token -> matchesPattern(token, patternLoader.getPattern("Command")) && modelState.getUserDefinedCommands().containsKey(token.toLowerCase()),
        tokens -> currentNode = new UserCommandNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));

  }
}

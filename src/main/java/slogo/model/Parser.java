package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;
import slogo.model.api.InsufficientArgumentsException;
import slogo.model.api.InvalidCommandException;
import slogo.model.api.InvalidTokenException;
import slogo.model.api.SlogoListener;
import slogo.model.node.CommandCreatorNode;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;
import slogo.model.node.UserCommandNode;
import slogo.model.node.VariableNode;

public class Parser {

  private static final String OPEN_BRACKET = "[";
  private static final String CLOSED_BRACKET = "]";
  private final ModelState modelState;
  private final SlogoListener myListener;
  private final PatternLoader patternLoader;
  private Node rootNode;
  private Map<String, String> commandMap;
  private Node currentNode;
  private int myIndex;
  private List<Map.Entry<Predicate<String>, Consumer<List<String>>>> nodeHandler;
  private Map<String, Consumer<Stack<Node>>> tokenHandlers;

  public Parser(ModelState modelState, SlogoListener listener) throws IOException {
    this.modelState = modelState;
    this.myListener = listener;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");
    patternLoader = new PatternLoader(
        "src/main/resources/slogo/example/languages/Syntax.properties");
    initializeNodeHandler();
    initializeTokenMap();
  }

  public Node parse(String input) {
    resetParsing();
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    Stack<Node> nodeStack = new Stack<>();
    rootNode = new ListNode(OPEN_BRACKET, modelState, myListener);
    nodeStack.push(rootNode);
    while (myIndex < tokens.size()) {
      parseNextToken(tokens, nodeStack);
      myIndex++;
    }
    checkForExtraneousArguments(nodeStack);
    return rootNode;
  }

  private void resetParsing() {
    myIndex = 0;
    rootNode = null;
    currentNode = null;
  }

  private void checkForExtraneousArguments(Stack<Node> nodeStack) {
    while (!nodeStack.isEmpty() && topNodeSatisfied(nodeStack)) {
      nodeStack.pop();
    }
    if (!nodeStack.isEmpty()) {
      throw new InsufficientArgumentsException("More Tokens Expected");
    }
  }

  private boolean topNodeSatisfied(Stack<Node> nodeStack) {
    return nodeStack.peek().getNumArgs() <= nodeStack.peek().getChildren().size();
  }

  private void parseNextToken(List<String> tokens, Stack<Node> nodeStack) {
    skipCommentsAndWhitespace(tokens);
    Consumer<Stack<Node>> handler = tokenHandlers.getOrDefault(
        tokens.get(myIndex), (stack) -> createTokenAndUpdateStack(tokens, stack));
    handler.accept(nodeStack);
  }

  private void skipCommentsAndWhitespace(List<String> tokens) {
    while (myIndex < tokens.size() && (tokens.get(myIndex).isEmpty() || tokens.get(myIndex)
        .startsWith("#"))) {
      myIndex++;
    }
  }

  private void createTokenAndUpdateStack(List<String> tokens, Stack<Node> nodeStack) {
    createNode(tokens);
    while (!nodeStack.peek().getToken().equals(OPEN_BRACKET) && topNodeSatisfied(nodeStack)) {
      nodeStack.pop();
    }
    if (!tokens.get(myIndex).matches(patternLoader.getPattern("Command")) && nodeStack.peek()
        .equals(rootNode)) {
      throw new InsufficientArgumentsException(
          "Command Expected. Cannot use " + tokens.get(myIndex) + " here");
    }
    nodeStack.peek().addChild(currentNode);
    nodeStack.push(currentNode);
  }

  private void handleClosedBracket(Stack<Node> nodeStack) {
    while (!nodeStack.peek().getToken().equals(OPEN_BRACKET)) {
      nodeStack.pop();
    }
    if (!nodeStack.peek().equals(rootNode)) {
      nodeStack.pop();
    }
  }

  private void handleOpenBracket(Stack<Node> nodeStack) {
    while (!nodeStack.peek().equals(rootNode) && topNodeSatisfied(nodeStack)) {
      nodeStack.pop();
    }
    Node newNode = new ListNode(OPEN_BRACKET, modelState, myListener);
    nodeStack.peek().addChild(newNode);
    nodeStack.push(newNode);
  }

  private void createNode(List<String> tokens) {
    String token = tokens.get(myIndex);
    boolean flag = false;
    for (Map.Entry<Predicate<String>, Consumer<List<String>>> entry : nodeHandler) {
      if (entry.getKey().test(token)) {
        entry.getValue().accept(tokens);
        flag = true;
        break;
      }
    }
    if (!flag) {
      if (token.matches(patternLoader.getPattern("Command"))) {
        throw new InvalidCommandException("Command " + " '" + token + "' does not exist");
      } else {
        throw new InvalidTokenException("Invalid Token : " + "'" + token + "'");
      }
    }
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

  private void initializeNodeHandler() {
    nodeHandler = new ArrayList<>();
    nodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Constant")),
        tokens -> currentNode = new ConstantNode(tokens.get(myIndex).toLowerCase(), modelState,
            myListener)));

    nodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Variable")),
        tokens -> currentNode = new VariableNode(tokens.get(myIndex).toLowerCase(), modelState,
            myListener)));

    nodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command")) && commandMap.containsKey(
            token.toLowerCase()) && commandMap.get(token.toLowerCase()).equals("control.To"),
        this::makeCommandCreatorNode));

    nodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command")) && commandMap.containsKey(
            token.toLowerCase()) && !commandMap.get(token.toLowerCase()).equals("control.To"),
        tokens -> {
          try {
            currentNode = new CommandNode(commandMap.get(tokens.get(myIndex).toLowerCase()),
                modelState,
                myListener);
          } catch (ClassNotFoundException e) {}
        }));

    nodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command"))
            && modelState.getUserDefinedCommands().containsKey(token.toLowerCase()),
        tokens -> currentNode = new UserCommandNode(tokens.get(myIndex).toLowerCase(), modelState,
            myListener)));
  }

  private void initializeTokenMap() {
    tokenHandlers = new HashMap<>();
    tokenHandlers.put(OPEN_BRACKET, this::handleOpenBracket);
    tokenHandlers.put(CLOSED_BRACKET, this::handleClosedBracket);
  }
}

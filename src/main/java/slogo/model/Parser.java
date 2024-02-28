package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.function.BiConsumer;
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
  private List<Map.Entry<Predicate<String>, Consumer<List<String>>>> createNodeHandler;
  private Map<String, BiConsumer<Stack<Node>, Node>> tokenHandlers;




  public Parser(ModelState modelState, SlogoListener listener) throws IOException {
    this.modelState = modelState;
    this.myListener = listener;
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");
    patternLoader = new PatternLoader(
        "src/main/resources/slogo/example/languages/Syntax.properties");
    initializeActionList();
    initializeTokenMap();
  }

  public Node parse(String input) {
    List<String> tokens = Arrays.asList(input.split("\\s+"));
    myIndex = 0;
    currentNode = null;
    Stack<Node> nodeStack = new Stack<>();
    Node rootNode = new ListNode(OPEN_BRACKET, modelState, myListener); // Create a root node
    nodeStack.push(rootNode);
    while (myIndex < tokens.size()) {
      handleNextToken(tokens, nodeStack, rootNode);
    }
    while (!nodeStack.isEmpty() && nodeStack.peek().getNumArgs() <= nodeStack.peek().getChildren().size()) {
      nodeStack.pop();
    }
    if(!nodeStack.isEmpty()) {
      throw new RuntimeException("Extra Commands");
    }
    return rootNode;
  }

    private void handleNextToken(List<String> tokens, Stack<Node> nodeStack, Node rootNode) {
    skipEmptyOrCommentTokens(tokens);
    String token = tokens.get(myIndex);
      BiConsumer<Stack<Node>, Node> handler = tokenHandlers.getOrDefault(token, (stack, node) -> createTokenAndUpdateStack(tokens, stack));
      handler.accept(nodeStack, rootNode);
    myIndex++;
  }

  private void skipEmptyOrCommentTokens(List<String> tokens) {
    while (myIndex < tokens.size() && (tokens.get(myIndex).isEmpty() || tokens.get(myIndex).startsWith("#"))) {
      myIndex++;
    }
  }

  private void createTokenAndUpdateStack(List<String> tokens, Stack<Node> nodeStack) {
    createNode(tokens);
    while (!nodeStack.peek().getToken().equals(OPEN_BRACKET) &&
          nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
        nodeStack.pop();
    }
    nodeStack.peek().addChild(currentNode);
    nodeStack.push(currentNode);
  }

  private void handleClosedBracket(Stack<Node> nodeStack, Node rootNode) {
    while (!nodeStack.peek().getToken().equals(OPEN_BRACKET)) {
      nodeStack.pop();
    }
    if (!nodeStack.peek().equals(rootNode)) {
      nodeStack.pop();
    }
  }

  private void handleOpenBracket(Stack<Node> nodeStack, Node rootNode) {
    while (!nodeStack.peek().equals(rootNode)
        && nodeStack.peek().getNumArgs() == nodeStack.peek().getChildren().size()) {
      nodeStack.pop();
    }
    Node newNode = new ListNode(OPEN_BRACKET, modelState, myListener);
    nodeStack.peek().addChild(newNode);
    nodeStack.push(newNode);
  }

  private void createNode(List<String> tokens) {
    String token = tokens.get(myIndex);
    boolean flag = false;
    for (Map.Entry<Predicate<String>, Consumer<List<String>>> entry : createNodeHandler) {
      if (entry.getKey().test(token)) {
        entry.getValue().accept(tokens);
        flag = true;
        break;
      }
    }
    if (!flag) {
      if (token.matches(patternLoader.getPattern("Command"))) {
        throw new InvalidCommandException("Command " + " '" + token + "' does not exist");
      }
      else {
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
        for(String alias : aliases) {
          commandMap.put(alias, commandName);
        }
      }
    } catch (IOException e) {
      throw new FileNotFoundException("Cannot find file " + filePath);
    }
    return commandMap;
  }

  private void initializeActionList() {
    createNodeHandler = new ArrayList<>();
    createNodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Constant")),
        tokens -> currentNode = new ConstantNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));

    createNodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Variable")),
        tokens -> currentNode = new VariableNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));

    createNodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command")) && commandMap.containsKey(token.toLowerCase()) && commandMap.get(token.toLowerCase()).equals("control.To"),
        this::makeCommandCreatorNode));

    createNodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command")) && commandMap.containsKey(token.toLowerCase()) && !commandMap.get(token.toLowerCase()).equals("control.To"),
        tokens -> {
          try {
            currentNode = new CommandNode(commandMap.get(tokens.get(myIndex).toLowerCase()), modelState,
                myListener);
          } catch (ClassNotFoundException e) {
            throw new InvalidCommandException("Command Not Found");
          }
        }));

    createNodeHandler.add(new SimpleEntry<>(
        token -> token.matches(patternLoader.getPattern("Command")) && modelState.getUserDefinedCommands().containsKey(token.toLowerCase()),
        tokens -> currentNode = new UserCommandNode(tokens.get(myIndex).toLowerCase(), modelState, myListener)));
  }
  private void initializeTokenMap() {
    tokenHandlers = new HashMap<>();
    tokenHandlers.put(OPEN_BRACKET, this::handleOpenBracket);
    tokenHandlers.put(CLOSED_BRACKET, this::handleClosedBracket);
  }
}

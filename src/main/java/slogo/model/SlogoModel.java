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
import slogo.model.exceptions.InvalidVariableException;
import slogo.model.node.ListNode;
import slogo.model.node.Node;


/**
 * The SlogoModel class represents the main part of the Slogo interpreter and model. This implements
 * the external API, and is one of the FEW parts of the model that the view/controller is able to
 * directly interact with. The SlogoModel class is for receiving commands from the view, sending it
 * to the parser, and executing it to update the program state, and using Listeners to provide
 * feedback to the view.
 *
 * @author Noah Loewy
 */

public class SlogoModel implements Model {

  public static final String RESOURCE_PATH = "src/main/resources/slogo/example/languages/";
  private final SlogoListener myListener;
  private final Parser parser;
  private final Stack<String> myCommands;
  private final Properties prop;
  private ModelState modelState;

  /**
   * Constructs and initializes SlogoModel object with the specified listener and current language.
   * For the purposes of this project, the listener will always be a MainScreen, which will update
   * based on the executed commands. Creates a parser object.
   *
   * @param listener        The SlogoListener object to receive event notifications.
   * @param currentLanguage The current language used in the interpreter.
   * @throws IOException If an I/O error occurs while loading language properties.
   */

  public SlogoModel(SlogoListener listener, String currentLanguage) throws IOException {
    modelState = new ModelState();
    modelState.getTurtles().put(1, new Turtle(1));
    myListener = listener;
    parser = new Parser(modelState, currentLanguage);
    myCommands = new Stack<>();
    prop = new Properties();
    File file = new File(RESOURCE_PATH + currentLanguage + ".properties");
    prop.load(new FileInputStream(file));
  }

  /**
   * Sends the input string obtained from the view to the parser for parsing. The parser updates the
   * rootNode by turning it into a tree. Then, calls handleParseResult to execute the command(s)
   * (until either all comamnds are exhausted or an error is thrown), and alerts listener of final
   * value returned
   *
   * @param input The Slogo command to be parsed and executed.
   * @throws InvocationTargetException If an error occurs during command execution.
   * @throws IllegalAccessException    If access is not allowed to the invoked method.
   * @throws InvalidCommandException   If a non-command token is placed where a command is expected,
   *                                   or if command token not recognized.
   * @throws InvalidTokenException     If a token in the input string does not match an expected
   *                                   pattern.
   */

  @Override
  public void parse(String input)
      throws InvocationTargetException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    if (input.isEmpty()) {
      return;
    }
    Node root = new ListNode("[", modelState);
    try {
      parser.parse(input, root);
    } catch (InvalidCommandException | InvalidTokenException | InsufficientArgumentsException |
             InvalidVariableException e) {
      handleParseResult(input, root);
      SlogoException e2 = e; //shouldnt be in model
      String template = (String) prop.getOrDefault(e.getClass().getSimpleName(), e.getMessage());
      String message = String.format(template, e2.getToken());
      throw new SlogoException(message, "");
    }
    handleParseResult(input, root);
  }

  /**
   * Handles the result of parsing by evaluating the command and notifying the listener.
   *
   * @param input The input command string.
   * @param root  The root node of the parsed command.
   * @throws InvocationTargetException If an error occurs during command execution.
   * @throws IllegalAccessException    If access is not allowed to the invoked method.
   */

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

  /**
   * Performs a depth-first traversal of the node tree and adds the listener to each node.
   *
   * @param node The root node of the tree to traverse.
   */

  private void dfsAddListener(Node node) {
    if (node != null) {
      node.addListener(myListener);
      for (Node child : node.getChildren()) {
        dfsAddListener(child);
      }
    }
  }

  @Override
  public void resetModel() {
    modelState = new ModelState();
  }

  @Deprecated
    //FOR TESTING PURPOSES ONLY
  ModelState getModelstate() {
    return modelState;
  }

}






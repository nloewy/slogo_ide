package slogo.model;

import java.io.IOException;
import slogo.model.api.Model;
import slogo.model.api.SlogoException;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.InvalidCommandException;
import slogo.model.exceptions.InvalidTokenException;
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

  private static final String OPEN_BRACKET = "[";
  private final SlogoListener myListener;
  private final Parser parser;
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
    myListener = listener;
    myListener.onUpdatePalette(modelState.getPalette());
    parser = new Parser(modelState, currentLanguage);
  }

  /**
   * Sends the input string obtained from the view to the parser for parsing. The parser updates the
   * rootNode by turning it into a tree. Then, calls handleParseResult to execute the command(s)
   * (until either all comamnds are exhausted or an error is thrown), and alerts listener of final
   * value returned
   *
   * @param input The Slogo command to be parsed and executed.
   * @throws InvalidCommandException If a non-command token is placed where a command is expected,
   *                                 or if command token not recognized.
   * @throws InvalidTokenException   If a token in the input string does not match an expected
   *                                 pattern.
   */

  @Override
  public void parse(String input) throws SlogoException {
    if (input == null || input.isEmpty()) {
      return;
    }
    Node root = new ListNode(OPEN_BRACKET, modelState);
    parseString(input, root); // root is modified, the parsing tree is populated
    executeCommand(input, root);
  }

  private void executeCommand(String input, Node root) {
    try {
      handleParseResult(input, root); //if no parsing error, just execution error
    } catch (SlogoException e) {
      throw new SlogoException(e.getMessage(), e, e.getToken());
    }
  }

  private void parseString(String input, Node root) {
    try {
      parser.parse(input, root);
    } catch (SlogoException e) {       //does partial execution

      handleParseResult(input, root);
      throw new SlogoException(e.getMessage(), e, e.getToken());
    }
  }

  /**
   * Handles the result of parsing by evaluating the command and notifying the listener.
   *
   * @param input The input command string.
   * @param root  The root node of the parsed command.
   */

  private void handleParseResult(String input, Node root) {
    dfsAddListener(root);
    double val = -1;
    for (Node node : root.getChildren()) {
      val = node.evaluate();
    }
    myListener.onReturn(val, input);
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






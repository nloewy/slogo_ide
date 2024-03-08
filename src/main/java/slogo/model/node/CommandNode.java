package slogo.model.node;

import slogo.model.ModelState;
import slogo.model.command.Command;
import slogo.model.command.CommandFactory;
import slogo.model.exceptions.InsufficientArgumentsException;

/**
 * The CommandNode class represents a node that executes a command in the Slogo language. The
 * command instance created by the factory executes the command operation, passing the list of
 * children nodes as arguments.
 *
 * @author Noah Loewy
 */

public class CommandNode extends Node {

  private final String myToken;
  private final ModelState myModelState;

  /**
   * Constructs a new CommandNode with the given token and model state.
   *
   * @param token      the token representing the command
   * @param modelState the model state of the application
   */

  public CommandNode(String token, ModelState modelState) {
    super();
    myToken = token;
    myModelState = modelState;
  }

  /**
   * Evaluates the CommandNode by dynamically invoking the execute method of the corresponding
   * Command class using reflection based on the token. So long as the number of arguments match,
   * the command is executed by calling the execute method on the proper Command object. This
   * applies to 'non-turtle' commands, that are only executed one time
   *
   * @return the result of executing the CommandNode
   * @throws InsufficientArgumentsException if unexpected number of arguments received
   */


  @Override
  public double evaluate()
      throws InsufficientArgumentsException {
    Command command = CommandFactory.createCommand(myToken, myModelState, getListener());
    if (getNumArgs() != getChildren().size()) {
      throw new InsufficientArgumentsException("", getToken());
    }
    return command.execute(getChildren(),
        myModelState.getTurtles().get(myModelState.getCurrTurtle()));
  }

  /**
   * Retrieves the number of arguments expected for the Command corresponding to this CommandNode.
   * This method dynamically loads the Command class using reflection based on the token, and then
   * attempts to retrieve the NUM_ARGS field.
   *
   * @return the number of arguments expected for the Command
   */

  @Override
  public int getNumArgs() {
    return CommandFactory.getNumArgs(myToken);
  }

  /**
   * Retrieves the token associated with this CommandNode.
   *
   * @return the token (name) of the command
   */

  @Override
  public String getToken() {
    return myToken;
  }
}

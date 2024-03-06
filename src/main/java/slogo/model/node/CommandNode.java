package slogo.model.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.command.Command;
import slogo.model.command.CommandFactory;
import slogo.model.exceptions.IncompleteClassException;
import slogo.model.exceptions.InsufficientArgumentsException;
import slogo.model.exceptions.InvalidCommandException;
import slogo.model.exceptions.InvalidOperandException;

/**
 * The CommandNode class represents a node that executes a command in the Slogo language.
 * <p>
 * This class adheres to the Factory Pattern, as it dynamically creates instances of the Command
 * class based on the token stored in the CommandNode. It dynamically loads the Command class using
 * reflection and then instantiates it using the constructor that taken the ModelState and
 * SlogoListener as arguments, effectively creating a new Command instance. The created Command
 * instance is then used to execute the command operation, passing the list of children nodes as
 * arguments. If any errors occur during the creation or execution of the Command instance,
 * appropriate exceptions are thrown.
 *
 * @author Noah Loewy
 */

public class CommandNode extends Node {

  private final String myToken;
  private final ModelState myModelState;
  private Command command;

  /**
   * Constructs a new CommandNode with the given token and model state.
   *
   * @param token      the token representing the command
   * @param modelState the model state of the application
   * @throws ClassNotFoundException if the command class cannot be found
   */

  public CommandNode(String token, ModelState modelState)
      throws ClassNotFoundException {
    super();
    myToken = token;
    myModelState = modelState;
  }

  /**
   * Evaluates the CommandNode by dynamically invoking the execute method of the corresponding
   * Command class using reflection based on the token. So long as the number of arguments match,
   * the command is executed by calling the execute method on the proper Command object
   *
   * @return the result of executing the CommandNode
   * @throws InvalidCommandException        if the Command class corresponding to token is not
   *                                        found
   * @throws InsufficientArgumentsException if unexpected number of arguments received
   * @throws InvalidOperandException        if an invalid operand is encountered during execution
   */

  @Override
  public double evaluate() throws InsufficientArgumentsException {
    Command command = CommandFactory.createCommand(myToken, myModelState, getListener());
    if (getNumArgs() != getChildren().size()) {
      throw new InsufficientArgumentsException("", getToken());
    }
    double val = 0;
    if (myToken.equals("control.Make") || myToken.startsWith("multiple.Ask") || !myModelState.getOuter()) {
      val = command.execute(getChildren(), myModelState.getCurrTurtle());
    } else {
      for (int index = 0; index < myModelState.getActiveTurtles().peek().size(); index++) {
        myModelState.setCurrTurtle(myModelState.getActiveTurtles().peek().get(index));
        val = command.execute(getChildren(), myModelState.getCurrTurtle());
      }
    }
    myModelState.setOuter(true) ;

    return val;
  }

  /**
   * Retrieves the number of arguments expected for the Command corresponding to this CommandNode.
   * This method dynamically loads the Command class using reflection based on the token, and then
   * attempts to retrieve the NUM_ARGS field.
   *
   * @return the number of arguments expected for the Command
   * @throws IncompleteClassException if the NUM_ARGS field is not found for the Command class
   */

  @Override
  public int getNumArgs() throws IncompleteClassException {
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

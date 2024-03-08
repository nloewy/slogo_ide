package slogo.model.node;

import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.model.command.CommandFactory;
import slogo.model.exceptions.InsufficientArgumentsException;

/**
 * The TurtleCommandNode class represents a node that executes a command in the Slogo language. The
 * command instance created by the factory executes the command operation, passing the list of
 * children nodes as arguments. This applies to commands that are executed on a per-turtle basis.
 *
 * @author Noah Loewy
 */


public class TurtleCommandNode extends CommandNode {

  private final ModelState myModelState;

  /**
   * Constructs a new TurtleCommandNode with the given token and model state.
   *
   * @param modelState the model state of the application
   */

  public TurtleCommandNode(String token, ModelState modelState) {
    super(token, modelState);
    myModelState = modelState;

  }

  /**
   * Evaluates the CommandNode by dynamically invoking the execute method of the corresponding
   * Command class using reflection based on the token. So long as the number of arguments match,
   * the command is executed by calling the execute method on the proper Command object. This
   * applies to 'turtle' commands, that are executed one time for each turtle
   *
   * @return the result of executing the TurtleCommandNode
   * @throws InsufficientArgumentsException if unexpected number of arguments received
   */

  @Override
  public double evaluate() throws InsufficientArgumentsException {
    Command command = CommandFactory.createCommand(getToken(), myModelState, getListener());
    if (getNumArgs() != getChildren().size()) {
      throw new InsufficientArgumentsException("", getToken());
    }
    return evaluateEachTurtle(command);
  }

  //with more time, i would use a combination of a stack and composition design
  private double evaluateEachTurtle(Command command) {
    int oldTurtleId = myModelState.getCurrTurtle();
    double val = 0;
    for (int currentTurtleId : myModelState.getActiveTurtles().peek()) {
      val = evaluateSingleTurtle(currentTurtleId, command);
      myModelState.setCurrTurtle(oldTurtleId);
    }
    return val;
  }

  private double evaluateSingleTurtle(int turtleId, Command command) {
    myModelState.setCurrTurtle(turtleId);
    Turtle currentTurtle = myModelState.getTurtles().get(turtleId);
    return command.execute(getChildren(), currentTurtle);
  }

}

package slogo.model.command;

import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.IncompleteClassException;
import slogo.model.exceptions.InvalidCommandException;

public class CommandFactory {

  private static final String BASE_PACKAGE = "slogo.model.command.";

  public static Command createCommand(String myToken, ModelState myModelState, SlogoListener listener) throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PACKAGE + myToken + "Command");
      return (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class)
          .newInstance(myModelState, listener);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("", myToken);
    }
  }

  public static int getNumArgs(String myToken) throws IncompleteClassException {
    try {
      return (int) Class.forName(BASE_PACKAGE + myToken + "Command").getField("NUM_ARGS").get(null);
    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      throw new IncompleteClassException(
          "Error getting number of arguments. Field NUM_ARGS not found for " + myToken);
    }
  }
}

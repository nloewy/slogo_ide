package slogo.model.command;

import com.sun.jdi.ClassNotPreparedException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.InvalidCommandException;

/**
 * The CommandFactory class is responsible for creating instances of Command objects based on input
 * tokens. It also provides methods to retrieve the number of arguments expected by a command. This
 * class adheres to the Factory Pattern, as it dynamically creates instances of the Command class
 * based on the token stored in the CommandNode. It dynamically loads the Command class using
 * reflection and then instantiates it using the constructor that taken the ModelState and
 * SlogoListener as arguments, effectively creating a new Command instance. The factory separates
 * the external code from the actual creation of Command objects. The client doesn't need to know
 * the details of how each Command object is instantiated; it only needs to provide the token and
 * required parameters to the factory.
 *
 * @author Noah Loewy
 */

public class CommandFactory {

  /**
   * Creates a Command object based on the given token, model state, and listener.
   *
   * @param myToken      the token representing the command
   * @param myModelState the model state for the command
   * @param listener     the listener for state change events
   * @return a Command object corresponding to the given token
   * @throws InvalidCommandException if the command represented by the token is invalid
   */

  public static Command createCommand(String myToken, ModelState myModelState,
      SlogoListener listener) throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(CommandFactory.getFullName(myToken));
      return (Command) clazz.getDeclaredConstructor(ModelState.class, SlogoListener.class)
          .newInstance(myModelState, listener);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("", myToken);
    }
  }

  /**
   * Retrieves the number of arguments expected by a command associated with the given token.
   *
   * @param myToken the token representing the command
   * @return the number of arguments expected by the command
   */

  public static int getNumArgs(String myToken) {
    try {
      return (int) Class.forName(getFullName(myToken)).getField("NUM_ARGS").get(null);
    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      throw new ClassNotPreparedException();
    }
  }

  private static String getFullPackagePath(String myToken, File[] directories,
      String currentPackage)
      throws ClassNotFoundException {
    if (directories != null) {
      for (File directory : directories) {
        String fullName = currentPackage + "." + directory.getName() + "." + myToken + "Command";
        try {
          Class.forName(fullName);
          return fullName;
        } catch (ClassNotFoundException ignored) {
          continue;
        }
      }
    }
    throw new ClassNotFoundException("Class Does not Exist");
  }

  private static String getFullName(String myToken) throws ClassNotFoundException {
    String currentPackage = CommandFactory.class.getPackage().getName();
    String packagePath = currentPackage.replace('.', '/');
    String basePath = Objects.requireNonNull(
        CommandFactory.class.getClassLoader().getResource(packagePath)).getFile();
    File[] directories = new File(basePath).listFiles(File::isDirectory);
    return getFullPackagePath(myToken, directories, currentPackage);
  }
}


package slogo.model.command;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import slogo.model.ModelState;
import slogo.model.api.SlogoListener;
import slogo.model.exceptions.IncompleteClassException;
import slogo.model.exceptions.InvalidCommandException;

public class CommandFactory {


  private static String getFullName(String myToken) {
    String currentPackage = CommandFactory.class.getPackage().getName();
    String packagePath = currentPackage.replace('.', File.separatorChar);
    String basePath = CommandFactory.class.getClassLoader().getResource(packagePath).getFile();
    File[] directories = new File(basePath).listFiles(File::isDirectory);

    if (directories != null) {
      for (File directory : directories) {
        String fullName = currentPackage + "." + directory.getName() + "." + myToken + "Command";
        try {
          Class.forName(fullName);
          return fullName;
        } catch (ClassNotFoundException e) {
        }
      }
    }
    return null;
  }


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

  public static int getNumArgs(String myToken) throws IncompleteClassException {
    try {
      return (int) Class.forName(getFullName(myToken)).getField("NUM_ARGS").get(null);
    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      throw new IncompleteClassException(
          "Error getting number of arguments. Field NUM_ARGS not found for " + myToken);
    }
  }

}


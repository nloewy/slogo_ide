package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import slogo.model.api.Model;
import slogo.model.node.CommandNode;
import slogo.model.node.Node;

public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private final List<Turtle> myTurtles;
  private final Map<String, Double> myVariables;
  private final ModelState modelstate;

  private final Map<String, String> syntaxMap;
  private Map<String, String> commandMap;
  private Node currentNode;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    myTurtles = new ArrayList<>();
    myVariables = new HashMap<>();

    myListener = listener;
    syntaxMap = loadRegexMap("src/main/resources/slogo/example/languages/Syntax.properties");
    commandMap = loadCommandMap("src/main/resources/slogo/example/languages/English.properties");

  }


  private Map<String, String> loadRegexMap(String filePath) {
    Properties properties = new Properties();
    Map<String, String> regexMap = new HashMap<>();
    try {
      File file = new File(filePath);
      properties.load(new FileInputStream(file));
      for (String key : properties.stringPropertyNames()) {
        regexMap.put(properties.getProperty(key), key);
      }
      System.out.println("Properties as Map:");
      for (Map.Entry<String, String> entry : regexMap.entrySet()) {
        System.out.println(entry.getKey() + " = " + entry.getValue());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return regexMap;
  }

  private Map<String, String> loadCommandMap(String filePath) {
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
      System.out.println("Commands as Map:");
      for (Map.Entry<String, String> entry : commandMap.entrySet()) {
        System.out.println(entry.getKey() + " = " + entry.getValue());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return commandMap;
  }

  /**
   * Parses the given command string.
   *
   * @param commandStr The command string to parse.
   * @throws IllegalArgumentException If the command is invalid.
   */
  /**
   * Parses the given command string.
   *
   * @param commandStr The command string to parse.
   * @throws IllegalArgumentException If the command is invalid.
   */
  public void parse(String commandStr) throws IllegalArgumentException {

    String[] tokens = commandStr.split("\\s+");
    for (String token : tokens) {
      boolean matched = false;
      for (String key : syntaxMap.keySet()) {
        if (token.matches(key)) {
          matched = true;
          if (currentNode == null) {
            if (!(syntaxMap.get(key).equals("Command"))) {
              throw new IllegalArgumentException("Command Required");
            } else {
              try {
                String[] typeToken = commandMap.get(token).split("\\.");

                Node node = new CommandNode(
                    "command." + typeToken[0] + "." + typeToken[1] + "Command", modelstate);
              } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                       InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
              }
            }
          }
        }
      }
      if (!matched) {
        throw new IllegalArgumentException("Invalid command token: " + token);
      }
    }
  }

  @Override
  public File loadXml(String path) {
    return null;
  }

  @Override
  public File saveXml(String path) {
    return null;
  }

  @Override
  public void resetModel() {
  }

}


package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import slogo.model.api.Node;

public class Parser {

  private final Map<String, String> syntaxMap;
  private Map<String, String> commandMap;


  public Parser() {
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


  public List<Node> parse(String commandStr) throws IllegalArgumentException {
    /**
     resetTrees();

     String[] tokens = commandStr.split("\\s+");
     for (String token : tokens) {
     for (String key : syntaxMap.keySet()) {
     if (token.matches(key)) {
     if (currentNode == null) {
     if (!(syntaxMap.get(key).equals("Command"))) {
     throw new IllegalArgumentException("Command Required");
     } else {
     try {
     String[] typeToken = commandMap.get(token).split("\\.");
     //create CommandNode or whatever node we should using reflection yay!!
     //then we execute the root command (its like a list of trees)
     //after each execution, tell listener, but dont give final value back to the view until we get the outermost command
     Node node = new CommandNode(
     "command." + typeToken[0] + "." + typeToken[1] + "Command", myTurtles.get(0));
     } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
     InstantiationException | IllegalAccessException e) {
     throw new RuntimeException(e);
     }
     }
     }
     }
     }
     }
     }

     */
    return null;
  }

  private void resetTrees() {
    /**
     myNodes = new ArrayList<>();
     currentNodeInd = 0;
     currentNode = null;
     */
  }

}

package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import slogo.model.api.Model;
import slogo.model.command.Command;

public class SlogoModel implements Model {

  private final List<Turtle> myTurtles;
  private final List<Variable> myVariables;
  private final SlogoListener myListener;
  private Map<String,String> syntaxMap;
  private Map<String,String> commandMap;
  private List<Node> myNodes;
  private Node currentNode;
  private int currentNodeInd;
  public SlogoModel(SlogoListener listener) {
    myTurtles = new ArrayList<>();
    myVariables = new ArrayList<>();
    myListener = listener;
    syntaxMap = getRegexMap();
    commandMap = getCommandMap();
    myNodes = new ArrayList<>();
    currentNode = null;
  }

  private void resetTrees() {
    myNodes = new ArrayList<>();
    currentNodeInd = 0;
    currentNode = null;
  }
  public void parse(String commandStr) throws IllegalArgumentException {
    resetTrees();

    String[] tokens = commandStr.split("\\s+");
    for(String token : tokens) {
      for(String key : syntaxMap.keySet()) {
        if(token.matches(key)) {
          if(currentNode == null) {
            if(!(syntaxMap.get(key).equals("Command"))) {
              throw new IllegalArgumentException("Command Required");
            }
            else{
              try {
                String[] typeToken = commandMap.get(token).split("\\.");
                Class<?> myInstance = Class.forName("command." + typeToken[0] +"." + typeToken[1] + "Command");
                Constructor<?> constructor = myInstance.getConstructor(new Class[] {Turtle.class});
                Command cmd = (Command) constructor.newInstance(myTurtles.get(0));
              }
              catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
              }
            }
          }
        }
      }
    }
  }

  private Map<String,String> getRegexMap() {
    String syntaxNames = "src/main/resources/slogo/example/languages/Syntax.properties";
    Properties properties = new Properties();
    syntaxMap = new HashMap<>();
    try {
      File filePath = new File(syntaxNames);
      properties.load(new FileInputStream(filePath));
      for (String key : properties.stringPropertyNames()) {
        syntaxMap.put(properties.getProperty(key), key);
      }
      System.out.println("Properties as Map:");
      for (Map.Entry<String, String> entry : syntaxMap.entrySet()) {
        System.out.println(entry.getKey() + " = " + entry.getValue());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return syntaxMap;
  }


  private Map<String,String> getCommandMap() {
    String syntaxNames = "src/main/resources/slogo/example/languages/English.properties";
    Properties properties = new Properties();
    commandMap = new HashMap<>();
    try {
      File filePath = new File(syntaxNames);
      properties.load(new FileInputStream(filePath));
      for (String commandName : properties.stringPropertyNames()) {
        String[] aliases = properties.getProperty(commandName).split("\\|");
        for(String alias: aliases) {
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

  //get List of Command objects using regex/inflection, lots of fun stuff

    //for each command object:
    // execute()
    // in execute:
    // if changes turtle state
    //1) update backend turtle
    //2) send listener.onUpdateTurtleState(immutableTurtle, valueReturned)
    //how to get the immutable turtle. Involves updating turtle that model, but not command can access
    //wouldnt this mean that turtle needs public getter/setter methods (would those be in api?)

    // if changes variable state
    //1) add variable to models list of variables
    //2) send listener.onUpdateTurtleState(immutableTurtle, valueReturned)
    //how to add to the variable list, as only model, and not command, can access
    //wouldnt this mean that variable needs public getter methods (would those be in api?)

    // if display something we determine what to display and inform listener
    // HOW TO DETERMINE WHAT TO DISPLAY?
    //Simple, just whatever the execute function returns, perhaps this should be told to listener


}


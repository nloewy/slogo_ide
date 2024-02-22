package slogo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import slogo.model.api.Model;

public class SlogoModel implements Model {

  private List<Turtle> myTurtles;
  private List<Variable> myVariables;
  private SlogoListener myListener;
  private Map<String,String> syntaxMap;

  public SlogoModel(SlogoListener listener) {
    myTurtles = new ArrayList<>();
    myVariables = new ArrayList<>();
    myListener = listener;
    syntaxMap = getRegexMap();
  }

  public void parse(String commandStr) {
    List<String> tokens = Arrays.asList(commandStr.split("\\s+"));
    for(String token : tokens) {
      for(String key : syntaxMap.keySet()) {
        if(token.matches(key)) {
          System.out.println(syntaxMap.get(key) + " " + token);
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


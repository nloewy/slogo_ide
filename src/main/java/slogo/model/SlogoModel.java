package slogo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import slogo.model.api.Model;

public class SlogoModel implements Model {

  private List<Turtle> myTurtles;
  private List<Variable> myVariables;
  private SlogoListener myListener;

  public SlogoModel(SlogoListener listener) {
    myTurtles = new ArrayList<>();
    myVariables = new ArrayList<>();
    myListener = listener;
  }
  public void parse(String commandStr){

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

  /**
   * Opens a file dialog to load a new XML file.
   * @param path: the path of the file to be loaded
   */
  public File loadXml(String path){ return null;}

  /**
   * Saves the current state of the model to an XML file.
   * Saves the commands, variables, and turtle state.
   * @param path
   */
  public File saveXml(String path) {return null;}

  /**
   * Resets the model to its initial state.
   */
  public void resetModel() {};
}


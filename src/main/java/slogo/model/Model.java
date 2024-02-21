package slogo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model {

  private List<Turtle> myTurtles;
  private SlogoListener myListener;
  public Model(SlogoListener listener) {
    myTurtles = new ArrayList<>();
    myTurtles.add(new Turtle(1));
    myListener = listener;
  }
//TODO COMMENTS

  public void parse(String commandStr){}

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

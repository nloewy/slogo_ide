package slogo.model;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import slogo.model.api.Model;
import slogo.model.node.Node;


public class SlogoModel implements Model {

  private final SlogoListener myListener;
  private ModelState modelstate;
  private final Parser parser;

  public SlogoModel(SlogoListener listener) {
    modelstate = new ModelState();
    modelstate.getTurtles().add(new Turtle(1));
    myListener = listener;
    parser = new Parser(modelstate, myListener);

  }

  @Override
  public void parse(String input)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    Node root = parser.parse(input);
    double val = root.getValue();
    myListener.onReturn(val);
  }

  //JUST FOR TESTING ==> WE USE A LISTENER
  public ModelState getModelstate() {
    return modelstate;
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
    modelstate = new ModelState();
  }

}






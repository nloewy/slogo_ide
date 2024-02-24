package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.command.Command;

public class MakeCommand extends Command {
private final Turtle myTurtle;
private final Map<String, Double> myVariables;

public MakeCommand(Turtle turtle, Map<String, Double> variables) {
  myTurtle = turtle;
  myVariables = variables;
}

public double execute(List<Node> arguments)
    throws InvocationTargetException, IllegalAccessException {

}

public void notifyListener(SlogoListener listener, double value) {
  super.notifyListener(listener, value);
}

}
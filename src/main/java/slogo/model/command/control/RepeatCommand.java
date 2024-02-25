package slogo.model.command.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.SlogoListener;
import slogo.model.Turtle;
import slogo.model.VariableNode;
import slogo.model.command.Command;

public class RepeatCommand extends Command {


  @Override
  public Function<ModelState, Double> execute(List<Node> arguments) throws InvocationTargetException, IllegalAccessException {
    return modelState -> {
      return 0.0;
      //modelState.getVariables().put
      //Turtle turtle = modelState.getTurtles().get(0);
      //double newX = turtle.getX() - pixels * Math.sin(Math.toRadians(turtle.getHeading()));
     // double newY = turtle.getY() - pixels * Math.cos(Math.toRadians(turtle.getHeading()));
      //turtle.setX(newX);
      //turtle.setY(newY);
      //return pixels;
    };
  }

  public void notifyListener(SlogoListener listener, double value) {
    //super.notifyListener(listener, value);
  }
}

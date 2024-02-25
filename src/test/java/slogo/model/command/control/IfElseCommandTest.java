package slogo.model.command.control;


import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class IfElseCommandTest {


  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;


  @Test
  public void testIfElseCommandTrue()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new CommandNode("slogo.model.command.control.IfElseCommand", model);
    Node nodeTwo = new CommandNode("slogo.model.command.math.SumCommand", model);
    Node nodeThree = new ConstantNode("-5", model);
    Node nodeFour = new ConstantNode("7", model);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Node nodeFive = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    nodeFive.addChild(new ConstantNode("60", model));
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("slogo.model.command.turtle.BackwardCommand", model);
    nodeFive.addChild(new ConstantNode("30", model));
    node.addChild(nodeFive);
    Assertions.assertEquals(60.0, node.getValue(), DELTA);
    Assertions.assertEquals(60.0, myTurtle.getY(), DELTA);
  }

  @Test
  public void testIfElseCommandFalse()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new CommandNode("slogo.model.command.control.IfElseCommand", model);
    Node nodeTwo = new CommandNode("slogo.model.command.math.SumCommand", model);
    Node nodeThree = new ConstantNode("-5", model);
    Node nodeFour = new ConstantNode("5", model);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Node nodeFive = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    nodeFive.addChild(new ConstantNode("60", model));
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("slogo.model.command.turtle.BackwardCommand", model);
    nodeSix.addChild(new ConstantNode("30", model));
    node.addChild(nodeSix);
    Assertions.assertEquals(30.0, node.getValue(), DELTA);
    Assertions.assertEquals(-30.0, myTurtle.getY(), DELTA);
  }

}

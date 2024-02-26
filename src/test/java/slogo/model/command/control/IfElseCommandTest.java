package slogo.model.command.control;


import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;

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
    node = new CommandNode("control.IfElse", model);
    Node nodeTwo = new CommandNode("math.Sum", model);
    Node nodeThree = new ConstantNode("-5", model);
    Node nodeFour = new ConstantNode("7", model);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);

    Node nodeFive = new ListNode("", model);
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("turtle.Forward", model);
    nodeSix.addChild(new ConstantNode("60", model));
    nodeFive.addChild(nodeSix);
    Node nodeSeven = new CommandNode("turtle.Forward", model);
    nodeSeven.addChild(new ConstantNode("30", model));
    nodeFive.addChild(nodeSeven);
    Node nodeEight = new ListNode("", model);
    Node nodeNine = new CommandNode("turtle.Forward", model);
    nodeNine.addChild(new ConstantNode("100", model));
    nodeEight.addChild(nodeNine);

    node.addChild(nodeEight);
    Assertions.assertEquals(30.0, node.getValue(), DELTA);
    Assertions.assertEquals(90.0, myTurtle.getY(), DELTA);
  }

  @Test
  public void testIfElseCommandFalse()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new CommandNode("control.IfElse", model);
    Node nodeTwo = new CommandNode("math.Sum", model);
    Node nodeThree = new ConstantNode("-5", model);
    Node nodeFour = new ConstantNode("5", model);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Node nodeFive = new ListNode("", model);
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("turtle.Forward", model);
    nodeSix.addChild(new ConstantNode("60", model));
    nodeFive.addChild(nodeSix);
    Node nodeSeven = new CommandNode("turtle.Forward", model);
    nodeSeven.addChild(new ConstantNode("30", model));
    nodeFive.addChild(nodeSeven);
    Node nodeEight = new ListNode("", model);
    Node nodeNine = new CommandNode("turtle.Forward", model);
    nodeEight.addChild(nodeNine);
    nodeNine.addChild(new ConstantNode("100", model));
    node.addChild(nodeEight);
    Assertions.assertEquals(100.0, node.getValue(), DELTA);
    Assertions.assertEquals(100.0, myTurtle.getY(), DELTA);

  }

}

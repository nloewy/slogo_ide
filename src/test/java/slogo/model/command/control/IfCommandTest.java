package slogo.model.command.control;


import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;

public class IfCommandTest extends CommandTest {


  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;


  @Test
  public void testIfCommandTrue()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new CommandNode("control.If", model, myListener);
    Node nodeTwo = new CommandNode("math.Sum", model, myListener);
    Node nodeThree = new ConstantNode("-5", model, myListener);
    Node nodeFour = new ConstantNode("7", model, myListener);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Node nodeFive = new ListNode("", model, myListener);
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("turtle.Forward", model, myListener);
    nodeSix.addChild(new ConstantNode("60", model, myListener));
    nodeFive.addChild(nodeSix);
    Node nodeSeven = new CommandNode("turtle.Forward", model, myListener);
    nodeSeven.addChild(new ConstantNode("30", model, myListener));
    nodeFive.addChild(nodeSeven);
    Assertions.assertEquals(30.0, node.getValue(), DELTA);
    Assertions.assertEquals(90.0, myTurtle.getY(), DELTA);
  }

  @Test
  public void testIfCommandFalse()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new CommandNode("control.If", model, myListener);
    Node nodeTwo = new CommandNode("math.Sum", model, myListener);
    Node nodeThree = new ConstantNode("-5", model, myListener);
    Node nodeFour = new ConstantNode("5", model, myListener);
    node.addChild(nodeTwo);
    nodeTwo.addChild(nodeThree);
    nodeTwo.addChild(nodeFour);
    Node nodeFive = new ListNode("", model, myListener);
    node.addChild(nodeFive);
    Node nodeSix = new CommandNode("turtle.Forward", model, myListener);
    nodeSix.addChild(new ConstantNode("60", model, myListener));
    nodeFive.addChild(nodeSix);
    Node nodeSeven = new CommandNode("turtle.Forward", model, myListener);
    nodeSeven.addChild(new ConstantNode("30", model, myListener));
    node.addChild(nodeFive);
    Assertions.assertEquals(0.0, node.getValue(), DELTA);
    Assertions.assertEquals(0.0, myTurtle.getY(), DELTA);
  }

}

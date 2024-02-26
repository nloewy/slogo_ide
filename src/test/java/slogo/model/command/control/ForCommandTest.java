package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ListNode;
import slogo.model.ModelState;
import slogo.model.api.Node;
import slogo.model.Turtle;
import slogo.model.VariableNode;

public class ForCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
  }

  @Test
  void testForForwardVariableNotUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ForCommand", model);
    Node fwdNode = new CommandNode("turtle.ForwardCommand", model);
    Node varNode = new VariableNode("i", model);
    Node listNode = new ListNode("", model);
    Node commandListNode = new ListNode("", model);
    commandListNode.addChild(fwdNode);
    fwdNode.addChild(new ConstantNode("2", model));
    listNode.addChild(varNode);
    listNode.addChild(new ConstantNode("1", model));
    listNode.addChild(new ConstantNode("5", model));
    listNode.addChild(new ConstantNode("1", model));
    node.addChild(listNode);
    node.addChild(commandListNode);
    assertEquals(node.getValue(), 2, DELTA);
    assertEquals(myTurtle.getY(), 10, DELTA);
  }

  @Test
  void testForForwardVariableUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ForCommand", model);
    Node listNode = new ListNode("", model);
    Node commandListNode = new ListNode("", model);
    Node cmdNode = new CommandNode("turtle.ForwardCommand", model);
    commandListNode.addChild(cmdNode);
    Node varNode = new VariableNode("i", model);
    cmdNode.addChild(new VariableNode("i", model));
    listNode.addChild(varNode);
    listNode.addChild(new ConstantNode("5", model));
    listNode.addChild(new ConstantNode("10", model));
    listNode.addChild(new ConstantNode("1", model));
    Node cmdNode2 = new CommandNode("turtle.ForwardCommand", model);
    cmdNode2.addChild(new VariableNode("i", model));
    commandListNode.addChild(cmdNode2);
    node.addChild(listNode);
    node.addChild(commandListNode);
    assertEquals(node.getValue(), 10, DELTA);
    assertEquals(myTurtle.getY(), 90, DELTA);
  }
}

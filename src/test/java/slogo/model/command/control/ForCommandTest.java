package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;
import slogo.model.node.VariableNode;

public class ForCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp() {
    model = new ModelState();
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);

    myTurtle = new Turtle(1);
    model.getTurtles().put(1, myTurtle);
  }

  @Test
  void testForForwardVariableNotUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("For", model);
    Node fwdNode = new CommandNode("Forward", model);
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
    dfsAddListener(node);
    assertEquals(node.evaluate(), 2, DELTA);
    assertEquals(myTurtle.getY(), -10, DELTA);
  }

  @Test
  void testForForwardVariableUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("For", model);
    Node listNode = new ListNode("", model);
    Node commandListNode = new ListNode("", model);
    Node cmdNode = new CommandNode("Forward", model);
    commandListNode.addChild(cmdNode);
    Node varNode = new VariableNode("i", model);
    cmdNode.addChild(new VariableNode("i", model));
    listNode.addChild(varNode);
    listNode.addChild(new ConstantNode("5", model));
    listNode.addChild(new ConstantNode("10", model));
    listNode.addChild(new ConstantNode("1", model));
    Node cmdNode2 = new CommandNode("Forward", model);
    cmdNode2.addChild(new VariableNode("i", model));
    commandListNode.addChild(cmdNode2);
    node.addChild(listNode);
    node.addChild(commandListNode);
    dfsAddListener(node);
    assertEquals(10, node.evaluate(), DELTA);
    assertEquals(-90, myTurtle.getY(), DELTA);
  }
}

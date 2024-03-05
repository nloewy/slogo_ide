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

public class RepeatCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().put(1, myTurtle);
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);

  }

  @Test
  void testRepeatNoVariable()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.Repeat", model);
    Node cmd = new CommandNode("math.SquareRoot", model);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model));
    Node commandListNode = new ListNode("", model);
    Node fwdNode = new CommandNode("turtle.Backward", model);
    fwdNode.addChild(new ConstantNode("2", model));
    commandListNode.addChild(fwdNode);
    Node fwdNode2 = new CommandNode("turtle.Backward", model);
    fwdNode2.addChild(new ConstantNode("5", model));
    commandListNode.addChild(fwdNode2);
    node.addChild(commandListNode);
    dfsAddListener(node);
    assertEquals(5, node.evaluate(), DELTA);
    assertEquals(21, myTurtle.getY(), DELTA);
  }

  @Test
  void testRepeatVariableUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.Repeat", model);
    Node cmd = new CommandNode("math.SquareRoot", model);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model));

    Node commandListNode = new ListNode("", model);
    Node cmdNode = new CommandNode("turtle.Backward", model);
    commandListNode.addChild(cmdNode);
    Node varNode = new VariableNode(":repcount", model);
    cmdNode.addChild(varNode);
    node.addChild(commandListNode);

    dfsAddListener(node);
    assertEquals(3, node.evaluate(), DELTA);
    assertEquals(6, myTurtle.getY(), DELTA);
  }
}

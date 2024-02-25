package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ListNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.VariableNode;

public class RepeatCommandTest {

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
  void testRepeatNoVariable()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.control.RepeatCommand", model);
    Node cmd = new CommandNode("slogo.model.command.math.SquareRootCommand", model);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model));
    Node commandListNode = new ListNode("", model);
    Node fwdNode = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    fwdNode.addChild(new ConstantNode("2", model));
    commandListNode.addChild(fwdNode);
    Node fwdNode2 = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    fwdNode2.addChild(new ConstantNode("5", model));
    commandListNode.addChild(fwdNode2);
    node.addChild(commandListNode);
    assertEquals(node.getValue(), 5, DELTA);
    assertEquals(myTurtle.getY(), 21, DELTA);
  }

  @Test
  void testRepeatVariableUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.control.RepeatCommand", model);
    Node cmd = new CommandNode("slogo.model.command.math.SquareRootCommand", model);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model));

    Node commandListNode = new ListNode("", model);
    Node cmdNode = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    commandListNode.addChild(cmdNode);
    Node varNode = new VariableNode("repcount", model);
    cmdNode.addChild(varNode);
    node.addChild(commandListNode);

    assertEquals(node.getValue(), 3, DELTA);
    assertEquals(myTurtle.getY(), 6, DELTA);
  }
}

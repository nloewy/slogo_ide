package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
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
    model.getTurtles().add(myTurtle);
  }

  @Test
  void testRepeatNoVariable()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.Repeat", model, myListener);
    Node cmd = new CommandNode("math.SquareRoot", model, myListener);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model, myListener));
    Node commandListNode = new ListNode("", model, myListener);
    Node fwdNode = new CommandNode("turtle.Forward", model, myListener);
    fwdNode.addChild(new ConstantNode("2", model, myListener));
    commandListNode.addChild(fwdNode);
    Node fwdNode2 = new CommandNode("turtle.Forward", model, myListener);
    fwdNode2.addChild(new ConstantNode("5", model, myListener));
    commandListNode.addChild(fwdNode2);
    node.addChild(commandListNode);
    assertEquals(node.getValue(), 5, DELTA);
    assertEquals(myTurtle.getY(), 21, DELTA);
  }

  @Test
  void testRepeatVariableUsed()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.Repeat", model, myListener);
    Node cmd = new CommandNode("math.SquareRoot", model, myListener);
    node.addChild(cmd);
    cmd.addChild(new ConstantNode("9", model, myListener));

    Node commandListNode = new ListNode("", model, myListener);
    Node cmdNode = new CommandNode("turtle.Forward", model, myListener);
    commandListNode.addChild(cmdNode);
    Node varNode = new VariableNode(":repcount", model, myListener);
    cmdNode.addChild(varNode);
    node.addChild(commandListNode);

    assertEquals(node.getValue(), 3, DELTA);
    assertEquals(myTurtle.getY(), 6, DELTA);
  }
}
package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.VariableNode;

public class ForCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    myTurtle = new Turtle(1);
    model.getTurtles().add(myTurtle);
    node = new VariableNode("Skeeyee", model);

  }

  @Test
  void testForForwardVariableNotUsed() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.control.ForCommand", model);
    Node fwdNode = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    Node varNode = new VariableNode("i", model);
    fwdNode.addChild(new ConstantNode("2", model));
    node.addChild(varNode);
    node.addChild(new ConstantNode("1",model));
    node.addChild(new ConstantNode("5",model));
    node.addChild(new ConstantNode("1",model));
    node.addChild(fwdNode);
    assertEquals(node.getValue(), 2, DELTA);
    assertEquals(myTurtle.getY(), 10, DELTA);
  }

  @Test
  void testForForwardVariableUsed() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.control.ForCommand", model);
    Node fwdNode = new CommandNode("slogo.model.command.turtle.ForwardCommand", model);
    Node varNode = new VariableNode("i", model);
    fwdNode.addChild(varNode);
    node.addChild(varNode);
    node.addChild(new ConstantNode("1",model));
    node.addChild(new ConstantNode("5",model));
    node.addChild(new ConstantNode("1",model));
    node.addChild(fwdNode);
    assertEquals(node.getValue(), 5, DELTA);
    assertEquals(myTurtle.getY(), 15, DELTA);
  }
}

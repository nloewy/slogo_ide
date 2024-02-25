package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ListNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;
import slogo.model.UserCommandNode;
import slogo.model.VariableNode;
import slogo.model.command.Command;

public class ToCommandTest {


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
  void testToCommandNoVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ToCommand", model);
    Node nodeTwo = new UserCommandNode("HalfSquare", model);
    node.addChild(nodeTwo);

    nodeTwo.addChild(new ListNode("", model));

    Node fwdNode = new CommandNode("turtle.ForwardCommand", model);
    fwdNode.addChild(new ConstantNode("2", model));

    Node rightNode = new CommandNode("turtle.RightCommand", model);
    rightNode.addChild(new ConstantNode("90", model));


    Node commandList = new ListNode("", model);
    commandList.addChild(fwdNode);
    commandList.addChild(rightNode);
    commandList.addChild(fwdNode);
    node.addChild(commandList);


    assertEquals(1.0, node.getValue(), DELTA);


    Command c = new UserCommand();

    assertEquals(2,  model.applyCommandToModelState(c.execute(model.getUserDefinedCommands().get("HalfSquare"))));
    assertEquals(2.0, myTurtle.getY(), DELTA);
    assertEquals(2.0, myTurtle.getX(), DELTA);
  }

  @Test
  void testToCommandWithVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ToCommand", model);
    Node nodeTwo = new UserCommandNode("HalfSquare", model);
    node.addChild(nodeTwo);

    nodeTwo.addChild(new ListNode("", model));

    Node fwdNode = new CommandNode("turtle.ForwardCommand", model);
    fwdNode.addChild(new ConstantNode("2", model));

    Node rightNode = new CommandNode("turtle.RightCommand", model);
    rightNode.addChild(new VariableNode("i", model));


    Node commandList = new ListNode("", model);
    commandList.addChild(fwdNode);
    commandList.addChild(rightNode);
    commandList.addChild(fwdNode);
    node.addChild(commandList);


    Node variableMaker = new CommandNode("control.MakeCommand", model);
    variableMaker.addChild(new VariableNode("i", model));
    variableMaker.addChild(new ConstantNode("90", model));
    assertEquals(90.0, variableMaker.getValue(), DELTA);


    assertEquals(1.0, node.getValue(), DELTA);


    Command c = new UserCommand();

    assertEquals(2,  model.applyCommandToModelState(c.execute(model.getUserDefinedCommands().get("HalfSquare"))));
    assertEquals(2.0, myTurtle.getY(), DELTA);
    assertEquals(2.0, myTurtle.getX(), DELTA);
  }
}
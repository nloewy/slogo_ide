package slogo.model.command.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.ModelState;
import slogo.model.node.Node;
import slogo.model.Turtle;
import slogo.model.node.UserCommandNode;
import slogo.model.node.VariableNode;

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
  void testToCommandYesVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ToCommand", model);
    Node nodeTwo = new UserCommandNode("HalfSquare",  model);
    nodeTwo.addChild(new VariableNode("var1", model));
    nodeTwo.addChild(new VariableNode("var2", model));
    nodeTwo.addChild(new VariableNode("var3", model));
    node.addChild(nodeTwo);

    Node fwdNode = new CommandNode("turtle.ForwardCommand", model);
    fwdNode.addChild(new VariableNode("var1", model));

    Node rightNode = new CommandNode("turtle.RightCommand", model);
    rightNode.addChild(new VariableNode("var2", model));

    Node fwdNodeSecond = new CommandNode("turtle.ForwardCommand", model);
    fwdNodeSecond.addChild(new VariableNode("var3", model));

    Node commandList = new ListNode("", model);
    commandList.addChild(fwdNode);
    commandList.addChild(rightNode);
    commandList.addChild(fwdNodeSecond);
    node.addChild(commandList);

    assertEquals(1.0, node.getValue(), DELTA);

    List<Node> newNodes = new ArrayList<>(); //nodeTwo.getChildren.size() nodes
    newNodes.add(new ConstantNode("2", model));
    newNodes.add(new ConstantNode("90", model));
    newNodes.add(new ConstantNode("3", model));

    for(int i = 0; i < model.getUserDefinedCommands().get("HalfSquare").get(0).getChildren().size() ; i++) {
      //in reality wld parse
      String token = model.getUserDefinedCommands().get("HalfSquare").get(0).getChildren().get(i).getToken();
      model.getVariables().put(token, newNodes.get(i).getValue());

    }
    assertEquals(3.0, model.getUserDefinedCommands().get("HalfSquare").get(1).getValue(), DELTA);
    assertEquals(2.0, myTurtle.getY(), DELTA);
    assertEquals(3.0, myTurtle.getX(), DELTA);
  }

  @Test
  void testToCommandNoVariables()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("control.ToCommand", model);
    Node nodeTwo = new UserCommandNode("HalfSquare",  model);
    node.addChild(nodeTwo);

    Node fwdNode = new CommandNode("turtle.ForwardCommand", model);
    fwdNode.addChild(new ConstantNode("2", model));

    Node rightNode = new CommandNode("turtle.RightCommand", model);
    rightNode.addChild(new ConstantNode("90", model));

    Node fwdNodeSecond = new CommandNode("turtle.ForwardCommand", model);
    fwdNodeSecond.addChild(new ConstantNode("3", model));

    Node commandList = new ListNode("", model);
    commandList.addChild(fwdNode);
    commandList.addChild(rightNode);
    commandList.addChild(fwdNodeSecond);
    node.addChild(commandList);

    assertEquals(1.0, node.getValue(), DELTA);

    List<Node> newNodes = new ArrayList<>(); //nodeTwo.getChildren.size() nodes

    for(int i = 0; i < model.getUserDefinedCommands().get("HalfSquare").get(0).getChildren().size() ; i++) {
      //in reality wld parse
      String token = model.getUserDefinedCommands().get("HalfSquare").get(0).getChildren().get(i).getToken();
      model.getVariables().put(token, newNodes.get(i).getValue());

    }
    assertEquals(3.0, model.getUserDefinedCommands().get("HalfSquare").get(1).getValue(), DELTA);
    assertEquals(2.0, myTurtle.getY(), DELTA);
    assertEquals(3.0, myTurtle.getX(), DELTA);
  }
}
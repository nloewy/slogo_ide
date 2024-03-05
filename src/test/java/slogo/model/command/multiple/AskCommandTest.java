package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;

public class AskCommandTest extends CommandTest {
  public static final double DELTA = 0.001;
  private ModelState model;
  private Node node;

@Test
  void testTurtlesNoExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model = new ModelState();
    model.getTurtles().clear();
    model.getTurtles().put(5, new Turtle(5));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(5);
    model.getTurtles().put(4, new Turtle(4));
    model.getTurtles().put(10, new Turtle(10));
    model.getActiveTurtles().get(0).add(10);
    model.getTurtles().put(9, new Turtle(9));
    model.getActiveTurtles().get(0).add(9);
    Node askNode = new CommandNode("multiple.Ask", model);
    Node list1 = new ListNode("[", model) ;
    Node list2 = new ListNode("[", model) ;
    askNode.addListener(myListener);
    list1.addListener(myListener);
    list2.addListener(myListener);
    askNode.getChildren().add(list1);
    Node nodeNine = new ConstantNode("20", model) ;
    list1.getChildren().add(nodeNine);
    list1.getChildren().add(new ConstantNode("9", model));
    askNode.getChildren().add(list2);
    Node rightNode = new CommandNode("turtle.Right", model);
    rightNode.addListener(myListener);
    Node nodeNinty = new ConstantNode("90", model);
    list2.getChildren().add(rightNode);
    rightNode.getChildren().add(nodeNinty);
    assertEquals(90.0, askNode.evaluate(), DELTA);
    assertEquals(90, model.getTurtles().get(20).getHeading(), DELTA);
    assertEquals(90, model.getTurtles().get(9).getHeading(), DELTA);
    Node rightNode2 = new CommandNode("turtle.Right", model);
    Node nodeEighty = new ConstantNode("80", model);
    nodeNine.addListener(myListener);
    nodeNinty.addListener(myListener);
    rightNode2.addListener(myListener);
    nodeEighty.addListener(myListener);
    rightNode2.addChild(nodeEighty);
    rightNode2.evaluate();
    assertEquals(3.0, model.getActiveTurtles().peek().size(), DELTA);
    assertEquals(5.0, model.getTurtles().size(), DELTA);

    assertTrue(model.getActiveTurtles().peek().contains(10));
    assertTrue(model.getActiveTurtles().peek().contains(9));
    assertTrue(model.getActiveTurtles().peek().contains(5));
    assertEquals(80, model.getTurtles().get(5).getHeading(), DELTA);
    assertEquals(0, model.getTurtles().get(4).getHeading(), DELTA);
    assertEquals(80, model.getTurtles().get(10).getHeading(), DELTA);
    assertEquals(90, model.getTurtles().get(20).getHeading(), DELTA);
    assertEquals(170, model.getTurtles().get(9).getHeading(), DELTA);
  }


  @Test
  void testTurtlesWithExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model = new ModelState();
    model.getTurtles().clear();
    model.getTurtles().put(5, new Turtle(5));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().get(0).add(5);
    model.getTurtles().put(4, new Turtle(4));
    model.getTurtles().put(10, new Turtle(10));
    model.getActiveTurtles().get(0).add(10);
    model.getTurtles().put(9, new Turtle(9));
    model.getActiveTurtles().get(0).add(9);
    model.getTurtles().put(2, new Turtle(2));
    Node askNode = new CommandNode("multiple.Ask", model);
    Node list1 = new ListNode("[", model) ;
    Node list2 = new ListNode("[", model) ;
    askNode.addListener(myListener);
    list1.addListener(myListener);
    list2.addListener(myListener);
    askNode.getChildren().add(list1);
    Node nodeSeven = new CommandNode("math.Sum", model) ;
    Node nodeEight = new ConstantNode("4", model) ;
    Node nodeNine = new ConstantNode("16.2", model) ;
    list1.getChildren().add(nodeSeven);
    nodeSeven.getChildren().add(nodeEight);
    nodeSeven.getChildren().add(nodeNine);
    list1.getChildren().add(new ConstantNode("9", model));
    askNode.getChildren().add(list2);
    Node rightNode = new CommandNode("turtle.Right", model);
    rightNode.addListener(myListener);
    Node nodeNinty = new ConstantNode("90", model);
    list2.getChildren().add(rightNode);
    rightNode.getChildren().add(nodeNinty);
    assertEquals(90.0, askNode.evaluate(), DELTA);
    assertEquals(90, model.getTurtles().get(20).getHeading(), DELTA);
    assertEquals(90, model.getTurtles().get(9).getHeading(), DELTA);
    Node rightNode2 = new CommandNode("turtle.Right", model);
    Node nodeEighty = new ConstantNode("80", model);
    nodeSeven.addListener(myListener);
    nodeEight.addListener(myListener);
    nodeNine.addListener(myListener);
    nodeNinty.addListener(myListener);
    rightNode2.addListener(myListener);
    nodeEighty.addListener(myListener);
    rightNode2.addChild(nodeEighty);
    rightNode2.evaluate();
    assertEquals(3.0, model.getActiveTurtles().peek().size(), DELTA);
    assertEquals(6.0, model.getTurtles().size(), DELTA);
    assertTrue(model.getActiveTurtles().peek().contains(10));
    assertTrue(model.getActiveTurtles().peek().contains(9));
    assertTrue(model.getActiveTurtles().peek().contains(5));
    assertEquals(80, model.getTurtles().get(5).getHeading(), DELTA);
    assertEquals(0, model.getTurtles().get(4).getHeading(), DELTA);
    assertEquals(80, model.getTurtles().get(10).getHeading(), DELTA);
    assertEquals(0, model.getTurtles().get(2).getHeading(), DELTA);
    assertEquals(90, model.getTurtles().get(20).getHeading(), DELTA);
    assertEquals(170, model.getTurtles().get(9).getHeading(), DELTA);
  }


}

package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class RightCommandTest {


  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.turtle.RightCommand", myTurtle);

  }

  @Test
  void testBasicRight()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "75";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 75);
    assertEquals(myTurtle.getHeading(), 75);

  }

  @Test
  void testRightWithNonZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String rot = "20";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 20);
    assertEquals(myTurtle.getHeading(), 80);
  }

  @Test
  void testRightWithZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "0";
    myTurtle.setHeading(500);
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 0);
    assertEquals(myTurtle.getHeading(), 140);

  }

  @Test
  void testRightNegativeHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "-75";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), -75);
    assertEquals(myTurtle.getHeading(), 285);
  }

  @Test
  void testRightHeadingOver360()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "900";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 900);
    assertEquals(myTurtle.getHeading(), 180);
  }
}
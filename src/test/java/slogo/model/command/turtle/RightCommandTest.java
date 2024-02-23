package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class RightCommandTest {

  public static final double DELTA = 0.001;

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
    assertEquals(node.getValue(), 75, DELTA);
    assertEquals(myTurtle.getHeading(), 75, DELTA);

  }

  @Test
  void testRightWithNonZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String rot = "20";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 20, DELTA);
    assertEquals(myTurtle.getHeading(), 80, DELTA);
  }

  @Test
  void testRightWithZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "0";
    myTurtle.setHeading(500);
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 0, DELTA);
    assertEquals(myTurtle.getHeading(), 140, DELTA);

  }

  @Test
  void testRightNegativeHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "-75";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), -75, DELTA);
    assertEquals(myTurtle.getHeading(), 285, DELTA);
  }

  @Test
  void testRightHeadingOver360()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "900";
    node.addChildren(new ConstantNode(rot, myTurtle));
    assertEquals(node.getValue(), 900, DELTA);
    assertEquals(myTurtle.getHeading(), 180, DELTA);
  }
}
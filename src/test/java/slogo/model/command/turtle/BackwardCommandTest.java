package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class BackwardCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.turtle.BackwardCommand", myTurtle);

  }

  @Test
  void testBasicBackward()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "75";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 75, DELTA);
    assertEquals(myTurtle.getY(), -75, DELTA);

  }

  @Test
  void testBackwardWithHeading()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String dist = "20";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 20, DELTA);
    assertEquals(myTurtle.getX(), -10 * Math.sqrt(3), DELTA);
    assertEquals(myTurtle.getY(), -10, DELTA);
  }

  @Test
  void testBackwardWithNonZeroOrigin()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    myTurtle.setX(50);
    myTurtle.setY(70);
    String dist = "20";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 20, DELTA);
    assertEquals(myTurtle.getX(), 50 - 10 * Math.sqrt(3), DELTA);
    assertEquals(myTurtle.getY(), 60, DELTA);
  }

  @Test
  void testBackwardWithZeroDistance()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "0";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 0, DELTA);
    assertEquals(myTurtle.getX(), 0, DELTA);
    assertEquals(myTurtle.getY(), 0, DELTA);
  }

  @Test
  void testBackwardNegativeDistance()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "-75";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), -75, DELTA);
    assertEquals(myTurtle.getY(), 75, DELTA);
  }
}
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

public class ForwardCommandTest {


  private Turtle myTurtle;

  @BeforeEach
  void setUp() {
    myTurtle = new Turtle(1);
  }

  @Test
  void testBasicForward()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    String dist = "75";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 75);
    assertEquals(myTurtle.getY(), 75);

  }

  @Test
  void testForwardWithHeading()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    myTurtle.setHeading(60);
    String dist = "20";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 20);
    assertEquals(myTurtle.getX(), 10*Math.sqrt(3), .00001);
    assertEquals(myTurtle.getY(), 10, .00001);
  }

  @Test
  void testForwardWithNonZeroOrigin()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    myTurtle.setHeading(60);
    myTurtle.setX(50);
    myTurtle.setY(70);
    String dist = "20";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 20);
    assertEquals(myTurtle.getX(), 50 + 10 * Math.sqrt(3), .00001);
    assertEquals(myTurtle.getY(), 80, .00001);
  }

  @Test
  void testForwardWithZeroDistance()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    String dist = "0";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 0);
    assertEquals(myTurtle.getX(), 0);
    assertEquals(myTurtle.getY(), 0);
  }

  @Test
  void testForwardNegativeDistance()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    String dist = "-75";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), -75);
    assertEquals(myTurtle.getY(), -75);

  }
  }
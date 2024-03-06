package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class ForwardCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model = new ModelState();
    model.getTurtles().put(1, new Turtle(1));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);
    myTurtle = model.getTurtles().get(1);
    node = new CommandNode("Forward", model);
    dfsAddListener(node);
  }

  @Test
  void testBasicForward()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "75";
    node.addChild(new ConstantNode(dist, model));
    node.addListener(myListener);
    assertEquals(75, node.evaluate(), DELTA);
    assertEquals(-75, myTurtle.getY(), DELTA);

  }

  @Test
  void testForwardWithHeading()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String dist = "20";
    node.addChild(new ConstantNode(dist, model));
    node.addListener(myListener);
    assertEquals(20, node.evaluate(), DELTA);
    assertEquals(10 * Math.sqrt(3), myTurtle.getX(), DELTA);
    assertEquals(-10, myTurtle.getY(), DELTA);
  }

  @Test
  void testForwardWithNonZeroOrigin()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    myTurtle.setX(50);
    myTurtle.setY(70);
    String dist = "20";
    node.addChild(new ConstantNode(dist, model));
    assertEquals(20, node.evaluate(), DELTA);
    assertEquals(50 + 10 * Math.sqrt(3), myTurtle.getX(), DELTA);
    assertEquals(60, myTurtle.getY(), DELTA);
  }

  @Test
  void testForwardWithZeroDistance()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "0";
    node.addChild(new ConstantNode(dist, model));
    assertEquals(0, node.evaluate(), DELTA);
    assertEquals(0, myTurtle.getX(), DELTA);
    assertEquals(0, myTurtle.getY(), DELTA);
  }

  @Test
  void testForwardNegativeDistance()
      throws InvocationTargetException, IllegalAccessException {
    String dist = "-75";
    node.addChild(new ConstantNode(dist, model));
    assertEquals(-75, node.evaluate(), DELTA);
    assertEquals(75, myTurtle.getY(), DELTA);
  }
}

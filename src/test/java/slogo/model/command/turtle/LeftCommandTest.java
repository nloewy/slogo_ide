package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class LeftCommandTest {


  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("turtle.LeftCommand", model);

  }

  @Test
  void testBasicLeft()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "75";
    node.addChild(new ConstantNode(rot, model));
    assertEquals(75, node.getValue(), DELTA);
    assertEquals(285, myTurtle.getHeading(), DELTA);

  }

  @Test
  void testLeftWithNonZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String rot = "20";
    node.addChild(new ConstantNode(rot, model));
    assertEquals(20, node.getValue(), DELTA);
    assertEquals(40, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testLeftWithZeroHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "0";
    myTurtle.setHeading(500);
    node.addChild(new ConstantNode(rot, model));
    assertEquals(0, node.getValue(), DELTA);
    assertEquals(140, myTurtle.getHeading(), DELTA);

  }

  @Test
  void testLeftNegativeHeading()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "-75";
    node.addChild(new ConstantNode(rot, model));
    assertEquals(-75, node.getValue(), DELTA);
    assertEquals(75, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testLeftHeadingOver360()
      throws InvocationTargetException, IllegalAccessException {
    String rot = "900";
    node.addChild(new ConstantNode(rot, model));
    assertEquals(900, node.getValue(), DELTA);
    assertEquals(180, myTurtle.getHeading(), DELTA);
  }
}

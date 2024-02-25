package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class SetHeadingCommandTest {

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
    node = new CommandNode("slogo.model.command.turtle.SetHeadingCommand", model);

  }

  @ParameterizedTest
  @CsvSource({
      "45, 45, 45",
      "90, 90, 90",
      "180, 180, 180",
      "181, 179, 181",
      "0, 0, 0"
  })
  void testBasicHeading(String newHeading, String expectedValue, String expectedHeading)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(newHeading, model));
    assertEquals(Double.parseDouble(expectedValue), node.getValue(), DELTA);
    assertEquals(Double.parseDouble(expectedHeading), myTurtle.getHeading(), DELTA);

  }

  @Test
  void testHeadingWithNonZeroInitial()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setHeading(60);
    String newHeading = "20";
    node.addChild(new ConstantNode(newHeading, model));
    assertEquals(40, node.getValue(), DELTA);
    assertEquals(20, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testHeadingOver360()
      throws InvocationTargetException, IllegalAccessException {
    String newHeading = "940";
    myTurtle.setHeading(500);
    node.addChild(new ConstantNode(newHeading, model));
    assertEquals(80, node.getValue(), DELTA);
    assertEquals(220, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testHeadingNoChange()
      throws InvocationTargetException, IllegalAccessException {
    String newHeading = "1081";
    myTurtle.setHeading(361);
    node.addChild(new ConstantNode(newHeading, model));
    assertEquals(0, node.getValue(), DELTA);
    assertEquals(1, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testHeadingNegativeValue()
      throws InvocationTargetException, IllegalAccessException {
    String newHeading = "-270";
    myTurtle.setHeading(95);
    node.addChild(new ConstantNode(newHeading, model));
    assertEquals(5, node.getValue(), DELTA);
    assertEquals(90, myTurtle.getHeading(), DELTA);
  }
}
package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class SetTowardsCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("slogo.model.command.turtle.SetTowardsCommand", model);
  }

  @ParameterizedTest
  @CsvSource({
      "100, 100, 45, 45",
      "100, -100, 135, 135",
      "-100, 100, 45, 315",
      "-100, -100, 135, 225",
      "100, 0, 90, 90",
      "0, 100, 0, 0",
      "-100, 0, 90, 270",
      "0, -100, 180, 180",
      "0, 0, 0, 0,",
      "100000000, 100000000, 45, 45",
      "100000000, -100000000, 135, 135",
      "-100000000, 100000000, 45, 315",
      "-100000000, -100000000, 135, 225",
      "100000000, 0, 90, 90",
      "0, 100000000, 0, 0",
      "-100000000, 0, 90, 270",
      "0, -100000000, 180, 180",
      ".000000001, .000000001, 45, 45",
      ".000000001, -.000000001, 135, 135",
      "-.000000001, .000000001, 45, 315",
      "-.000000001, -.000000001, 135, 225",
      ".000000001, 0, 90, 90",
      "0, .000000001, 0, 0",
      "-.000000001, 0, 90, 270",
      "0, -.000000001, 180, 180",

  })
  void testBasicToward(String x, String y, String expectedValue, String expectedHeading)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(x, myTurtle));
    node.addChildren(new ConstantNode(y, myTurtle));
    assertEquals(Double.parseDouble(expectedValue), node.getValue(), DELTA);
    assertEquals(Double.parseDouble(expectedHeading), myTurtle.getHeading(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "150, 50, 45, 45",
      "150, -150, 135, 135",
      "-50, 50, 45, 315",
      "-50, -150, 135, 225",
      "150, -50, 90, 90",
      "50, 50, 0, 0",
      "-50, -50, 90, 270",
      "50, -150, 180, 180",
      "50, -50, 0, 0"
  })
  void testTowardNonZeroStart(String x, String y, String expectedValue, String expectedHeading)
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setX(50);
    myTurtle.setY(-50);
    node.addChildren(new ConstantNode(x, myTurtle));
    node.addChildren(new ConstantNode(y, myTurtle));
    assertEquals(Double.parseDouble(expectedValue), node.getValue(), DELTA);
    assertEquals(Double.parseDouble(expectedHeading), myTurtle.getHeading(), DELTA);
  }
}


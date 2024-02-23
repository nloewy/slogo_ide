package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class SetPositionCommandTest {
  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.turtle.SetPositionCommand", myTurtle);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0, 3, 4",
      "0, 0, 5, 12",
      "3.14, 2.71, 6.28, 5.42",
      "-8.39, 5.86, 9.25, -4.72",
      "0.5, -1.2, -3.7, 2.9",
      "7.3, -2.8, -5.6, -9.4",
      "-12.67, -5.82, 8.91, 0.003",
      "1.0, 0.0, 0.0, -1.0",
      "-3.0, -2.0, 1.0, 3.0",
      "5.5, -6.2, -7.3, 8.9",
      "4.6, -9.8, 6.2, 4.1",
      "-3.5, 2.3, 8.1, -6.7",
      "2147483647.0, 2147483647.0, -1.0, -1.0", // Points at max integers
      "-2147483648.0, -2147483648.0, 1.0, 1.0", // Points at min integers
      "3.4028235E38, -3.4028235E38, 0.0, 0.0", // Points at max/min floats
      "1.0, 1.0, 1.0001, 1.0001", // Points very close together
      "0.0, 0.0, 1000000.0, 1000000.0", // Points very far apart
      "0.0, 0.0, 3.0, 0.0", // Points on coordinate axes
      "1.0, 1.0, -1.0, -1.0"
  })

  void testBasicPosition(String originalX, String originalY, String newX, String newY)
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setX(Double.parseDouble(originalX));
    myTurtle.setY(Double.parseDouble(originalY));
    node.addChildren(new ConstantNode(newX, myTurtle));
    node.addChildren(new ConstantNode(newY, myTurtle));
    double expectedVal = Math.hypot(Double.parseDouble(newX) -myTurtle.getX(), Double.parseDouble(newY) - myTurtle.getY());
    assertEquals(expectedVal, node.getValue(), DELTA);
    assertEquals(Double.parseDouble(newX), myTurtle.getX(), DELTA);
    assertEquals(Double.parseDouble(newY), myTurtle.getY(), DELTA);
  }

}


package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.node.CommandNode;
import slogo.model.node.Node;

public class ClearScreenCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp() {
    myTurtle = new Turtle(1);
  }

  @ParameterizedTest
  @CsvSource({
      "3, 4",
      "0, 0",
      "3.14, 2.71",
      "-8.39, 5.86",
      "0.5, -1.2",
      "7.3, -2.8",
      "-12.67, -5.82",
      "8.0, 0.0",
      "-3.0, -2.0",
      "5.5, -6.2",
      "4.6, -9.8",
      "-3.5, 2.3",
      "2147483647.0, 2147483647.0",
      "-2147483648.0, -2147483648.0",
      "3.4028235E38, -3.4028235E38",
      "1.0001, 1.0001",
      "1000000.0, 1000000.0",
      "-1.0, -1.0"
  })
  void testBasicClearScreen(String originalX, String originalY)
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("turtle.ClearScreen", model);
    myTurtle.setX(Double.parseDouble(originalX));
    myTurtle.setY(Double.parseDouble(originalY));
    double expectedVal = Math.hypot(Double.parseDouble(originalX), Double.parseDouble(originalY));
    assertEquals(expectedVal, node.getValue(), DELTA);
    assertEquals(0, myTurtle.getX(), DELTA);
    assertEquals(0, myTurtle.getY(), DELTA);
  }
}

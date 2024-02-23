package slogo.model.command.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class YcoordinateCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.query.YcoordinateCommand", myTurtle);

  }

  @ParameterizedTest
  @CsvSource({
      "0",
      "-3939393948",
      "-8.39",
      "0.5",
      "7.3",
      "2147483647.0", // Points at max integers
      "-2147483648.0", // Points at min integers
      "3.4028235E38", // Points at max/min floats
      "1.0001"
  })
  void testYCorrBasic(String y)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(y, myTurtle));
    assertEquals(myTurtle.getY(), node.getValue(), DELTA);
  }

}
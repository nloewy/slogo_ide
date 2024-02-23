package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class ArcTanCommandTest {

  public static final double DELTA = 0.001;
  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    node = new CommandNode("slogo.model.command.math.ArcTangentCommand", myTurtle);
  }

  @ParameterizedTest
  @CsvSource({
      "0.0, 0",
      "0.57735026919, 30",
      "1.0, 45",
      "1.73205080757, 60",
      "-0.57735026919, -30",
      "-1.0, -45",
      "-1.73205080757, -60",
      "1.457732628, 55.55"
  })
  void testArctanBasic(String op, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChildren(new ConstantNode(op, myTurtle));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

}

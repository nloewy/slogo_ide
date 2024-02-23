package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class TrigCommandTests {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp() {
    myTurtle = null;
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0.0",
      "30, 0.5",
      "45, 0.70710678118",
      "60, 0.86602540378",
      "90, 1.0",
      "180, 0.0",
      "270, -1.0",
      "360, 0.0",
      "720, 0.0",
      "-30, -0.5",
      "-45, -0.70710678118",
      "-60, -0.86602540378",
      "-90, -1.0",
      "-180, 0.0",
      "-270, 1.0",
      "-360, 0.0",
      "1.5, 0.0261769483"
  })
  void testSineBasic(String op1, String result)
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.math.SineCommand", myTurtle);
    node.addChildren(new ConstantNode(op1, myTurtle));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }


  @ParameterizedTest
  @CsvSource({
      "0, 1.0",
      "30, 0.86602540378",
      "45, 0.70710678118",
      "60, 0.5",
      "90, 0.0",
      "180, -1.0",
      "270, 0.0",
      "360, 1.0",
      "720, 1.0",
      "-30, 0.86602540378",
      "-45, 0.70710678118",
      "-60, 0.5",
      "-90, 0.0",
      "-180, -1.0",
      "-270, 0.0",
      "-360, 1.0",
      "88.5, 0.0261769483"
  })
  void testCosineBasic(String op1, String result)
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.math.CosineCommand", myTurtle);
    node.addChildren(new ConstantNode(op1, myTurtle));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0.0",
      "30, 0.57735026919",
      "45, 1.0",
      "60, 1.73205080757",
      "180, 0.0",
      "360, 0.0",
      "720, 0.0",
      "-30, -0.57735026919",
      "-45, -1.0",
      "-60, -1.73205080757",
      "-180, 0.0",
      "-360, 0.0",
      "55.55, 1.457732628"
  })
  void testTangentBasic(String op1, String result)
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    node = new CommandNode("slogo.model.command.math.TangentCommand", myTurtle);
    node.addChildren(new ConstantNode(op1, myTurtle));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "90",
      "270",
      "990",
      "-90",
      "-270",
      "-1170"
  })
  void testTangentInvalid(String degrees)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    node = new CommandNode("slogo.model.command.math.TangentCommand", myTurtle);
    node.addChildren(new ConstantNode(degrees, myTurtle));
    Throwable e = assertThrows(InvocationTargetException.class, () -> {node.getValue();});
    assertTrue(e.getCause() instanceof ArithmeticException);
  }
}
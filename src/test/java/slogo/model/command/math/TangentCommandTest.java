package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class TangentCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("slogo.model.command.math.TangentCommand", model);
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
      throws InvocationTargetException, IllegalAccessException {
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
    node.addChildren(new ConstantNode(degrees, myTurtle));
    Throwable e = assertThrows(ArithmeticException.class, () -> {node.getValue();});
  }
}
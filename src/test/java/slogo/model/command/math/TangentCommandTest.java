package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.api.InvalidOperandException;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class TangentCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.Tangent", model);
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
    node.addChild(new ConstantNode(op1, null));
    assertEquals(Double.parseDouble(result), node.evaluate(), DELTA);
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
    node.addChild(new ConstantNode(degrees, null));
    assertThrows(InvalidOperandException.class, () -> {
      node.evaluate();
    });
  }
}

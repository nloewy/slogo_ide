package slogo.model.command.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class LogCommandTest extends CommandTest {

  public static final double DELTA = 0.1;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    myTurtle = null;
    ModelState model = new ModelState();
    node = new CommandNode("math.NaturalLog", model, myListener);

  }

  @Test
  void testLogBasicE()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(Double.toString(Math.E), null, myListener));
    assertEquals(1, node.getValue(), DELTA);
  }

  @Test
  void testLogBasicOne()
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode("1", null, myListener));
    assertEquals(0, node.getValue(), DELTA);
  }

  @ParameterizedTest
  @CsvSource({
      "2, 0.69314718056",
      "5, 1.60943791243",
      "10, 2.30258509299",
      "100, 4.60517018599",
      "1000, 6.90775527898",
      "10000, 9.21034037198",
      "123456, 11.7236376114",
      "987654, 13.8007711417",
      "1000000, 13.8155105579",
      "12345678, 16.3238816252",
      "100000000, 18.420680744",
      "123456789, 18.6297098477",
      "987654321, 20.7074430552",
      "1000000000, 20.7232658369",
      "2.5, 0.91629073187",
      "5.8, 1.75785791755",
      "11.3, 2.42480235444",
      "23.7, 3.16547504981"
  })
  void testLogFloats(String op1, String result)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null, myListener));
    assertEquals(Double.parseDouble(result), node.getValue(), DELTA);
  }


  @ParameterizedTest
  @CsvSource({
      "0",
      "-.000001",
      "-3",
      "-3.85885",
      "-3939393.333",
  })
  void testLogNonPositive(String op1)
      throws InvocationTargetException, IllegalAccessException {
    node.addChild(new ConstantNode(op1, null, myListener));
    Throwable e = assertThrows(InvocationTargetException.class, () -> {
      node.getValue();
    });
    assertInstanceOf(IllegalArgumentException.class, e.getCause());
  }
}

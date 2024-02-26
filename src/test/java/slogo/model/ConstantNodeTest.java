package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

public class ConstantNodeTest {


  private final Turtle myTurtle = null;

  private final ModelState model = new ModelState();

  @Test
  void testConstantNodeIntInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("4", model);
    assertEquals(4.0, node.getValue());
  }

  @Test
  void testConstantNodeDoubleInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("40.9999999", model);
    assertEquals(40.9999999, node.getValue());
  }

  @Test
  void testConstantNodeNegativeInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("-40.9999999", model);
    assertEquals(-40.9999999, node.getValue());
  }

  @Test
  void testConstantNodeInvalidInput() {
    assertThrows(NumberFormatException.class, () -> new ConstantNode("-40.999S999", model));
  }

  @Test
  void testConstantNodeLeadingZeros() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("000054.54", model);
    assertEquals(54.54, node.getValue());
  }
}
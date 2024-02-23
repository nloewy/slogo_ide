package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
public class ConstantNodeTest {


  private Turtle myTurtle = null;
  @Test
  void testConstantNodeIntInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("4", myTurtle);
    assertEquals(4.0, node.getValue());
  }
  @Test
  void testConstantNodeDoubleInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("40.9999999", myTurtle);
    assertEquals(40.9999999, node.getValue());
  }
  @Test
  void testConstantNodeNegativeInput() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("-40.9999999", myTurtle);
    assertEquals(-40.9999999, node.getValue());
  }
  @Test
  void testConstantNodeInvalidInput() {
    assertThrows(NumberFormatException.class, () -> new ConstantNode("-40.999S999", myTurtle));
  }
  @Test
  void testConstantNodeLeadingZeros() throws InvocationTargetException, IllegalAccessException {
    Node node = new ConstantNode("000054.54", myTurtle);
    assertEquals(54.54, node.getValue());  }
  }
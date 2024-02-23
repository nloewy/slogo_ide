package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class PenUpTest {
  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTurtle = new Turtle(1);
    node = new CommandNode("slogo.model.command.turtle.PenUpCommand", myTurtle);
  }

  @Test
  void testBasicUp()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(false);
    Assertions.assertEquals(0, node.getValue());
    Assertions.assertFalse(myTurtle.getPen());
  }

  @Test
  void testPenUpAlreadyUp()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setPen(false);
    Assertions.assertEquals(0, node.getValue());
    Assertions.assertFalse(myTurtle.getPen());
  }
}


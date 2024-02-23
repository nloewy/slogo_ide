package slogo.model.command.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ConstantNode;
import slogo.model.Node;
import slogo.model.Turtle;

public class ForwardCommandTest {


  private Turtle myTurtle;

  @BeforeEach
  void setUp() {
    myTurtle = new Turtle(1);
  }

  @Test
  void testBasicForward()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Node node = new CommandNode("slogo.model.command.turtle.ForwardCommand", myTurtle);
    String dist = "75";
    node.addChildren(new ConstantNode(dist, myTurtle));
    assertEquals(node.getValue(), 75);
    assertEquals(myTurtle.getY(), 75);
  }
}
package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandNode;
import slogo.model.ModelState;
import slogo.model.Node;
import slogo.model.Turtle;

public class ShowTurtleCommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ModelState model = new ModelState();
    model.getTurtles().add(new Turtle(1));
    myTurtle = model.getTurtles().get(0);
    node = new CommandNode("slogo.model.command.turtle.ShowTurtleCommand", model);
  }

  @Test
  void testBasicShowTurtle()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(false);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getVisible());
  }

  @Test
  void testShowTurtleAlreadyShown()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(true);
    Assertions.assertEquals(1, node.getValue());
    Assertions.assertTrue(myTurtle.getVisible());
  }
}


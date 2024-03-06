package slogo.model.command.turtle;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.Node;

public class ShowTurtleCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ModelState model = new ModelState();
    model.getTurtles().put(1, new Turtle(1));
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);
    myTurtle = model.getTurtles().get(1);
    node = new CommandNode("ShowTurtle", model);
    node.addListener(myListener);
  }

  @Test
  void testBasicShowTurtle()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(false);
    Assertions.assertEquals(1, node.evaluate());
    Assertions.assertTrue(myTurtle.getVisible());
  }

  @Test
  void testShowTurtleAlreadyShown()
      throws InvocationTargetException, IllegalAccessException {
    myTurtle.setVisible(true);
    Assertions.assertEquals(1, node.evaluate());
    Assertions.assertTrue(myTurtle.getVisible());
  }
}


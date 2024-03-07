package slogo.model.command.display;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.api.Model;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class PenColorCommandTest extends CommandTest {

  public static final double DELTA = 0.001;

  private Turtle myTurtle;
  private Node node;
  private ModelState model;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    model = new ModelState();
    model.getActiveTurtles().add(new ArrayList<>());
    model.getActiveTurtles().peek().add(1);
    model.getTurtles().put(1, new Turtle(1));
  }

  @Test
  void testPenColor()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model.getTurtles().get(1).setPenColor(3);
    node = new CommandNode("PenColor", model);
    Assertions.assertEquals(3, node.evaluate());
  }

  @Test
  void testSetPenColor()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    model.getTurtles().get(1).setPenColor(3);
    node = new CommandNode("SetPenColor", model);
    Node nodeChild = new ConstantNode("4", model);
    node.addChild(nodeChild);
    node.addListener(myListener);
    node.evaluate();
    Assertions.assertEquals(4, model.getTurtles().get(1).getPenColor());
  }

}
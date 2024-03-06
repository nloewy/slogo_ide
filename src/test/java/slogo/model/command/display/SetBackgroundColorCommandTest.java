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

public class SetBackgroundColorCommandTest extends CommandTest {

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
    model.getTurtles().put(2, new Turtle(2));
  }

  @Test
  void testBgColor()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    node = new CommandNode("display.SetBackground", model);
    node.addChild(new ConstantNode("4", model));
    Assertions.assertEquals(4, node.evaluate());
    Assertions.assertEquals(4, model.getTurtles().get(1).getImmutableTurtle().bgIndex());
    Assertions.assertEquals(4, model.getTurtles().get(2).getImmutableTurtle().bgIndex());
  }

}
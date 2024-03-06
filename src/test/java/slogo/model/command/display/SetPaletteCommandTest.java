package slogo.model.command.display;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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

public class SetPaletteCommandTest extends CommandTest {

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
    node = new CommandNode("SetPalette", model);
    node.addChild(new ConstantNode("8", model));
    node.addChild(new ConstantNode("200", model));
    node.addChild(new ConstantNode("100", model));
    node.addChild(new ConstantNode("50", model));
    node.addListener(myListener);
    Assertions.assertEquals(8, node.evaluate());
    Assertions.assertEquals(8, model.getPalette().size());
    Assertions.assertEquals(List.of(200, 100, 50), model.getPalette().get(8));
    Assertions.assertEquals(List.of(0, 0, 0), model.getPalette().get(5));
  }

}
package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.exceptions.InvalidOperandException;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;



  public class IdCommandTest extends CommandTest {

    public static final double DELTA = 0.001;

    private Turtle myTurtle;
    private Node node;
    private ModelState model;



   @Test
   void testIdBasic()
        throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
      model = new ModelState();
      model.getTurtles().clear();
      model.getTurtles().put(5, new Turtle(5));
      model.getActiveTurtles().add(new ArrayList<>());
      model.getActiveTurtles().get(0).add(5);
      node = new CommandNode("multiple.Id", model);
      assertEquals(5.0, node.evaluate(), DELTA);
    }

}

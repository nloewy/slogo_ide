package slogo.model.command.multiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import slogo.model.ModelState;
import slogo.model.Turtle;
import slogo.model.command.CommandTest;
import slogo.model.node.CommandNode;
import slogo.model.node.ConstantNode;
import slogo.model.node.ListNode;
import slogo.model.node.Node;

public class AskWithCommandTest extends CommandTest {


    public static final double DELTA = 0.001;
    private ModelState model;
    private Node node;

    @Test
    void testTurtlesNoExpression()
        throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
      model = new ModelState();
      model.getTurtles().clear();
      model.getActiveTurtles().add(new ArrayList<>());
      model.getActiveTurtles().peek().add(17);
      model.getTurtles().put(17, new Turtle(17));
      for(int i = 0; i < 16; i++) {
        model.getTurtles().put(i, new Turtle(i));
      }
      Node askWithNode = new CommandNode("multiple.AskWith", model);
      Node list1 = new CommandNode("control.If", model) ;
      Node equal = new CommandNode("bool.Equal", model) ;

      Node remainder = new CommandNode("math.Remainder", model);
      Node dividend = new CommandNode("multiple.Id", model) ;
      Node divisor = new ConstantNode("3", model) ;
      Node zero = new ConstantNode("0", model);
      askWithNode.addChild(list1);
      list1.addChild(equal);
      list1.addChild(new ConstantNode("1", model));
      equal.addChild(remainder);
      remainder.addChild(dividend);
      remainder.addChild(divisor);
      equal.addChild(zero);
      Node list2 = new ListNode("[", model) ;
      askWithNode.getChildren().add(list2);
      list1.addListener(myListener);
      list2.addListener(myListener);
      Node rightNode = new CommandNode("turtle.Right", model);
      rightNode.addListener(myListener);
      Node nodeNinty = new ConstantNode("90", model);
      list2.getChildren().add(rightNode);
      rightNode.getChildren().add(nodeNinty);
      assertEquals(90.0, askWithNode.evaluate(), DELTA);
      assertTrue(model.getActiveTurtles().peek().contains(17));
      assertEquals(1,model.getActiveTurtles().size());
      assertEquals(17,model.getTurtles().size());
      Node r = new CommandNode("turtle.Right", model);
      r.addListener(myListener);
      Node n90 = new ConstantNode("60", model);
      r.addChild(n90);
      r.evaluate();
      for(int i = 0; i< 16; i++) {
        if(i%3==0) {
          assertEquals(90, model.getTurtles().get(i).getHeading(), DELTA);
        }
        else {
          assertEquals(0, model.getTurtles().get(i).getHeading(), DELTA);
        }
      }
      assertEquals(60, model.getTurtles().get(17).getHeading(), DELTA);



    }

}

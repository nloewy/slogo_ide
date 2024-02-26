package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.Model;
import slogo.model.api.TurtleRecord;
import slogo.model.node.ConstantNode;
import slogo.model.node.Node;

public class ParseTest {


  public static final double DELTA = .0001;
  private Turtle myTurtle;
  private ModelState model;
  private SlogoModel slogo;

  @BeforeEach
  void setUp() {

    slogo = new SlogoModel(new SlogoListener() {
      @Override
      public void onUpdateValue(String variableName, Number newValue) {
      }

      @Override
      public void onUpdateTurtleState(TurtleRecord turtleState) {
      }

      @Override
      public void onResetTurtle(int id) {
      }

      @Override
      public void onReturn(double value) {
      }
    });

  }


  @Test
  void testCommandOne()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    slogo.parse("PENUP FORWARD #LOL IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(60.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testCommandTwo()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
    slogo.parse("PENDOWN #LOL IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(0);
    assertEquals(80.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

}
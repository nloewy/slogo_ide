package slogo.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.Turtle;
import slogo.model.api.SlogoException;
import slogo.model.api.SlogoListener;
import slogo.model.api.SlogoModel;
import slogo.model.api.TurtleRecord;
import slogo.model.exceptions.InvalidCommandException;
import slogo.model.exceptions.InvalidTokenException;

public class ParseTest {


  public static final double DELTA = .0001;
  private SlogoModel slogo;

  @BeforeEach
  void setUp() throws IOException {

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
      public void onReturn(double value, String string) {
      }

      @Override
      public void onUserDefinedCommand(String s) {
      }

      @Override
      public void onSetActiveTurtles(List<Integer> ids) {

      }

      @Override
      public void onUpdatePalette(Map<Integer, List<Integer>> pallette) {

      }
    }, "English");


  }

  @Test
  void testCommandZero()
      throws InvalidTokenException, InvalidCommandException {
    slogo.parse("PENUP");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertFalse(myTurtle.getPen());
  }


  @Test
  void testNestedIfElseFalse()
      throws SlogoException {
    slogo.parse("PENUP fd #LOL IFELSE PENDOWN? SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertFalse(myTurtle.getPen());
    assertEquals(-60.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testNestedIfElseTrue()
      throws SlogoException {
    slogo.parse("PENDOWN #LOL fd IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(-80.0, myTurtle.getY(), DELTA);

  }

  @Test
  void valueWithNoCommandAlone()
      throws SlogoException {
    assertThrows(SlogoException.class, () -> {
      slogo.parse(
          "50");
    });
  }

  @Test
  void mathErrorHalfwayThrough()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertThrows(SlogoException.class, () -> {
      slogo.parse("fd SUM 10 10 fd QUOTIENT 5 0 fd SUM 10 10");
    });
    assertEquals(-20.0, myTurtle.getY(), DELTA);
  }


  @Test
  void constantWithNoCommandBetweenProperCommands()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertThrows(SlogoException.class, () -> {
      slogo.parse("FD 10 10 RT 30");
      assertEquals(10.0, myTurtle.getY(), DELTA);
    });

  }

  @Test
  void variableWithNoCommandBetweenProperCommands()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertThrows(SlogoException.class, () -> {
      slogo.parse("FD 10 :C RT 30");
      assertEquals(10.0, myTurtle.getY(), DELTA);
    });

  }

  @Test
  void testNestedIfElseList()
      throws SlogoException {
    slogo.parse(
        "[ [ [ [ [ PENDOWN #LOL fd IFELSE PENDOWNP SUM 50 30 PRODUCT 30 SUM 1 1 RT 50 ] ] ] ] ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
    assertEquals(-80.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testConstantsInIfElse()
      throws SlogoException {
    slogo.parse("IFELSE 1 1 1");
  }


  @Test
  void testNestedError()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertThrows(SlogoException.class, () -> {
      slogo.parse(
          "BK 10 REPEAT 5 [ IFELSE PENDOWN? [ FD 10 FD 10R ] FD 50 ]");
      assertEquals(-10.0, myTurtle.getY(), DELTA);
    });
  }

  @Test
  void testVariables()
      throws SlogoException {
    slogo.parse("MAKE :CLASS 308 fd SUM :CLASS 192");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(-500.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testRepeat()
      throws SlogoException {
    slogo.parse("MAKE :CLASS 10 REPEAT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(100.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testInvalidCommand()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertThrows(SlogoException.class, () -> {
      slogo.parse(
          "MAKE :CLASS 10 RANDOMTEXT DIFFERENCE :CLASS 5 [ RIGHT :CLASS ] RIGHT 50");
      assertEquals(10.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
      assertEquals(00.0, slogo.getModelstate().getTurtles().get(1).getHeading(), DELTA);

    });
  }

  @Test
  void testInvalidToken()
      throws SlogoException {

    assertThrows(SlogoException.class, () -> {
      slogo.parse("MAKE :CLASS 10 FD :CLaSs REPEAT DIFFERENCE :CLASS 5 [ RIGHT4 :CLASS ] RIGHT 50");
      assertEquals(10.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
      assertEquals(10.0, slogo.getModelstate().getTurtles().get(1).getY(), DELTA);
      assertEquals(00.0, slogo.getModelstate().getTurtles().get(1).getHeading(), DELTA);
    });
  }


  @Test
  void testNotEnoughArguments()
      throws SlogoException {
    assertThrows(SlogoException.class, () -> {
      slogo.parse("MAKE :CLASS");
    });
  }

  @Test
  void testCaseSensitiveCommands()
      throws SlogoException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLASS 5 [ RIGHT :CLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testVariablesNotDefined()
      throws SlogoException {
    assertFalse(slogo.getModelstate().getVariables().containsKey(":CLASS"));
    slogo.parse("REPEAT 5 [ RIGHT NOT :CLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(5.0, myTurtle.getHeading(), DELTA);
    assertEquals(0.0, slogo.getModelstate().getVariables().get(":class"), DELTA);
  }

  @Test
  void testCaseSensitiveVariables()
      throws SlogoException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLass 5 [ RIGHT :cLASS ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testExtraParam()
      throws SlogoException {
    slogo.parse("mAkE :CLASS 10 REPEAT dIffEreNcE :CLass 5 [ RIGHT :cLASS 10 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testRandom()
      throws SlogoException {

    for (int i = 0; i < 100; i++) {
      slogo.parse("rt 270 mAke :random sum 1 random 100 fd sum 1 :random");
      Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
      assertEquals(0.0, myTurtle.getY(), DELTA);
      if (!(myTurtle.getX() <= -1.999 && myTurtle.getX() >= -102.001)) {
        System.out.println(myTurtle.getX());
      }
      assertTrue(myTurtle.getX() <= -1.999 && myTurtle.getX() >= -102.001);
      assertEquals(270.0, myTurtle.getHeading(), DELTA);
      slogo.parse("cs");
    }
  }

  @Test
  void testDashWithoutUserDefined()
      throws SlogoException {
    slogo.parse("repeat 12 [ fd 20 fd 20 ]");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(-480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testSimpleTo()
      throws SlogoException {
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    slogo.parse("to fwd [ :dist ] [ fd :dist ]");
    slogo.parse("fwd 50");
    assertEquals(-50.0, myTurtle.getY(), DELTA);
    slogo.parse("fwd 100");
    assertEquals(-150.0, myTurtle.getY(), DELTA);

  }

  @Test
  void testDashNoParams()
      throws SlogoException {
    slogo.parse(
        "set :count 12 set :distance 20 to dash [ ] [ repeat :count [ pu fd :distance pd fd :distance ] ] dash");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(12.0, slogo.getModelstate().getVariables().get(":count"));
    assertEquals(20.0, slogo.getModelstate().getVariables().get(":distance"));

    assertEquals(-480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashWithParams()
      throws SlogoException {
    slogo.parse(
        "to dash [ :count :distance ] [ repeat :count [ pu fd :distance pd fd :distance ] ] dash 12 20");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(-480.0, myTurtle.getY(), DELTA);
  }

  @Test
  void testDashWithStuffAfter()
      throws SlogoException {
    slogo.parse(
        "to dash [ :count :distance ] [ repeat :count [ pu fd :distance pd fd :distance ] ] dash 12 20 rt 50");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(-480.0, myTurtle.getY(), DELTA);
    assertEquals(50.0, myTurtle.getHeading(), DELTA);
  }

  @Test
  void testDoubleUserDefinedNoParams()
      throws SlogoException {
    slogo.parse(
        "to       partialrect [ ] [ fd 50 rt 90 fd         60 rt 90 fd 30 rt 90 ] to twice         [ ] [ repeat 2 [ partialrect ] ] twice fd 60 left 100");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(40.0, myTurtle.getX(), DELTA);
    assertEquals(-20.0, myTurtle.getY(), DELTA);
    assertEquals(440.0, myTurtle.getHeading(), DELTA);
  }

}

package slogo.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.Turtle;
import slogo.model.api.SlogoException;
import slogo.model.api.SlogoListener;
import slogo.model.api.SlogoModel;
import slogo.model.api.TurtleRecord;
import slogo.model.command.CommandTest;
import slogo.model.exceptions.InvalidCommandException;
import slogo.model.exceptions.InvalidTokenException;

public class AskWithTellAskCommandTest extends CommandTest {

  public static final double DELTA = .0001;
  public SlogoModel slogo;

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
  void testPartialFlower()
      throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidCommandException, InvalidTokenException {
    slogo.parse(
        "to arc [ :incr :degrees ] [ repeat quotIEnt   :degrees 2 [ fd 40 rt :incr ] ] to petal [ :size ] [ repeat 2 [ arc :size 60 rt 120 ] ] to fLOwer [ :length ] [ repeat 5 [ petal :length rt 60 ] ] flowEr 100");
    Turtle myTurtle = slogo.getModelstate().getTurtles().get(1);
    assertTrue(myTurtle.getPen());
    assertEquals(-34.6410161514, myTurtle.getX(), DELTA);
    assertEquals(-29.0672638767, myTurtle.getY(), DELTA);
  }


  @Test
  void testVariableMaker()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("set :count 16");
    assertEquals(16.0, slogo.getModelstate().getVariables().get(":count"));
    slogo.parse("pendown");
    assertTrue(slogo.getModelstate().getTurtles().get(1).getPen());
    slogo.parse("set :count ifelse pendown? power :count 2 sqrt :count");
    assertEquals(256.0, slogo.getModelstate().getVariables().get(":count"));
    slogo.parse("set :count 16");
    slogo.parse("penup");
    slogo.parse("set :count ifelse pendown? power :count 2 sqrt :count");
    assertEquals(4.0, slogo.getModelstate().getVariables().get(":count"));
  }


  @Test
  void testVariables1()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("make :x 10 tell [ 1 2 3 ] make :x [ * :x :x ] fd :x");
    assertEquals(100, slogo.getModelstate().getVariables().get(":x"));
    assertEquals(-100.0, slogo.getModelstate().getTurtles().get(1).getY());
    assertEquals(-100.0, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(-100.0, slogo.getModelstate().getTurtles().get(3).getY());
  }

  @Test
  void testVariables2()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("set :x 10 tell [ 1 2 3 ] fd * :x id");
    assertEquals(-10, slogo.getModelstate().getTurtles().get(1).getY());
    assertEquals(-20, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(-30, slogo.getModelstate().getTurtles().get(3).getY());
  }

  @Test
  void testVariables3()
      throws InvocationTargetException, InvalidCommandException, InvalidTokenException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
    slogo.parse("set :x 10 tell [ 1 2 3 ] fd set :x [ + :x 10 ]");
    assertEquals(-20, slogo.getModelstate().getTurtles().get(1).getY());
    assertEquals(-30, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(-40, slogo.getModelstate().getTurtles().get(3).getY());
  }

  @Test
  void askCommandNoPara() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("ask 2 fd 90 rt 90");
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(90, slogo.getModelstate().getTurtles().get(1).getHeading());
    assertEquals(0, slogo.getModelstate().getTurtles().get(2).getHeading());
  }

  @Test
  void askCommandWithPara() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("ask [ 2 ] [ fd 90 rt 90 ]");
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(90, slogo.getModelstate().getTurtles().get(2).getHeading());
  }

  @Test
  void askWithCommandNoPara() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("tell 2 askwith id [ fd 90 rt 90 ]");
    assertEquals(-90, slogo.getModelstate().getTurtles().get(1).getY());
    assertEquals(90, slogo.getModelstate().getTurtles().get(1).getHeading());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(90, slogo.getModelstate().getTurtles().get(2).getHeading());
  }

  @Test
  void askWithCommandYesPara() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("tell [ 1 2 ] askwith - id 1 [ fd 90 ] rt 90");
    assertEquals(90, slogo.getModelstate().getTurtles().get(2).getHeading());
    assertEquals(90, slogo.getModelstate().getTurtles().get(1).getHeading());
    assertEquals(0, slogo.getModelstate().getTurtles().get(1).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());

  }

  @Test
  void flower() throws InvocationTargetException, IllegalAccessException {
    slogo.parse(
        "tell 2 fd 90 tell [ 1 2 ] repeat 6 [ Repeat 2 [ Repeat 30 [ fd 10 right 2 ] right 120 ] right 60 ]");
    assertEquals(0, slogo.getModelstate().getTurtles().get(1).getY(), DELTA);
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY(), DELTA);
  }

  @Test
  void flowerCommand() throws InvocationTargetException, IllegalAccessException {
    slogo.parse(
        "Ask 2 fd 90 ask 3 fd 180 tell [ 1 2 3 ] to arc [ :incr :degrees ] [  repeat quotient :degrees 2  [  fd :incr rt 2  ] ] to petal [ :size ] [  repeat 2  [    arc :size 60    rt 120  ] ] to flower [ :length ] [  repeat 6  [    petal :length    rt 60  ] ] ");
    slogo.parse("flower 3");
    assertEquals(0, slogo.getModelstate().getTurtles().get(1).getY(), DELTA);
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY(), DELTA);
  }

  @Test
  void halfSquare() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("tell 2 fd 90 tell [ 1 2 ] fd 90 rt 90 fd 90");
    assertEquals(-90, slogo.getModelstate().getTurtles().get(1).getY(), DELTA);
    assertEquals(90, slogo.getModelstate().getTurtles().get(1).getX(), DELTA);
    assertEquals(-180, slogo.getModelstate().getTurtles().get(2).getY(), DELTA);
    assertEquals(90, slogo.getModelstate().getTurtles().get(2).getX(), DELTA);
  }

  @Test
  void halfSquareCommand() throws InvocationTargetException, IllegalAccessException {
    slogo.parse("tell 2 fd 90 to square [ ] [ fd 90 rt 90 fd 90 ] tell [ 1 2 ] square");
    assertEquals(90, slogo.getModelstate().getTurtles().get(2).getX(), DELTA);
    assertEquals(-90, slogo.getModelstate().getTurtles().get(1).getY(), DELTA);
    assertEquals(90, slogo.getModelstate().getTurtles().get(1).getX(), DELTA);
    assertEquals(-180, slogo.getModelstate().getTurtles().get(2).getY(), DELTA);


  }

  @Test
  void askWithCommandTest() throws InvocationTargetException, IllegalAccessException {
    slogo.parse(
        "tell [ 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 17 ] tell 17 askwith equal remainder id 3 0 [ right 90 ] right 60");
    for (int i = 0; i < 16; i++) {

      if (i % 3 == 0) {
        assertEquals(90, slogo.getModelstate().getTurtles().get(i).getHeading(), DELTA);
      } else {
        assertEquals(0, slogo.getModelstate().getTurtles().get(i).getHeading(), DELTA);
      }
    }
    assertEquals(60, slogo.getModelstate().getTurtles().get(17).getHeading(), DELTA);

  }

  @Test
  void testInvalidUserCommand() throws InvocationTargetException, IllegalAccessException {
    assertThrows(SlogoException.class, () -> {
      slogo.parse(
          "to fd [ ] [ fd 90 ] ");
    });
  }

  @Test
  void testInvalidVariable() throws InvocationTargetException, IllegalAccessException {
    assertThrows(SlogoException.class, () -> {
      slogo.parse(
          "to fd [ 90 ] [ fd 90 ] ");
    });
  }


  @Test
  void testRightMultipleActive()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    slogo.parse("TELL [ 4 5 10 9 2 ] TELL [ 5 10 9 2 ] RIGHT 80");

    assertEquals(80, slogo.getModelstate().getTurtles().get(5).getHeading(), DELTA);
    assertEquals(80, slogo.getModelstate().getTurtles().get(10).getHeading(), DELTA);
    assertEquals(80, slogo.getModelstate().getTurtles().get(9).getHeading(), DELTA);
    assertEquals(80, slogo.getModelstate().getTurtles().get(2).getHeading(), DELTA);
    assertEquals(0, slogo.getModelstate().getTurtles().get(4).getHeading(), DELTA);
  }

  @Test
  void testTurtlesNoExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    slogo.parse("TELL [ 5 4 10 9 ] TELL [ 5 10 9 ] ASK [ 20 9 ] [ RIGHT 90 ] RIGHT 80");
    assertEquals(3.0, slogo.getModelstate().getActiveTurtles().peek().size(), DELTA);
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(10));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(9));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(5));
    assertEquals(80, slogo.getModelstate().getTurtles().get(5).getHeading(), DELTA);
    assertEquals(0, slogo.getModelstate().getTurtles().get(4).getHeading(), DELTA);
    assertEquals(80, slogo.getModelstate().getTurtles().get(10).getHeading(), DELTA);
    assertEquals(90, slogo.getModelstate().getTurtles().get(20).getHeading(), DELTA);
    assertEquals(170, slogo.getModelstate().getTurtles().get(9).getHeading(), DELTA);
  }


  @Test
  void testTurtlesWithExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    slogo.parse("TELL [ 5 4 10 9 2 ] TELL [ 5 10 9 ] ASK [ SUM 4 16.2 9 ] [ RIGHT 90 ] RIGHT 80");
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(10));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(9));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(5));
    assertEquals(80, slogo.getModelstate().getTurtles().get(5).getHeading(), DELTA);
    assertEquals(0, slogo.getModelstate().getTurtles().get(4).getHeading(), DELTA);
    assertEquals(80, slogo.getModelstate().getTurtles().get(10).getHeading(), DELTA);
    assertEquals(0, slogo.getModelstate().getTurtles().get(2).getHeading(), DELTA);
    assertEquals(90, slogo.getModelstate().getTurtles().get(20).getHeading(), DELTA);
    assertEquals(170, slogo.getModelstate().getTurtles().get(9).getHeading(), DELTA);
  }


  @Test
  void testTellNoExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    slogo.parse("TELL [ 5 4 10 9 ] TELL [ 5 2 9 ] FD 90");
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(2));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(9));
    assertTrue(slogo.getModelstate().getActiveTurtles().peek().contains(5));
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(9).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(5).getY());
    assertEquals(0, slogo.getModelstate().getTurtles().get(4).getY());
    assertEquals(0, slogo.getModelstate().getTurtles().get(10).getY());
  }

  @Test
  void testTellWithExpression()
      throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
    slogo.parse("TELL [ 5 4 10 9 ] TELL [ 9 2 8 30 SUM 4 1 50 ] FD 90");

    assertEquals(-90, slogo.getModelstate().getTurtles().get(9).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(5).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(2).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(8).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(50).getY());
    assertEquals(-90, slogo.getModelstate().getTurtles().get(30).getY());
    assertEquals(0, slogo.getModelstate().getTurtles().get(4).getY());

  }


}

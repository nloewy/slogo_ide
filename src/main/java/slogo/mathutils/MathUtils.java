package slogo.mathutils;

public class MathUtils {
  public static double toDegrees(double radians) {
    return radians * 180 / Math.PI;
  }

  public static double toRadians(double degrees) {
    return degrees * Math.PI / 180;
  }

  public static double dist(double x, double y, double currentX, double currentY) {
    return Math.sqrt(Math.pow((x-currentX),2) + Math.pow((y-currentY),2));
  }

}

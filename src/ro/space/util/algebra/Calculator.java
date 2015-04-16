
package ro.space.util.algebra;

import ro.space.display.particles.Trio;

public class Calculator {

  public static Trio add(Trio first, Trio second) {
    float a = first.getX() + second.getX();
    float b = first.getY() + second.getY();
    float c = first.getZ() + second.getZ();

    return new Trio(a, b, c);
  }

  public static Trio subtract(Trio first, Trio second) {
    float a = first.getX() - second.getX();
    float b = first.getY() - second.getY();
    float c = first.getZ() - second.getZ();

    return new Trio(a, b, c);
  }

  public static Trio normalize(Trio vector) {

    float x = vector.getX();
    float y = vector.getY();
    float z = vector.getZ();

    double xSquared = Math.pow(x, 2);
    double ySquared = Math.pow(y, 2);
    double zSquared = Math.pow(z, 2);

    double magnitude = Math.sqrt(xSquared + ySquared + zSquared);

    if (magnitude == 0.0f) {
      magnitude = 1.0f;
    }

    float a = x / (float) magnitude;
    float b = y / (float) magnitude;
    float c = z / (float) magnitude;

    return new Trio(a, b, c);
  }

  public static Trio cross(Trio first, Trio second) {
    float a1 = first.getX();
    float a2 = first.getY();
    float a3 = first.getZ();

    float b1 = second.getX();
    float b2 = second.getY();
    float b3 = second.getZ();

    float a = a2 * b3 - a3 * b2;

    float b = a3 * b1 - a1 * b3;

    float c = a1 * b2 - a2 * b1;

    return new Trio(a, b, c);
  }

  public static Trio scale(Trio vector, int scale) {
    float a = vector.getX() * scale;
    float b = vector.getY() * scale;
    float c = vector.getZ() * scale;

    return new Trio(a, b, c);
  }
}
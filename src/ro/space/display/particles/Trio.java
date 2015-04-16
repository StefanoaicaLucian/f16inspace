
package ro.space.display.particles;

public class Trio {

  private float x;
  private float y;
  private float z;

  public Trio(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Trio add(Trio vector) {
    x += vector.getX();
    y += vector.getY();
    z += vector.getZ();

    return this;
  }

  @Override
  public String toString() {
    return "Trio [x=" + x + ", y=" + y + ", z=" + z + "]";
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getZ() {
    return z;
  }
}
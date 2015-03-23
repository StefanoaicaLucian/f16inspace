
package ro.space.constants;

public enum Numbers {

  NUMBER_OF_PARTICLES(10),
  CANVAS_WIDTH(800),
  CANVAS_HEIGHT(600),
  FPS(60);

  private final int value;

  private Numbers(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
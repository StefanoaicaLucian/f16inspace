
package ro.space.display.graphic_objects;

import javax.media.opengl.GL2;

public class Floor extends GraphicObject {

  public Floor(GL2 gl) {
    super(gl);
  }

  @Override
  protected void display() {
    gl.glPushMatrix();
    draw();
    gl.glPopMatrix();
  }
}


package ro.space.display.graphic_objects;

import javax.media.opengl.GL2;

public class Floor extends GraphicObject {

  public Floor(GL2 gl) {
    super(gl);
  }

  @Override
  protected void display() {
    gl.glPushMatrix();

    gl.glTranslatef(0.0f, -1.0f, 0.0f);

    draw();
    gl.glPopMatrix();
  }
}

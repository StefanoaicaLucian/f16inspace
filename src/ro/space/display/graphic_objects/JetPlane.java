
package ro.space.display.graphic_objects;

import javax.media.opengl.GL2;

import static javax.media.opengl.GL2.GL_BLEND;

public class JetPlane extends GraphicObject {

  private float rotationAngle;

  public JetPlane(GL2 gl) {
    super(gl);
  }

  public void display() {

    gl.glDisable(GL_BLEND);

    gl.glPushMatrix();

    gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
    draw();

    gl.glPopMatrix();

    rotationAngle += 0.5f;
  }
}
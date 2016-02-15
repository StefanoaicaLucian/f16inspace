
package ro.uvt.space.graphic_objects;

import javax.media.opengl.GL2;


import static javax.media.opengl.GL2.GL_BLEND;

public class JetPlane extends GraphicObject {

  private float rotationAngle = 270f;

  public JetPlane(GL2 gl) {
    super(gl);
  }

  @Override
  public void draw() {

    gl.glDisable(GL_BLEND);

    gl.glPushMatrix();

    gl.glTranslatef(-3.0f, -2.0f, -2.5f);
    gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
    commonDraw();

    gl.glPopMatrix();

    // rotationAngle += 0.5f;
  }
}
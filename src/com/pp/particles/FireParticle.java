
package com.pp.particles;

import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;

import javax.media.opengl.GL2;

public class FireParticle extends Particle {

  private GL2 gl;

  private float texLeft;
  private float texRight;
  private float texTop;
  private float texBottom;

  public FireParticle(GL2 gl, Trio location, Trio speed, Trio acceleration) {

    super(location, speed, acceleration);

    this.gl = gl;
  }

  public void update() {
    speed.add(acceleration);
    location.add(speed);
    lifespan -= 0.1f;
  }

  @Override
  public void draw() {

    gl.glColor4f(1.0f, 0.5f, 0.5f, lifespan);

    gl.glBegin(GL_TRIANGLE_STRIP);

    drawVertex(texLeft, texTop, location.getX() - 0.025f, location.getY() + 0.025f, location.getZ());

    drawVertex(texRight, texTop, location.getX() + 0.025f, location.getY() + 0.025f, location.getZ());

    drawVertex(texLeft, texBottom, location.getX() - 0.025f, location.getY() - 0.025f, location.getZ());

    drawVertex(texRight, texBottom, location.getX() + 0.025f, location.getY() - 0.025f, location.getZ());

    gl.glEnd();
  }

  private void drawVertex(float texCoordU, float texCoordV, float x, float y, float z) {

    gl.glTexCoord2d(texCoordU, texCoordV);
    gl.glVertex3f(x, y, z);
  }

  public void setTexLeft(float texLeft) {
    this.texLeft = texLeft;
  }

  public void setTexRight(float texRight) {
    this.texRight = texRight;
  }

  public void setTexTop(float texTop) {
    this.texTop = texTop;
  }

  public void setTexBottom(float texBottom) {
    this.texBottom = texBottom;
  }
}
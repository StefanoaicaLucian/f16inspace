
package ro.space.display.particles;

import static javax.media.opengl.GL.GL_TRIANGLES;

import java.util.Random;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class FireParticle extends Particle {

  private GL2 gl;

  private Texture texture;

  private float left;
  private float right;
  private float top;
  private float bottom;

  private float radius = 0.5f;

  private float fadeUnit;

  public FireParticle(GL2 gl, Trio location, Trio speed, Trio acceleration, Texture texture) {
    super(location, speed, acceleration);
    this.gl = gl;
    this.texture = texture;

    TextureCoords textureCoords = this.texture.getImageTexCoords();

    top = textureCoords.top();
    bottom = textureCoords.bottom();
    left = textureCoords.left();
    right = textureCoords.right();
    
    fadeUnit = new Random().nextInt(100) / 1000.0f + 0.003f;
  }

  public void update() {
    speed.add(acceleration);
    location.add(speed);
    lifespan -= fadeUnit;
  }

  @Override
  public void draw() {

    // radius

    gl.glColor4f(1.0f, 0.0f, 0.0f, lifespan);

    gl.glBegin(GL_TRIANGLES);
    // front
    drawVertex(right, bottom, location.getX() + radius, location.getY() - radius, location.getZ());
    drawVertex(right, top, location.getX() + radius, location.getY() + radius, location.getZ());
    drawVertex(left, bottom, location.getX() - radius, location.getY() - radius, location.getZ());
    drawVertex(right, top, location.getX() + radius, location.getY() + radius, location.getZ());
    drawVertex(left, top, location.getX() - radius, location.getY() + radius, location.getZ());
    drawVertex(left, bottom, location.getX() - radius, location.getY() - radius, location.getZ());

    // back
    drawVertex(right, bottom, location.getX() + radius, location.getY() - radius, location.getZ());
    drawVertex(right, top, location.getX() + radius, location.getY() + radius, location.getZ());
    drawVertex(left, bottom, location.getX() - radius, location.getY() - radius, location.getZ());
    drawVertex(left, bottom, location.getX() - radius, location.getY() - radius, location.getZ());
    drawVertex(right, top, location.getX() + radius, location.getY() + radius, location.getZ());
    drawVertex(left, top, location.getX() - radius, location.getY() + radius, location.getZ());
    gl.glEnd();
  }

  private void drawVertex(float texCoordU, float texCoordV, float x, float y, float z) {
    gl.glTexCoord2d(texCoordU, texCoordV);
    gl.glVertex3f(x, y, z);
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }
}
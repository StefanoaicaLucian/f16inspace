
package ro.space.display.particles;

import static javax.media.opengl.GL.GL_TRIANGLES;

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

  public FireParticle(GL2 gl, Trio location, Trio speed, Trio acceleration, Texture texture) {
    super(location, speed, acceleration);
    this.gl = gl;
    this.texture = texture;

    TextureCoords textureCoords = this.texture.getImageTexCoords();

    top = textureCoords.top();
    bottom = textureCoords.bottom();
    left = textureCoords.left();
    right = textureCoords.right();
  }

  public void update() {
    speed.add(acceleration);
    location.add(speed);
    lifespan -= 0.01f;
  }

  @Override
  public void draw() {

    gl.glColor4f(1.0f, 0.5f, 0.5f, lifespan);

    gl.glBegin(GL_TRIANGLES);
    // front
    drawVertex(right, bottom, location.getX() + 0.05f, location.getY() - 0.05f, location.getZ());
    drawVertex(right, top, location.getX() + 0.05f, location.getY() + 0.05f, location.getZ());
    drawVertex(left, bottom, location.getX() - 0.05f, location.getY() - 0.05f, location.getZ());
    drawVertex(right, top, location.getX() + 0.05f, location.getY() + 0.05f, location.getZ());
    drawVertex(left, top, location.getX() - 0.05f, location.getY() + 0.05f, location.getZ());
    drawVertex(left, bottom, location.getX() - 0.05f, location.getY() - 0.05f, location.getZ());

    // back
    drawVertex(right, bottom, location.getX() + 0.05f, location.getY() - 0.05f, location.getZ());
    drawVertex(right, top, location.getX() + 0.05f, location.getY() + 0.05f, location.getZ());
    drawVertex(left, bottom, location.getX() - 0.05f, location.getY() - 0.05f, location.getZ());
    drawVertex(left, bottom, location.getX() - 0.05f, location.getY() - 0.05f, location.getZ());
    drawVertex(right, top, location.getX() + 0.05f, location.getY() + 0.05f, location.getZ());
    drawVertex(left, top, location.getX() - 0.05f, location.getY() + 0.05f, location.getZ());
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
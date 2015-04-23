
package ro.space.display.particles;

import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.util.Random;

import javax.media.opengl.GL2;

import ro.space.build.graphic_components.Material;
import ro.space.util.algebra.Calculator;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class FireParticle extends Particle {

  private GL2 gl;

  private Texture texture;

  private float left;
  private float right;
  private float top;
  private float bottom;

  private float radius = 0.25f;

  private float fadeUnit;

  private Random rand = new Random();

  private float[] ambient = {0.6f, 0.25f, 0.25f};
  private float[] diffuse = {0.6f, 0.25f, 0.25f};
  private float[] specular = {0.6f, 0.25f, 0.25f};
  private float[] shine = {120.078431f};

  private Material theMaterial;

  public FireParticle(GL2 gl, Trio location, Trio speed, Trio acceleration, Texture texture, Trio eye, double cameraAngle) {
    super(location, speed, acceleration, eye, cameraAngle);
    this.gl = gl;
    this.texture = texture;

    TextureCoords textureCoords = this.texture.getImageTexCoords();

    top = textureCoords.top();
    bottom = textureCoords.bottom();
    left = textureCoords.left();
    right = textureCoords.right();

    fadeUnit = rand.nextInt(100) / 1000.0f + 0.003f;

    theMaterial = new Material(ambient, diffuse, specular, shine);
  }

  @Override
  public void move() {
    speed.add(acceleration);
    location.add(speed);
    lifespan -= fadeUnit;

    cameraDistance = Calculator.computeDistance(eye, location);
  }

  @Override
  public void draw() {
    enableMaterial(theMaterial);

    gl.glColor4f(0.0f, 0.0f, 0.0f, lifespan);
    drawBillboard(location, radius);
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  private void drawBillboard(Trio position, float particleSize) {

    Trio leftBottom = new Trio(position.getX(), position.getY(), position.getZ());
    Trio rightBottom = new Trio(position.getX(), position.getY(), position.getZ());
    Trio rightTop = new Trio(position.getX(), position.getY(), position.getZ());
    Trio leftTop = new Trio(position.getX(), position.getY(), position.getZ());

    float sinSize = particleSize * (float) Math.sin(cameraAngle);
    float cosSize = particleSize * (float) Math.cos(cameraAngle);

    Trio positiveSin = new Trio(0.0f, 0.0f, sinSize);
    rightBottom.add(positiveSin);
    rightTop.add(positiveSin);
    Trio negativeSin = new Trio(0.0f, 0.0f, -1 * sinSize);
    leftBottom.add(negativeSin);
    leftTop.add(negativeSin);

    Trio positiveCos = new Trio(cosSize, 0.0f, 0.0f);
    rightBottom.add(positiveCos);
    rightTop.add(positiveCos);
    Trio negativeCos = new Trio(-1 * cosSize, 0.0f, 0.0f);
    leftBottom.add(negativeCos);
    leftTop.add(negativeCos);

    Trio negativeSize = new Trio(0.0f, -1 * particleSize, 0.0f);
    leftBottom.add(negativeSize);
    rightBottom.add(negativeSize);
    Trio positiveSize = new Trio(0.0f, particleSize, 0.0f);
    leftTop.add(positiveSize);
    rightTop.add(positiveSize);

    gl.glPushMatrix();

    gl.glBegin(GL_TRIANGLE_STRIP);

    gl.glTexCoord2d(right, bottom);
    gl.glVertex3f(rightBottom.getX(), rightBottom.getY(), rightBottom.getZ());

    gl.glTexCoord2d(right, top);
    gl.glVertex3f(rightTop.getX(), rightTop.getY(), rightTop.getZ());

    gl.glTexCoord2d(left, bottom);
    gl.glVertex3f(leftBottom.getX(), leftBottom.getY(), leftBottom.getZ());

    gl.glTexCoord2d(left, top);
    gl.glVertex3f(leftTop.getX(), leftTop.getY(), leftTop.getZ());

    gl.glEnd();

    gl.glPopMatrix();
  }

  protected void enableMaterial(Material theMaterial) {

    gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, theMaterial.getKd(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SPECULAR, theMaterial.getKs(), 0);
    gl.glMaterialfv(GL_FRONT, GL_AMBIENT, theMaterial.getKa(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SHININESS, theMaterial.getNs(), 0);
  }
}
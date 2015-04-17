
package ro.space.display.listeners;

import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_LINE_SMOOTH;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_NO_ERROR;
import static javax.media.opengl.GL.GL_ONE;
import static javax.media.opengl.GL.GL_SRC_ALPHA;
import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;
import static javax.media.opengl.GL.GL_VERSION;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.GL2ES1.GL_POINT_SMOOTH_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ro.space.display.particles.Trio;
import ro.space.read.textures.TextureReader;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class GraphicListener implements GLEventListener {

  private LightKeeper lighter;

  private GLU glu;

  private GL2 gl;

  private Scene theScene;

  // used by gluLookAt()
  private float eyeX = 0.0f;
  private float eyeY = 0.0f;
  private float eyeZ = 0.0f;

  private float targetX;
  private float targetY;
  private float targetZ;

  private float top;
  private float bottom;
  private float left;
  private float right;

  private Texture texture;

  private boolean culling = true;

  private Trio up = new Trio(0.0f, 1.0f, 0.0f);

  private double cameraAngle;

  public GraphicListener(KeyboardListener keyhandler) {

    eyeX = keyhandler.getEyeX();
    eyeY = keyhandler.getEyeY();
    eyeZ = keyhandler.getEyeZ();

    targetX = (float) keyhandler.getTargetX();
    targetY = (float) keyhandler.getEyeY();
    targetZ = (float) keyhandler.getTargetZ();
  }

  @Override
  public void init(GLAutoDrawable drawable) {

    System.out.println(drawable.getGL().glGetString(GL_VERSION));

    gl = drawable.getGL().getGL2();

    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.2f, 0.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL_DEPTH_TEST);
    gl.glDepthFunc(GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

    gl.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

    gl.glShadeModel(GL_SMOOTH);

    gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    gl.glEnable(GL_LINE_SMOOTH);

    lighter = new LightKeeper(gl);
    lighter.setupLights();

    theScene = new Scene(gl);

    texture = (new TextureReader(this.gl, "res/")).readTexture("anotherParticle2.png", ".png");
    TextureCoords textureCoords = texture.getImageTexCoords();

    top = textureCoords.top();
    bottom = textureCoords.bottom();
    left = textureCoords.left();
    right = textureCoords.right();
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    if (height == 0) {
      height = 1;
    }

    gl.glViewport(0, 0, width, height);

    gl.glMatrixMode(GL_PROJECTION);
    gl.glLoadIdentity();

    float aspect = (float) width / height;
    glu.gluPerspective(45.0, aspect, 0.1, 100.0);

    gl.glMatrixMode(GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  @Override
  public void display(GLAutoDrawable drawable) {

    if (culling) {
      gl.glEnable(GL_CULL_FACE);
      gl.glCullFace(GL_BACK);
    } else {
      gl.glDisable(GL_CULL_FACE);
    }

    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();
    glu.gluLookAt(eyeX, eyeY, eyeZ, eyeX + targetX, targetY, eyeZ + targetZ, up.getX(), up.getY(), up.getZ());

    theScene.draw();

    drawBillboard(new Trio(2.0f, 2.0f, -5.0f), 0.5f);

    catchGLError();

    gl.glFlush();
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  public void updateKeyboardInputs(KeyboardListener handler) {
    eyeX = handler.getEyeX();
    eyeY = handler.getEyeY();
    eyeZ = handler.getEyeZ();

    targetX = (float) handler.getTargetX();
    targetY = (float) handler.getEyeY();
    targetZ = (float) handler.getTargetZ();

    cameraAngle = handler.getAngle();
    System.out.println("cameraAngle: " + cameraAngle);

    System.out.println("leftBottom: " + leftBottom);
    System.out.println("rightBottom: " + rightBottom);
    System.out.println("rightTop: " + rightTop);
    System.out.println("leftTop: " + leftTop);

    System.out.println("===========================================");
  }

  private void catchGLError() {

    int errCode;
    String errString;

    while ((errCode = gl.glGetError()) != GL_NO_ERROR) {
      errString = glu.gluErrorString(errCode);
      System.err.println("OpenGL Error:" + errString);
    }
  }

  public void enableCulling() {
    culling = true;
  }

  public void disableCulling() {
    culling = false;
  }

  Trio leftBottom = new Trio(0.0f, 0.0f, 0.0f);
  Trio rightBottom = new Trio(0.0f, 0.0f, 0.0f);
  Trio rightTop = new Trio(0.0f, 0.0f, 0.0f);
  Trio leftTop = new Trio(0.0f, 0.0f, 0.0f);

  private void drawBillboard(Trio position, float particleSize) {

    leftBottom = new Trio(position.getX(), position.getY(), position.getZ());
    rightBottom = new Trio(position.getX(), position.getY(), position.getZ());
    rightTop = new Trio(position.getX(), position.getY(), position.getZ());
    leftTop = new Trio(position.getX(), position.getY(), position.getZ());

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

    texture.bind(gl);
    gl.glDisable(GL_BLEND);

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
}
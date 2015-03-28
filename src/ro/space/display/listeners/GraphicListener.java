
package ro.space.display.listeners;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ro.space.display.lights.LightHandler;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;

public class GraphicListener implements GLEventListener {

  private LightHandler lighter;

  private GLU glu;

  private GL2 gl;

  private Scene theScene;

  // used by gluLookAt()
  private double eyeX;
  private double eyeY;
  private double eyeZ;

  private double targetX;
  private double targetY;
  private double targetZ;

  public GraphicListener(KeyboardListener keyhandler) {

    eyeX = keyhandler.getEyeX();
    eyeY = keyhandler.getEyeY();
    eyeZ = keyhandler.getEyeZ();

    targetX = keyhandler.getTargetX();
    targetY = keyhandler.getEyeY();
    targetZ = keyhandler.getTargetZ();
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    gl = drawable.getGL().getGL2();
    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL_DEPTH_TEST);
    gl.glDepthFunc(GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

    gl.glShadeModel(GL_SMOOTH);

    gl.glEnable(GL_BLEND);
    gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    gl.glEnable(GL_LINE_SMOOTH);

    lighter = new LightHandler(gl);
    lighter.setupLights();

    gl.glEnable(GL_LIGHTING);
    gl.glEnable(GL_LIGHT0);

    theScene = new Scene(gl);
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
    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();
    glu.gluLookAt(eyeX, eyeY, eyeZ, eyeX + targetX, targetY, eyeZ + targetZ, 0, 1, 0);

    theScene.draw();

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

    targetX = handler.getTargetX();
    targetY = handler.getEyeY();
    targetZ = handler.getTargetZ();
  }

  private void catchGLError() {

    int errCode;
    String errString;

    while ((errCode = gl.glGetError()) != GL_NO_ERROR) {
      errString = glu.gluErrorString(errCode);
      System.err.println("OpenGL Error:" + errString);
    }
  }
}
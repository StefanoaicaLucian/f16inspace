
package ro.space.display.listeners;

import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_FUNC_ADD;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_LINE_SMOOTH;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_NO_ERROR;
import static javax.media.opengl.GL.GL_ONE;
import static javax.media.opengl.GL.GL_VERSION;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.GL2ES1.GL_POINT_SMOOTH_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ro.space.build.builders.GraphicObjectBuilder;
import ro.space.build.graphic_objects.GraphicObject;
import ro.space.display.particles.CylindricalFireSystem;
import ro.space.display.particles.ParticleSystem;
import ro.space.display.particles.SprayedFireSystem;
import ro.space.display.particles.Trio;
import ro.uvt.observer.Observer;
import ro.uvt.observer.Subject;

public class GraphicListener implements GLEventListener, Observer {

  private LightKeeper lighter;

  private GLU glu;

  private GL2 gl;

  private Trio up = new Trio(0.0f, 1.0f, 0.0f);

  private Trio cameraPosition;

  private double cameraAngle;

  private Trio target;

  private List<GraphicObject> objects = new ArrayList<>();

  private ParticleSystem fireSystem;
  private ParticleSystem secondSystem;

  private KeyboardListener keyHandler;

  public GraphicListener(KeyboardListener keyHandler) {
    cameraPosition = (Trio) keyHandler.getState().get("camera_position");
    target = keyHandler.getTarget();

    cameraAngle = (Double) keyHandler.getState().get("camera_angle");

    keyHandler.registerObserver(this);

    this.keyHandler = keyHandler;
  }

  @Override
  public void init(GLAutoDrawable drawable) {

    System.out.println("OpenGL version: " + drawable.getGL().glGetString(GL_VERSION));

    gl = drawable.getGL().getGL2();

    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.2f, 0.0f);

    gl.glEnable(GL_CULL_FACE);
    gl.glCullFace(GL_BACK);

    gl.glClearDepth(1.0f);
    gl.glEnable(GL_DEPTH_TEST);
    gl.glDepthFunc(GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

    gl.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

    gl.glShadeModel(GL_SMOOTH);

    gl.glBlendEquation(GL_FUNC_ADD);
    // gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    gl.glBlendFunc(GL_ONE, GL_ONE);

    gl.glEnable(GL_LINE_SMOOTH);

    lighter = new LightKeeper(gl);
    lighter.lightTheWay();

    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objects.add(objectBuilder.buildJetPlane());
    objects.add(objectBuilder.buildFloor());

    fireSystem = new SprayedFireSystem(gl, cameraPosition, cameraAngle, new Trio(2.5f, 1.7f, -6.8f), new Trio(10.0f, 1.7f, -6.8f), 2.7f);
    secondSystem = new CylindricalFireSystem(gl, cameraPosition, cameraAngle, new Trio(2.5f, 1.7f, -2.0f), new Trio(5.0f, 1.7f, -2.0f), 0.5f);

    keyHandler.registerObserver(fireSystem);
    keyHandler.registerObserver(secondSystem);
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
    glu.gluLookAt(cameraPosition.getX(),
                  cameraPosition.getY(),
                  cameraPosition.getZ(),
                  cameraPosition.getX() + target.getX(),
                  target.getY(),
                  cameraPosition.getZ() + target.getZ(),
                  up.getX(),
                  up.getY(),
                  up.getZ());

    for (GraphicObject obj : objects) {
      obj.draw();
    }

    fireSystem.draw();
    secondSystem.draw();

    catchGLError();

    gl.glFlush();
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  @Override
  public void update(Subject toObserve) {
    HashMap<String, Object> subjectState = toObserve.getState();

    cameraPosition = (Trio) subjectState.get("camera_position");

    target = ((KeyboardListener) toObserve).getTarget();
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
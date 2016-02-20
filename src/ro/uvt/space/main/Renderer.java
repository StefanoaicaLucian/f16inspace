
package ro.uvt.space.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import ro.uvt.api.systems.CylindricalSystem;
import ro.uvt.api.systems.FountainSystem;
import ro.uvt.api.systems.ParticleSystem;
import ro.uvt.api.systems.ReversedSystem;
import ro.uvt.api.systems.SprayedSystem;
import ro.uvt.api.util.MaterialProperties;
import ro.uvt.api.util.Observer;
import ro.uvt.api.util.Subject;
import ro.uvt.api.util.Vertex;
import ro.uvt.gol.GOL;
import ro.uvt.gol.GraphicObject;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;

import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_BLEND;
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
import static javax.media.opengl.GL.GL_SRC_ALPHA;
import static javax.media.opengl.GL.GL_VERSION;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.GL2ES1.GL_POINT_SMOOTH_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class Renderer extends WindowAdapter implements GLEventListener, Observer {

  private GLU glu;
  private GL2 gl;
  private Vertex cameraPosition;
  private Vertex targetPosition;
  private ParticleSystem fireSystem;
  private KeyboardListener keyListener = new KeyboardListener();
  private MaterialProperties particleSystemMaterial;
  private Texture particleSystemTexture;
  private FPSAnimator animator;

  private GOL gol;
  private List<GraphicObject> jetPlane = new ArrayList<GraphicObject>();
  private GraphicObject floor;

  public Renderer(FPSAnimator animator) {
    this.animator = animator;
    cameraPosition = (Vertex) keyListener.getState().get("camera_position");
    targetPosition = (Vertex) keyListener.getState().get("target_position");
    keyListener.registerObserver(this);
  }

  @Override
  public void init(GLAutoDrawable drawable) {
    System.out.println("OpenGL version: " + drawable.getGL().glGetString(GL_VERSION));

    gl = drawable.getGL().getGL2();
    glu = new GLU();
    gol = new GOL(gl, "objFiles/", "mtlFiles/", "textureFiles/");

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    gl.glEnable(GL_CULL_FACE);
    gl.glCullFace(GL_BACK);

    gl.glClearDepth(1.0f);
    gl.glEnable(GL_DEPTH_TEST);
    gl.glDepthFunc(GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

    gl.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

    gl.glShadeModel(GL_SMOOTH);

    gl.glBlendEquation(GL_FUNC_ADD); // it is GL_FUNC_ADD by default...
    // to make it back the way it was set to GL_ONE

    gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE);

    gl.glEnable(GL_LINE_SMOOTH);

    lightTheWay();

    jetPlane.add(gol.golLoad("bodyMat.obj", "camo.png"));
    jetPlane.add(gol.golLoad("bombsMat.obj", "metal.png"));
    jetPlane.add(gol.golLoad("glassMat.obj", "glass.png"));

    floor = gol.golLoad("floor.obj", "floor.png");

    // no need to use a new texture for each particle... just use the same for all... or maybe that will be an interesting effect.
    particleSystemTexture = gol.readTexture("particle.png");

    float[] ambient = {0.3f, 0.1f, 0.5f, 1.0f};
    float[] diffuse = {0.3f, 0.1f, 0.5f, 1.0f};
    float[] specular = {0.3f, 0.1f, 0.5f, 1.0f};
    float[] shine = {100.0f};

    particleSystemMaterial = new MaterialProperties(ambient, diffuse, specular, shine);

    keyListener.registerRenderer(this);

    Vertex[] positions = {new Vertex(2.5f, 1.7f, -4.4f), new Vertex(10.0f, 1.7f, -4.4f), cameraPosition};

    fireSystem = new SprayedSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
    initParticleSystem(fireSystem);
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

    glu.gluLookAt(cameraPosition.getPositionX(),
                  cameraPosition.getPositionY(),
                  cameraPosition.getPositionZ(),
                  cameraPosition.getPositionX() + targetPosition.getPositionX(),
                  targetPosition.getPositionY(),
                  cameraPosition.getPositionZ() + targetPosition.getPositionZ(),
                  0.0f,
                  1.0f,
                  0.0f);

    gl.glDisable(GL_BLEND);

    gl.glPushMatrix();
    gl.glTranslatef(-3.0f, -2.0f, -2.5f);
    gl.glRotatef(270f, 0.0f, 1.0f, 0.0f);
    for (GraphicObject obj : jetPlane) {
      gol.golDraw(obj);
    }
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotated(0.5f, 0.0f, 1.0f, 0.0f);
    gl.glTranslatef(0.0f, -1.0f, 0.0f);
    gol.golDraw(floor);
    gl.glPopMatrix();

    fireSystem.draw();

    // drawAxes();

    // gl.glFlush();

    queryForErrors();
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  @Override
  public void update(Subject toObserve) {
    Map<String, Object> subjectState = toObserve.getState();
    cameraPosition = (Vertex) subjectState.get("camera_position");
    targetPosition = (Vertex) subjectState.get("target_position");
  }

  public KeyboardListener getKeyListener() {
    return keyListener;
  }

  public void changeParticleSystem(int particleSystemID) {
    Vertex[] positions = new Vertex[3];
    switch (particleSystemID) {
      case 1:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Vertex(2.5f, 1.7f, -4.4f);
        positions[1] = new Vertex(10.0f, 1.7f, -4.4f);
        positions[2] = cameraPosition;
        fireSystem = new SprayedSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
        initParticleSystem(fireSystem);
        break;

      case 2:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Vertex(2.5f, 1.7f, 0.0f);
        positions[1] = new Vertex(10.0f, 1.7f, 0.0f);
        positions[2] = cameraPosition;
        fireSystem = new CylindricalSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 1f);
        initParticleSystem(fireSystem);
        break;

      case 3:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Vertex(2.0f, 1.0f, 0.0f);
        positions[1] = new Vertex(2.0f, 27.0f, 0.0f);
        positions[2] = cameraPosition;
        fireSystem = new FountainSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 5.0f);
        ((FountainSystem) fireSystem).setGravityVector(new Vertex(0.0f, -0.0024f, 0.0f));
        initParticleSystem(fireSystem);
        // the fountain particles will start off a lot smaller than the others
        break;

      case 4:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Vertex(2.5f, 1.7f, 0.0f);
        positions[1] = new Vertex(10.0f, 1.7f, 0.0f);
        positions[2] = cameraPosition;
        fireSystem = new ReversedSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
        initParticleSystem(fireSystem);
        break;
    }
  }

  public void lightTheWay() {
    float firstLightPosition[] = {20f, 20f, 20f, 1f};
    float secondLightPosition[] = {20f, 20f, -20f, 1f};

    float lightDiffuse[] = {1.0f, 1.0f, 1.0f, 0.6f};
    float lightSpecular[] = {1.0f, 1.0f, 1.0f, 1.6f};
    float lightAmbient[] = {0.6f, 0.6f, 0.6f, 0.6f};

    gl.glLightfv(GL_LIGHT0, GL_POSITION, firstLightPosition, 0);
    gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse, 0);
    gl.glLightfv(GL_LIGHT0, GL_SPECULAR, lightSpecular, 0);
    gl.glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient, 0);

    gl.glLightfv(GL_LIGHT1, GL_POSITION, secondLightPosition, 0);
    gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0);
    gl.glLightfv(GL_LIGHT1, GL_SPECULAR, lightSpecular, 0);
    gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0);

    gl.glEnable(GL_LIGHTING);
    gl.glEnable(GL_LIGHT0);
    // gl.glEnable(GL_LIGHT1);
  }

  private void queryForErrors() {
    int errorCode;
    String errorString = null;
    while ((errorCode = gl.glGetError()) != GL_NO_ERROR) {
      errorString = glu.gluErrorString(errorCode);
      System.out.println("OpenGL Error: " + errorString);
    }
  }

  //  private void drawAxes() {
  //    gl.glDisable(GL.GL_BLEND);
  //
  //    gl.glPushMatrix();
  //
  //    gl.glBegin(GL.GL_LINES);
  //
  //    gl.glVertex3f(-20f, 0.0f, 0.0f);
  //    gl.glVertex3f(20.0f, 0.0f, 0.0f);
  //
  //    gl.glVertex3f(0.0f, -20.0f, 0.0f);
  //    gl.glVertex3f(0.0f, 20.0f, 0.0f);
  //
  //    gl.glVertex3f(0.0f, 0.0f, -20.0f);
  //    gl.glVertex3f(0.0f, 0.0f, 20.0f);
  //
  //    gl.glEnd();
  //    gl.glPopMatrix();
  //  }

  private void initParticleSystem(ParticleSystem system) {
    fireSystem.setParticlesPerSpawn(10);
    fireSystem.setParticleRadius(0.2f);
    fireSystem.setFadeUnit(0.007f);
    fireSystem.setScalar(150f);

    keyListener.registerObserver(fireSystem);
  }

  @Override
  public void windowClosing(WindowEvent e) {
    Thread thread = new Thread() {

      public void run() {
        animator.stop();
        System.exit(0);
      }
    };
    thread.start();
  }
}

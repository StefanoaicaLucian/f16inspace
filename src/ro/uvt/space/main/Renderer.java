
package ro.uvt.space.main;

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
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ro.uvt.api.particles.CylindricalFireSystem;
import ro.uvt.api.particles.FountainSystem;
import ro.uvt.api.particles.Material;
import ro.uvt.api.particles.ParticleSystem;
import ro.uvt.api.particles.ReversedConeFireSystem;
import ro.uvt.api.particles.SprayedFireSystem;
import ro.uvt.api.particles.Trio;
import ro.uvt.api.util.Observer;
import ro.uvt.api.util.Subject;
import ro.uvt.space.build.GraphicObject;
import ro.uvt.space.build.GraphicObjectBuilder;
import ro.uvt.space.util.TextureReader;

import com.jogamp.opengl.util.texture.Texture;

public class Renderer implements GLEventListener, Observer {

  private LightKeeper lighter;
  private GLU glu;
  private GL2 gl;
  private Trio cameraPosition;
  private Trio targetPosition;
  private List<GraphicObject> objects = new ArrayList<>();
  private ParticleSystem fireSystem;
  private KeyboardListener keyListener = new KeyboardListener();
  private Material particleSystemMaterial;
  private Texture particleSystemTexture;

  public Renderer() {
    cameraPosition = (Trio) keyListener.getState().get("camera_position");
    targetPosition = (Trio) keyListener.getState().get("target_position");

    keyListener.registerObserver(this);
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
    gl.glBlendFunc(GL_ONE, GL_ONE);

    gl.glEnable(GL_LINE_SMOOTH);

    lighter = new LightKeeper(gl);
    lighter.lightTheWay();

    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objects.add(objectBuilder.buildJetPlane());
    objects.add(objectBuilder.buildFloor());

    particleSystemTexture = new TextureReader(gl, "res/").readTexture("particle.png", ".png");

    float[] ambient = {0.1f, 0.4f, 0.5f};
    float[] diffuse = {0.1f, 0.4f, 0.5f};
    float[] specular = {0.1f, 0.4f, 0.5f};
    float[] shine = {120.078431f};

    particleSystemMaterial = new Material(ambient, diffuse, specular, shine);

    keyListener.registerRenderer(this);

    Trio[] positions = {new Trio(2.5f, 1.7f, -6.8f), new Trio(10.0f, 1.7f, -6.8f), cameraPosition};

    fireSystem = new SprayedFireSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
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

    glu.gluLookAt(cameraPosition.getX(),
                  cameraPosition.getY(),
                  cameraPosition.getZ(),
                  cameraPosition.getX() + targetPosition.getX(),
                  targetPosition.getY(),
                  cameraPosition.getZ() + targetPosition.getZ(),
                  0.0f,
                  1.0f,
                  0.0f);

    for (GraphicObject obj : objects) {
      obj.draw();
    }

    fireSystem.draw();

    drawAxes();

    catchGLError();

    gl.glFlush();
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
  }

  @Override
  public void update(Subject toObserve) {
    Map<String, Object> subjectState = toObserve.getState();
    cameraPosition = (Trio) subjectState.get("camera_position");
    targetPosition = (Trio) subjectState.get("target_position");
  }

  private void catchGLError() {
    int errCode;
    String errString;

    while ((errCode = gl.glGetError()) != GL_NO_ERROR) {
      errString = glu.gluErrorString(errCode);
      System.err.println("OpenGL Error:" + errString);
    }
  }

  private void drawAxes() {
    gl.glDisable(GL.GL_BLEND);

    gl.glPushMatrix();

    gl.glBegin(GL.GL_LINES);

    gl.glVertex3f(-20f, 0.0f, 0.0f);
    gl.glVertex3f(20.0f, 0.0f, 0.0f);

    gl.glVertex3f(0.0f, -20.0f, 0.0f);
    gl.glVertex3f(0.0f, 20.0f, 0.0f);

    gl.glVertex3f(0.0f, 0.0f, -20.0f);
    gl.glVertex3f(0.0f, 0.0f, 20.0f);

    gl.glEnd();
    gl.glPopMatrix();
  }

  private void initParticleSystem(ParticleSystem system) {
    fireSystem.setParticlesPerSpawn(100);
    fireSystem.setParticleRadius(0.15f);
    fireSystem.setFadeUnit(0.03f);
    fireSystem.setDirectionVectorScalar(600f);

    keyListener.registerObserver(fireSystem);
  }

  public KeyboardListener getKeyListener() {
    return keyListener;
  }

  public void changeParticleSystem(int particleSystemID) {
    Trio[] positions = new Trio[3];
    switch (particleSystemID) {
      case 1:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Trio(2.5f, 1.7f, -6.8f);
        positions[1] = new Trio(10.0f, 1.7f, -6.8f);
        positions[2] = cameraPosition;
        fireSystem = new SprayedFireSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
        initParticleSystem(fireSystem);
        break;

      case 2:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Trio(2.5f, 1.7f, 0.0f);
        positions[1] = new Trio(10.0f, 1.7f, 0.0f);
        positions[2] = cameraPosition;
        fireSystem = new CylindricalFireSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 1f);
        initParticleSystem(fireSystem);
        break;

      case 3:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Trio(2.0f, 1.0f, 0.0f);
        positions[1] = new Trio(2.0f, 27.0f, 0.0);
        positions[2] = cameraPosition;
        fireSystem = new FountainSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 3.0f);
        initParticleSystem(fireSystem);
        break;

      case 4:
        keyListener.removeObserver(fireSystem);
        positions[0] = new Trio(2.5f, 1.7f, 0.0f);
        positions[1] = new Trio(10.0f, 1.7f, 0.0f);
        positions[2] = cameraPosition;
        fireSystem = new ReversedConeFireSystem(gl, positions, particleSystemTexture, particleSystemMaterial, 2.0f);
        initParticleSystem(fireSystem);
        break;
    }
  }
}

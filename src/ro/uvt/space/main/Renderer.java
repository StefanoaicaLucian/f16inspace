package ro.uvt.space.main;

import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_LINE_SMOOTH;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_NO_ERROR;
import static javax.media.opengl.GL.GL_RENDERER;
import static javax.media.opengl.GL.GL_VENDOR;
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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ro.uvt.api.util.Material;
import ro.uvt.api.util.Vertex;
import ro.uvt.gol.GOL;
import ro.uvt.gol.GraphicObject;
import ro.uvt.pel.PEL;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;

public class Renderer extends WindowAdapter implements GLEventListener,
	Observer {

    private GLU glu;
    private GL2 gl;
    private Vertex cameraPosition;
    private Vertex targetPosition;
    private KeyboardListener keyListener = new KeyboardListener();
    private Material particleSystemMaterial;
    private Texture particleSystemTexture;
    private FPSAnimator animator;
    private GOL gol;
    private PEL pel;
    private List<GraphicObject> jetPlane = new ArrayList<GraphicObject>();
    private GraphicObject floor;
    private float cameraAngle;
    private int particleSystemIndex;

    public Renderer(FPSAnimator animator) {
	this.animator = animator;
	cameraPosition = (Vertex) keyListener.getState().get("camera_position");
	targetPosition = (Vertex) keyListener.getState().get("target_position");
	keyListener.registerObserver(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
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

	// to make it back the way it was set to GL_ONE

	gl.glEnable(GL_LINE_SMOOTH);

	lightTheWay();

	jetPlane.add(gol.golLoad("bodyMat.obj", "camo.png"));
	jetPlane.add(gol.golLoad("bombsMat.obj", "metal.png"));
	jetPlane.add(gol.golLoad("glassMat.obj", "glass.png"));

	floor = gol.golLoad("floor.obj", "floor.png");

	pel = new PEL(gl);

	System.out.println("renderer: " + gl.glGetString(GL_RENDERER));
	System.out.println("vendor: " + gl.glGetString(GL_VENDOR));
	System.out.println("version: " + gl.glGetString(GL_VERSION));

	// no need to use a new texture for each particle... just use the same
	// for all... or maybe that will be an interesting effect.
	particleSystemTexture = gol.readTexture("particle.png");

	float[] ambient = { 0.3f, 0.1f, 0.5f, 1.0f };
	float[] diffuse = { 0.3f, 0.1f, 0.5f, 1.0f };
	float[] specular = { 0.3f, 0.1f, 0.5f, 1.0f };
	float[] shine = { 100.0f };

	particleSystemMaterial = new Material(ambient, diffuse, specular, shine);

	keyListener.registerRenderer(this);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
	    int height) {
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
		cameraPosition.getPositionY(), cameraPosition.getPositionZ(),
		cameraPosition.getPositionX() + targetPosition.getPositionX(),
		targetPosition.getPositionY(), cameraPosition.getPositionZ()
			+ targetPosition.getPositionZ(), 0.0f, 1.0f, 0.0f);

	gl.glPushMatrix();
	gl.glTranslatef(-3.0f, -2.0f, -2.5f);
	gl.glRotatef(270f, 0.0f, 1.0f, 0.0f);
	for (GraphicObject obj : jetPlane) {
	    gol.golDraw(obj);
	}
	gl.glPopMatrix();

	gl.glPushMatrix();
	gol.golDraw(floor);
	gl.glPopMatrix();

	drawSystem();

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
	cameraAngle = (float) subjectState.get("camera_angle");
    }

    public KeyboardListener getKeyListener() {
	return keyListener;
    }

    public void changeParticleEffect(int particleSystemID) {
	particleSystemIndex = particleSystemID;
    }

    public void lightTheWay() {
	float firstLightPosition[] = { 20f, 20f, 20f, 1f };
	float secondLightPosition[] = { 20f, 20f, -20f, 1f };

	float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 0.6f };
	float lightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.6f };
	float lightAmbient[] = { 0.6f, 0.6f, 0.6f, 0.6f };

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

    private void drawSystem() {
	Vertex[] positions = new Vertex[2];
	switch (particleSystemIndex) {
	case 1:
	    positions[0] = new Vertex(2.5f, 1.7f, -4.4f);
	    positions[1] = new Vertex(10.0f, 1.7f, -4.4f);
	    pel.pelDrawSprayedSystem(positions, particleSystemTexture,
		    particleSystemMaterial, 2.0f, 10, 0.2f, 0.007f, 150f,
		    cameraAngle);
	    break;

	case 2:
	    positions[0] = new Vertex(2.5f, 1.7f, -4.4f);
	    positions[1] = new Vertex(7.0f, 1.7f, -4.4f);
	    pel.pelDrawCylindricalSystem(positions, particleSystemTexture,
		    particleSystemMaterial, 0.2f, 10, 0.2f, 0.007f, 150f,
		    cameraAngle);
	    break;

	case 3:
	    positions[0] = new Vertex(2.5f, 1.7f, -4.4f);
	    positions[1] = new Vertex(7.0f, 1.7f, -4.4f);
	    pel.pelDrawReversedSystem(positions, particleSystemTexture,
		    particleSystemMaterial, 0.2f, 10, 0.2f, 0.007f, 150f,
		    cameraAngle);
	    break;

	case 4:
	    positions[0] = new Vertex(2.0f, 1.0f, 0.0f);
	    positions[1] = new Vertex(2.0f, 15.0f, 0.0f);
	    pel.pelDrawFountainSystem(positions, particleSystemTexture,
		    particleSystemMaterial, 5.0f, 10, 0.2f, 0.007f, 150f,
		    cameraAngle, new Vertex(0.0f, -0.0025f, 0.0f));
	    break;

	case 5:
	    positions[0] = new Vertex(5.0f, 3.0f, 0.0f);
	    positions[1] = new Vertex(0.0f, 3.0f, 0.0f);
	    pel.pelDrawLineSystem(positions, particleSystemTexture,
		    particleSystemMaterial, 5.0f, 30, 0.2f, 0.025f, 150f,
		    cameraAngle, new Vertex(0.0f, -0.0025f, 0.0f));
	    break;
	}
    }
}

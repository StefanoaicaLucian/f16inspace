package ro.uvt.space.main;

import java.awt.Frame;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class Main {

    public static void main(String[] args) {
	GLProfile profile = GLProfile.getDefault();
	GLCapabilities capabilities = new GLCapabilities(profile);
	capabilities.setHardwareAccelerated(true);
	capabilities.setDoubleBuffered(true);
	capabilities.setNumSamples(2);
	capabilities.setSampleBuffers(true);
	GLCanvas canvas = new GLCanvas(capabilities);
	FPSAnimator animator = new FPSAnimator(canvas, 100);
	Renderer renderer = new Renderer(animator);
	canvas.addGLEventListener(renderer);
	canvas.addKeyListener(renderer.getKeyListener());
	canvas.setFocusable(true);
	canvas.requestFocus();
	JFrame frame = new JFrame("F16 In Space");
	frame.getContentPane().add(canvas);
	frame.addWindowListener(renderer);
	frame.setUndecorated(true);
	frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	frame.setVisible(true);
	animator.start();
    }
}

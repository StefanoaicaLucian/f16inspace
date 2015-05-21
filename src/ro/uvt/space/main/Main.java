
package ro.uvt.space.main;

import java.awt.Dimension;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

public class Main {

  public static void main(String[] args) {
    GLProfile profile = GLProfile.getDefault();

    GLCapabilities capabilities = new GLCapabilities(profile);
    capabilities.setHardwareAccelerated(true);
    capabilities.setDoubleBuffered(true);
    capabilities.setNumSamples(2);
    capabilities.setSampleBuffers(true);

    GLCanvas canvas = new GLCanvas(capabilities);
    canvas.setPreferredSize(new Dimension(800, 600));

    Renderer renderer = new Renderer();

    canvas.addGLEventListener(renderer);
    canvas.addKeyListener(renderer.getKeyListener());
    canvas.setFocusable(true);
    canvas.requestFocus();

    Animator animator = new Animator(canvas);
    animator.start();

    JFrame frame = new JFrame("F16 In Space");
    frame.setSize(800, 600);
    frame.getContentPane().add(canvas);
    frame.addWindowListener(new WindowEventListener(animator));
    frame.setVisible(true);
  }
}
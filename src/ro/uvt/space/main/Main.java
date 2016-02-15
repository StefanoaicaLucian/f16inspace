
package ro.uvt.space.main;

import java.awt.Dimension;
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
    canvas.setPreferredSize(new Dimension(800, 600));

    Renderer renderer = new Renderer();

    canvas.addGLEventListener(renderer);
    canvas.addKeyListener(renderer.getKeyListener());
    canvas.setFocusable(true);
    canvas.requestFocus();

    FPSAnimator animator = new FPSAnimator(canvas, 60);

    JFrame frame = new JFrame("F16 In Space");
    frame.getContentPane().add(canvas);
    frame.addWindowListener(new WindowEventListener(animator));
    frame.setUndecorated(true);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);

    animator.start();
  }
}
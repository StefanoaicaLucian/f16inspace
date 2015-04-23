
package ro.space.main;

import java.awt.Dimension;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import ro.space.display.listeners.GraphicListener;
import ro.space.display.listeners.KeyboardListener;
import ro.space.util.constants.Numbers;
import ro.space.util.constants.Strings;
import ro.space.util.stop.AnimatorStopper;

import com.jogamp.opengl.util.FPSAnimator;

public class Application {

  private GLCanvas canvas = new GLCanvas();
  private JFrame frame = new JFrame();

  private KeyboardListener keyListener;
  private GraphicListener graphicListener;

  private FPSAnimator animator;

  private AnimatorStopper stopper;

  private Dimension preferredSize;

  public Application() {

    keyListener = new KeyboardListener();
    graphicListener = new GraphicListener(keyListener);

    animator = new FPSAnimator(canvas, Numbers.FPS.getValue(), true);

    stopper = new AnimatorStopper(animator);

    int width = Numbers.CANVAS_WIDTH.getValue();
    int height = Numbers.CANVAS_HEIGHT.getValue();
    preferredSize = new Dimension(width, height);
  }

  public void init() {

    canvas.setPreferredSize(preferredSize);

    canvas.addKeyListener(keyListener);

    canvas.setFocusable(true);
    canvas.requestFocus();

    canvas.addGLEventListener(graphicListener);

    frame.getContentPane().add(canvas);

    frame.addWindowListener(stopper);

    frame.setTitle(Strings.TITLE.getValue());
    frame.pack();
    frame.setVisible(true);
  }

  public void start() {
    animator.start();
  }
}

package ro.space.main;

import java.awt.Dimension;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import ro.space.constants.Numbers;
import ro.space.constants.Strings;
import ro.space.listeners.KeyboardListener;
import ro.space.listeners.GraphicListener;
import ro.space.stop.AnimatorStopper;

import com.jogamp.opengl.util.FPSAnimator;

public class Application {

  private GLCanvas canvas;
  private JFrame frame;

  private KeyboardListener keyListener;
  private GraphicListener graphicalListener;

  private FPSAnimator animator;

  private AnimatorStopper stopper;

  private Dimension preferredSize;

  public Application() {

    canvas = new GLCanvas();
    frame = new JFrame();

    keyListener = new KeyboardListener();
    graphicalListener = new GraphicListener(keyListener);

    animator = new FPSAnimator(canvas, Numbers.FPS.getValue(), true);

    stopper = new AnimatorStopper(animator);

    int width = Numbers.CANVAS_WIDTH.getValue();
    int height = Numbers.CANVAS_HEIGHT.getValue();
    preferredSize = new Dimension(width, height);
  }

  public void init() {

    canvas.setPreferredSize(preferredSize);

    keyListener.setSceneHandler(graphicalListener);

    canvas.addKeyListener(keyListener);

    canvas.setFocusable(true);
    canvas.requestFocus();

    canvas.addGLEventListener(graphicalListener);

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
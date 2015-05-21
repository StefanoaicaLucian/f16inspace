
package ro.uvt.space.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.util.Animator;

public class WindowEventListener extends WindowAdapter {

  private Animator mAnimator;

  public WindowEventListener(Animator animator) {
    mAnimator = animator;
  }

  @Override
  public void windowClosing(WindowEvent e) {
    Thread thread = new Thread() {

      public void run() {
        mAnimator.stop();
        System.exit(0);
      }
    };

    thread.start();
  }
}
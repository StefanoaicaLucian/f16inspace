
package ro.uvt.space.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.util.FPSAnimator;

public class WindowEventListener extends WindowAdapter {

  private FPSAnimator animator;

  public WindowEventListener(FPSAnimator animator) {
    this.animator = animator;
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

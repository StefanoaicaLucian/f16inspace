
package ro.space.util.stop;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.util.FPSAnimator;

public class AnimatorStopper extends WindowAdapter {

  private StopThread thread;

  public AnimatorStopper(FPSAnimator animator) {
    this.thread = new StopThread(animator);
  }

  @Override
  public void windowClosing(WindowEvent e) {
    thread.start();
  }
}
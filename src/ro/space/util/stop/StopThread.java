
package ro.space.util.stop;

import com.jogamp.opengl.util.FPSAnimator;

public class StopThread extends Thread {

  private FPSAnimator theAnimator;

  public StopThread(FPSAnimator theAnimator) {
    this.theAnimator = theAnimator;
  }

  @Override
  public void run() {
    theAnimator.stop();
    System.exit(0);
  }
}
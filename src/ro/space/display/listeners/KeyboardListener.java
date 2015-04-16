
package ro.space.display.listeners;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

  private GraphicListener sceneHandler;

  private float eyeX;
  private float eyeY;
  private float eyeZ;

  private double targetX;
  private double targetY;
  private double targetZ;

  private double angle;
  private double angleStep;
  private double fraction;

  public KeyboardListener() {
    eyeX = 0.0f;
    eyeY = 0.0f;
    eyeZ = 0.0f;

    targetX = 0.0f;
    targetY = 0.0f;
    targetZ = -1.0f;

    angleStep = 0.1f;
    fraction = 0.1f;
    
    System.out.println("angle: " + angle);
  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch (e.getKeyCode()) {

      case VK_LEFT:
        angle -= angleStep;
        targetX = Math.sin(angle);
        targetZ = -Math.cos(angle);
        notifySceneHandler();
        break;

      case VK_RIGHT:
        angle += angleStep;
        targetX = Math.sin(angle);
        targetZ = -Math.cos(angle);
        notifySceneHandler();
        break;

      case VK_UP:
        eyeX += (targetX * fraction);
        eyeZ += (targetZ * fraction);
        notifySceneHandler();
        break;

      case VK_DOWN:
        eyeX -= (targetX * fraction);
        eyeZ -= (targetZ * fraction);
        notifySceneHandler();
        break;

      case VK_A:
        sceneHandler.enableCulling();
        break;

      case VK_S:
        sceneHandler.disableCulling();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // unused
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // unused
  }

  public float getEyeX() {
    return eyeX;
  }

  public float getEyeY() {
    return eyeY;
  }

  public float getEyeZ() {
    return eyeZ;
  }

  public double getTargetX() {
    return targetX;
  }

  public double getTargetY() {
    return targetY;
  }

  public double getTargetZ() {
    return targetZ;
  }

  public void setSceneHandler(GraphicListener sceneHandler) {
    this.sceneHandler = sceneHandler;
  }

  private void notifySceneHandler() {
    System.out.println("angle: " + angle);

    sceneHandler.updateKeyboardInputs(this);
  }
}

package ro.space.display.listeners;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.space.display.particles.Trio;
import ro.uvt.observer.Observer;
import ro.uvt.observer.Subject;

public class KeyboardListener implements KeyListener, Subject {

  private Trio cameraPosition = new Trio(0.0f, 0.0f, 0.0f);

  private Trio target = new Trio(0.0f, 0.0f, -1.0f);

  private double cameraAngle;
  private double angleStep;
  private double fraction;

  private List<Observer> observers = new ArrayList<>();

  public KeyboardListener() {
    angleStep = 0.1f;
    fraction = 0.1f;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case VK_LEFT:
        cameraAngle -= angleStep;
        target.setX((float) Math.sin(cameraAngle));
        target.setZ((float) -Math.cos(cameraAngle));
        notifyObservers();
        break;

      case VK_RIGHT:
        cameraAngle += angleStep;
        target.setX((float) Math.sin(cameraAngle));
        target.setZ((float) -Math.cos(cameraAngle));
        notifyObservers();
        break;

      case VK_UP:
        cameraPosition.setX(cameraPosition.getX() + (float) (target.getX() * fraction));
        cameraPosition.setZ(cameraPosition.getZ() + (float) (target.getZ() * fraction));
        notifyObservers();
        break;

      case VK_DOWN:
        cameraPosition.setX(cameraPosition.getX() - (float) (target.getX() * fraction));
        cameraPosition.setZ(cameraPosition.getZ() - (float) (target.getZ() * fraction));
        notifyObservers();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // unused
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO test this to see the difference
  }

  @Override
  public void registerObserver(Observer toRegister) {
    observers.add(toRegister);
  }

  @Override
  public void removeObserver(Observer toRemove) {
    observers.remove(toRemove);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update(this);
    }
  }

  public Trio getTarget() {
    return target;
  }

  @Override
  public HashMap<String, Object> getState() {
    HashMap<String, Object> state = new HashMap<>();
    
    state.put("camera_position", cameraPosition);
    state.put("camera_angle", cameraAngle);
    
    return state;
  }
}
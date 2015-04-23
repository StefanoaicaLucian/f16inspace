
package ro.space.display.particles;

import static javax.media.opengl.GL.GL_BLEND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.media.opengl.GL2;

import ro.space.display.listeners.KeyboardListener;
import ro.space.display.listeners.Observer;
import ro.space.display.listeners.Subject;

public abstract class ParticleSystem implements Observer {

  protected List<Particle> particles = new ArrayList<>();

  protected GL2 gl;

  protected double cameraAngle;

  protected Trio eye;

  public ParticleSystem(Trio eye, double cameraAngle) {
    this.eye = eye;
    this.cameraAngle = cameraAngle;
  }

  public void draw() {
    spawnParticles();
    Collections.sort(particles);
    
    // printSortStatus();
    
    gl.glEnable(GL_BLEND);

    ListIterator<Particle> iterator = particles.listIterator(particles.size());

    while (iterator.hasPrevious()) {

      Particle particle = (Particle) iterator.previous();

      particle.draw();

      particle.move();

      if (particle.isDead()) {
        iterator.remove();
      }
    }
  }

  @Override
  public void update(Subject toObserve) {
    KeyboardListener subject = (KeyboardListener) toObserve;

    eye = subject.getEye();
    cameraAngle = subject.getCameraAngle();
  }

  protected abstract void spawnParticles();

  private void printSortStatus() {
    boolean sorted = true;

    Particle current = particles.get(0);

    for (int index = 1; index < particles.size(); ++index) {
      if (particles.get(index).getCameraDistance() < current.getCameraDistance()) {
        sorted = false;
        break;
      } else {
        current = particles.get(index);
      }
    }

    StringBuilder distances = new StringBuilder();

    for (Particle p : particles) {
      distances.append(p.getCameraDistance()).append(" ");
    }

    System.out.println(sorted + " : " + distances.toString());
  }
}

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
    
    gl.glEnable(GL_BLEND);
    
    /*
     * ListIterator<Integer> index = someList.listIterator(someList.size());
     * while (index.hasPrevious()) {
     * System.out.print(index.previous() + " ");
     * } 
     */
    
    ListIterator<Particle> index = particles.listIterator(particles.size());

    while (index.hasPrevious()) {

      Particle particle = (Particle) index.previous();

      particle.draw();

      particle.move();

      if (particle.died() == true) {
        index.remove();
      }
    }
  }

  @Override
  public void update(Subject toObserve) {
    KeyboardListener subject = (KeyboardListener) toObserve;

    eye = subject.getEye();
    cameraAngle = subject.getCameraAngle();

    for (Particle particle : particles) {
      particle.setCameraAngle(cameraAngle);
      particle.setCameraPosition(eye);
    }
  }

  protected abstract void spawnParticles();
}
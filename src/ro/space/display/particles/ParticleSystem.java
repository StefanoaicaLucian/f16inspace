
package ro.space.display.particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL2;

import static javax.media.opengl.GL2.GL_BLEND;

public abstract class ParticleSystem {

  protected List<Particle> particles = new ArrayList<>();

  protected GL2 gl;

  public void draw() {
    gl.glEnable(GL_BLEND);

    spawnParticles();

    Iterator<Particle> iterator = particles.iterator();

    while (iterator.hasNext()) {

      Particle particle = iterator.next();

      particle.draw();

      particle.update();

      if (particle.isDead()) {
        iterator.remove();
      }
    }
  }

  protected abstract void spawnParticles();
}
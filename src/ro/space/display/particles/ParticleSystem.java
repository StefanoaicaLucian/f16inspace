
package ro.space.display.particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ParticleSystem {

  protected List<Particle> particles = new ArrayList<>();

  public void display() {
    
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
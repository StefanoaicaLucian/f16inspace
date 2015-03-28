
package com.pp.particles;

import java.util.Random;
import javax.media.opengl.GL2;
import ro.space.util.constants.*;

public class FireSystem extends ParticleSystem {

  private TextureLoader texLoader;

  private Random generator = new Random();

  private GL2 gl;

  public FireSystem(GL2 gl) {
    this.gl = gl;
    texLoader = new TextureLoader(this.gl);
  }

  protected void spawnParticles() {

    texLoader.getTexture().bind(gl);

    for (int i = 0; i < Numbers.NUMBER_OF_PARTICLES.getValue(); ++i) {

      Trio loc = new Trio(-(generator.nextFloat() / 6), 0.0f, -5.0f);
      Trio speed = new Trio(0.0f, 1.0f / generator.nextInt(1000), 0.0f);
      Trio acceleration = new Trio(0.0f, 1.0f / generator.nextInt(1000), 0.0f);

      FireParticle particle = new FireParticle(gl, loc, speed, acceleration);

      particle.setTexLeft(texLoader.getTextureLeft());
      particle.setTexRight(texLoader.getTextureRight());
      particle.setTexTop(texLoader.getTextureTop());
      particle.setTexBottom(texLoader.getTextureBottom());

      particles.add(particle);
    }
  }
}
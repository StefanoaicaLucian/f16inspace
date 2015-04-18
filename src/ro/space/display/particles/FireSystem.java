
package ro.space.display.particles;

import java.util.Random;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import ro.space.read.textures.TextureReader;
import ro.space.util.constants.*;

public class FireSystem extends ParticleSystem {

  private TextureReader reader;

  private Random generator = new Random();

  public FireSystem(GL2 gl) {
    this.gl = gl;
    reader = new TextureReader(this.gl, "res/");
  }

  protected void spawnParticles() {

    Texture texture = reader.readTexture("anotherParticle.png", ".png");

    for (int i = 0; i < Numbers.NUMBER_OF_PARTICLES.getValue(); ++i) {

      Trio loc = new Trio(-(generator.nextFloat() / 6), 0.0f, -5.0f);
      Trio speed = new Trio(0.0f, 1.0f / generator.nextInt(1000), 0.0f);
      Trio acceleration = new Trio(0.0f, 1.0f / generator.nextInt(1000), 0.0f);

      FireParticle particle = new FireParticle(gl, loc, speed, acceleration, texture, angle);

      particles.add(particle);
    }
  }
}
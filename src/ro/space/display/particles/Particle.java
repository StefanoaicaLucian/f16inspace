
package ro.space.display.particles;


public abstract class Particle {

  protected Trio location;
  protected Trio speed;
  protected Trio acceleration;

  protected float lifespan;

  protected double angle;

  protected Particle(Trio location, Trio speed, Trio acceleration) {
    this.location = location;
    this.speed = speed;
    this.acceleration = acceleration;

    lifespan = 1.0f;
  }

  public boolean isDead() {
    if (lifespan < 0) {
      return true;
    } else {
      return false;
    }
  }

  public abstract void update();

  public abstract void draw();

  public void setAngle(double angle) {
    this.angle = angle;
  }
}
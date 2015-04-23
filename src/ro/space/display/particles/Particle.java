
package ro.space.display.particles;

import ro.space.util.algebra.Calculator;

public abstract class Particle implements Comparable<Particle> {

  protected double cameraDistance;

  protected Trio location;
  protected Trio speed;
  protected Trio acceleration;

  protected float lifespan;

  protected double cameraAngle;

  protected Trio eye;

  protected Particle(Trio location, Trio speed, Trio acceleration, Trio eye, double cameraAngle) {
    this.location = location;
    this.speed = speed;
    this.acceleration = acceleration;

    this.eye = eye;
    this.cameraAngle = cameraAngle;

    cameraDistance = Calculator.computeDistance(eye, location);

    lifespan = 1.0f;
  }

  public boolean isDead() {
    if (lifespan < 0) {
      return true;
    } else {
      return false;
    }
  }

  public abstract void move();

  public abstract void draw();

  @Override
  public int compareTo(Particle that) {
    int result = 0;

    if (this.cameraDistance < that.cameraDistance) {
      result = -1;
    } else if (this.cameraDistance > that.cameraDistance) {
      result = 1;
    }

    return result;
  }

  public double getCameraDistance() {
    return cameraDistance;
  }
}
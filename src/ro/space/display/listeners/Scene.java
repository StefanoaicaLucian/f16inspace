
package ro.space.display.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import ro.space.build.builders.GraphicObjectBuilder;
import ro.space.build.graphic_objects.GraphicObject;
import ro.space.display.particles.FireSystem;
import ro.space.display.particles.ParticleSystem;

public class Scene {

  private List<GraphicObject> objects = new ArrayList<>();

  private ParticleSystem fireSystem;

  public Scene(GL2 gl) {
    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objects.add(objectBuilder.buildJetPlane());
    objects.add(objectBuilder.buildFloor());

    fireSystem = new FireSystem(gl);
  }

  public void draw() {
    for (GraphicObject obj : objects) {
      obj.draw();
    }

    fireSystem.draw();
  }
}

package ro.space.display.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import ro.space.build.builders.GraphicObjectBuilder;
import ro.space.build.graphic_objects.GraphicObject;
import ro.space.display.particles.FireSystem;
import ro.space.display.particles.ParticleSystem;

import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL.GL_BLEND;

public class Scene {

  private List<GraphicObject> objects = new ArrayList<>();

  private ParticleSystem fireSystem;

  private GL2 gl;

  public Scene(GL2 gl) {
    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objects.add(objectBuilder.buildJetPlane());
    objects.add(objectBuilder.buildFloor());

    fireSystem = new FireSystem(gl);

    this.gl = gl;
  }

  public void draw() {
    for (GraphicObject obj : objects) {
      obj.draw();
    }

    fireSystem.draw();

    gl.glDisable(GL_BLEND);
    gl.glBegin(GL_TRIANGLES);
    gl.glVertex3f(0.0f, 0.0f, 0.0f);
    gl.glVertex3f(2.0f, 0.0f, 0.0f);
    gl.glVertex3f(0.0f, 2.0f, 0.0f);
    gl.glEnd();
  }
}
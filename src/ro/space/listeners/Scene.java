
package ro.space.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import ro.space.builders.GraphicObjectBuilder;
import ro.space.display.GraphicObject;

public class Scene {

  private List<GraphicObject> objectsToDraw = new ArrayList<>();

  private GraphicObjectBuilder objectBuilder;

  public Scene(GL2 gl) {
    objectBuilder = new GraphicObjectBuilder(gl);

    objectsToDraw.add(objectBuilder.buildFloor());
    objectsToDraw.add(objectBuilder.buildJetPlane());
  }

  public void draw() {
    for (GraphicObject obj : objectsToDraw) {
      obj.draw();
    }
  }
}

package ro.space.display.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import ro.space.display.graphic_objects.GraphicObject;
import ro.space.load.builders.GraphicObjectBuilder;

public class Scene {

  private List<GraphicObject> objectsToDraw = new ArrayList<>();

  public Scene(GL2 gl) {
    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objectsToDraw.add(objectBuilder.buildFloor());
    objectsToDraw.add(objectBuilder.buildJetPlane());
  }

  public void draw() {
    for (GraphicObject obj : objectsToDraw) {
      obj.draw();
    }
  }
}
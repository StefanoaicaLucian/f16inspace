
package ro.space.display.graphic_objects;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import ro.space.load.builders.GraphicObjectBuilder;

public class Scene {

  private List<GraphicObject> objects = new ArrayList<>();

  public Scene(GL2 gl) {
    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);

    objects.add(objectBuilder.buildJetPlane());
    objects.add(objectBuilder.buildFloor());
  }

  public void draw() {
    for (GraphicObject obj : objects) {
      obj.display();
    }
  }
}
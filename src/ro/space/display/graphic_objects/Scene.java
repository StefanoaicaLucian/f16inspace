
package ro.space.display.graphic_objects;

import javax.media.opengl.GL2;

import ro.space.load.builders.GraphicObjectBuilder;

public class Scene {

  private JetPlane thePlane;
  private GraphicObject theFloor;
  
  public Scene(GL2 gl) {
    GraphicObjectBuilder objectBuilder = new GraphicObjectBuilder(gl);
    
    thePlane = objectBuilder.buildJetPlane();
    theFloor = objectBuilder.buildFloor();
  }

  public void draw() {
    thePlane.display();
    theFloor.draw();
  }
}
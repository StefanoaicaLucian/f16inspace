
package ro.space.builders;

import javax.media.opengl.GL2;

import ro.space.display.GraphicComponent;
import ro.space.display.GraphicObject;

public class GraphicObjectBuilder {

  private GL2 gl;

  private GraphicComponentBuilder componentBuilder;

  public GraphicObjectBuilder(GL2 gl) {
    this.gl = gl;
    
    componentBuilder = new GraphicComponentBuilder(gl);
    
  }

  public GraphicObject buildJetPlane() {

    GraphicObject jetPlane = new GraphicObject(gl);

    jetPlane.addComponent(componentBuilder.buildComponent("bodyMat.obj", "camo.png"));
    jetPlane.addComponent(componentBuilder.buildComponent("bombsMat.obj", "metal.png"));
    jetPlane.addComponent(componentBuilder.buildComponent("glassMat.obj", "glass.png"));

    return jetPlane;
  }

  public GraphicObject buildFloor() {
    GraphicObject floor = new GraphicObject(gl);

    GraphicComponent component = componentBuilder.buildComponent("floor.obj", "floor.png");
    
    floor.addComponent(component);

    return floor;
  }
}
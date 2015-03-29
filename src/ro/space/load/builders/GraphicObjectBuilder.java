
package ro.space.load.builders;

import javax.media.opengl.GL2;

import ro.space.display.graphic_objects.Floor;
import ro.space.display.graphic_objects.GraphicComponent;
import ro.space.display.graphic_objects.JetPlane;

public class GraphicObjectBuilder {

  private GL2 gl;

  private GraphicComponentBuilder componentBuilder;

  public GraphicObjectBuilder(GL2 gl) {
    this.gl = gl;

    componentBuilder = new GraphicComponentBuilder(gl);
  }

  public JetPlane buildJetPlane() {

    JetPlane jetPlane = new JetPlane(gl);

    jetPlane.addComponent(componentBuilder.buildComponent("bodyMat.obj", "camo.png"));
    jetPlane.addComponent(componentBuilder.buildComponent("bombsMat.obj", "metal.png"));
    jetPlane.addComponent(componentBuilder.buildComponent("glassMat.obj", "glass.png"));

    return jetPlane;
  }

  public Floor buildFloor() {
    Floor floor = new Floor(gl);

    GraphicComponent component = componentBuilder.buildComponent("floor.obj", "floor.png");

    floor.addComponent(component);

    return floor;
  }
}
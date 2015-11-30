
package ro.uvt.space.graphic_objects;

import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL.GL_UNSIGNED_INT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import ro.uvt.api.util.MaterialProperties;
import ro.uvt.space.build.GraphicComponent;

public abstract class GraphicObject {

  protected GL2 gl;

  private ArrayList<GraphicComponent> components = new ArrayList<>();

  public GraphicObject(GL2 gl) {
    this.gl = gl;
  }

  public void addComponent(GraphicComponent component) {
    components.add(component);
  }

  public abstract void draw();

  protected void enableMaterial(MaterialProperties theMaterial) {

    gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, theMaterial.getDiffuse(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SPECULAR, theMaterial.getSpecular(), 0);
    gl.glMaterialfv(GL_FRONT, GL_AMBIENT, theMaterial.getAmbient(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SHININESS, theMaterial.getShine(), 0);
  }

  protected void commonDraw() {
    for (GraphicComponent component : components) {
      enableMaterial(component.getMaterial());
      component.getTexture().bind(gl);
      gl.glBindVertexArray(component.getVertexArrayObjectId());
      gl.glDrawElements(GL_TRIANGLES, component.getTotalElements(), GL_UNSIGNED_INT, 0);
    }
  }
}
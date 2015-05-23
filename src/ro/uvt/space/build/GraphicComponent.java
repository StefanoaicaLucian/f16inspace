
package ro.uvt.space.build;


import ro.uvt.api.particles.Material;

import com.jogamp.opengl.util.texture.Texture;


public class GraphicComponent {

  private int vertexArrayObjectId;
  private Material material;
  private Texture texture;
  private int totalElements;

  public GraphicComponent(int vertexArrayObjectId, Material material, Texture texture, int totalElements) {
    this.vertexArrayObjectId = vertexArrayObjectId;
    this.material = material;
    this.texture = texture;
    this.totalElements = totalElements;
  }

  public int getVertexArrayObjectId() {
    return vertexArrayObjectId;
  }

  public Material getMaterial() {
    return material;
  }

  public Texture getTexture() {
    return texture;
  }

  public int getTotalElements() {
    return totalElements;
  }
}
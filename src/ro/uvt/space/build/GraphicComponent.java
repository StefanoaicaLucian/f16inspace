
package ro.uvt.space.build;

import ro.uvt.api.util.MaterialProperties;

import com.jogamp.opengl.util.texture.Texture;

public class GraphicComponent {

  private int vertexArrayObjectId;
  private MaterialProperties material;
  private Texture texture;
  private int totalElements;

  public GraphicComponent(int vertexArrayObjectId, MaterialProperties material, Texture texture, int totalElements) {
    this.vertexArrayObjectId = vertexArrayObjectId;
    this.material = material;
    this.texture = texture;
    this.totalElements = totalElements;
  }

  public int getVertexArrayObjectId() {
    return vertexArrayObjectId;
  }

  public void setVertexArrayObjectId(int vertexArrayObjectId) {
    this.vertexArrayObjectId = vertexArrayObjectId;
  }

  public MaterialProperties getMaterial() {
    return material;
  }

  public void setMaterial(MaterialProperties material) {
    this.material = material;
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public int getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(int totalElements) {
    this.totalElements = totalElements;
  }
}
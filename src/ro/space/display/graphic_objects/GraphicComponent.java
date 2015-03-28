
package ro.space.display.graphic_objects;

import com.jogamp.opengl.util.texture.Texture;

import ro.space.load.contents.MtlContent;

public class GraphicComponent {

  private int vertexArrayObjectId;
  private MtlContent material;
  private Texture texture;
  private int totalElements;

  public GraphicComponent(int vertexArrayObjectId, MtlContent material, Texture texture, int totalElements) {
    this.vertexArrayObjectId = vertexArrayObjectId;
    this.material = material;
    this.texture = texture;
    this.totalElements = totalElements;
  }

  public int getVertexArrayObjectId() {
    return vertexArrayObjectId;
  }

  public MtlContent getMaterial() {
    return material;
  }

  public Texture getTexture() {
    return texture;
  }

  public int getTotalElements() {
    return totalElements;
  }
}
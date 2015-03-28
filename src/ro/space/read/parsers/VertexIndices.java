
package ro.space.read.parsers;

public class VertexIndices {

  private int positionIndex;
  private int textureIndex;
  private int normalIndex;

  public VertexIndices(int positionIndex, int textureIndex, int normalIndex) {
    this.positionIndex = positionIndex;
    this.textureIndex = textureIndex;
    this.normalIndex = normalIndex;
  }

  public int getPositionIndex() {
    return positionIndex;
  }

  public int getTextureIndex() {
    return textureIndex;
  }

  public int getNormalIndex() {
    return normalIndex;
  }
}
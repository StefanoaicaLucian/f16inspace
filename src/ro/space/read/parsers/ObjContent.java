
package ro.space.read.parsers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;

public class ObjContent {

  private int vaoID;

  private int vertexBufferID;
  private int textureBufferID;
  private int normalBufferID;
  private int elementBufferID;

  private FloatBuffer vertexBufferData;
  private FloatBuffer textureBufferData;
  private FloatBuffer normalBufferData;
  private IntBuffer elementBufferData;

  private String mtlFileName;

  public int getVaoID() {
    return vaoID;
  }

  public int getVertexBufferID() {
    return vertexBufferID;
  }

  public int getTextureBufferID() {
    return textureBufferID;
  }

  public int getNormalBufferID() {
    return normalBufferID;
  }

  public int getElementBufferID() {
    return elementBufferID;
  }

  public FloatBuffer getVertexBufferData() {
    return vertexBufferData;
  }

  public void setVertexBufferData(float[] vertices) {
    this.vertexBufferData = Buffers.newDirectFloatBuffer(vertices);
  }

  public FloatBuffer getTextureBufferData() {
    return textureBufferData;
  }

  public void setTextureBufferData(float[] textureCoords) {
    this.textureBufferData = Buffers.newDirectFloatBuffer(textureCoords);
  }

  public FloatBuffer getNormalBufferData() {
    return normalBufferData;
  }

  public void setNormalBufferData(float[] normals) {
    this.normalBufferData = Buffers.newDirectFloatBuffer(normals);
  }

  public IntBuffer getElementBufferData() {
    return elementBufferData;
  }

  public void setElementBufferData(int[] drawOrder) {
    this.elementBufferData = Buffers.newDirectIntBuffer(drawOrder);
  }

  public int getVertexBufferSize() {
    return vertexBufferData.capacity() * Buffers.SIZEOF_FLOAT;
  }

  public int getTextureBufferSize() {
    return textureBufferData.capacity() * Buffers.SIZEOF_FLOAT;
  }

  public int getTotalElements() {
    return elementBufferData.capacity();
  }

  public int getNormalBufferSize() {
    return normalBufferData.capacity() * Buffers.SIZEOF_FLOAT;
  }

  public int getElementBufferSize() {
    return elementBufferData.capacity() * Buffers.SIZEOF_INT;
  }

  public void setVaoID(int vaoID) {
    this.vaoID = vaoID;
  }

  public void setVertexBufferID(int vertexBufferID) {
    this.vertexBufferID = vertexBufferID;
  }

  public void setTextureBufferID(int textureBufferID) {
    this.textureBufferID = textureBufferID;
  }

  public void setNormalBufferID(int normalBufferID) {
    this.normalBufferID = normalBufferID;
  }

  public void setElementBufferID(int elementBufferID) {
    this.elementBufferID = elementBufferID;
  }

  public String getMtlFileName() {
    return mtlFileName;
  }

  public void setMtlFileName(String mtlFileName) {
    this.mtlFileName = mtlFileName;
  }
}
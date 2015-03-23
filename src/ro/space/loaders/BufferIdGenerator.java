
package ro.space.loaders;

import java.nio.IntBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

import ro.space.contents.ObjContent;

public class BufferIdGenerator {

  private GL2 gl;

  private IntBuffer vaoIdBuffer;
  private IntBuffer vboIdBuffer;

  public BufferIdGenerator(GL2 gl) {
    this.gl = gl;
  }

  public void injectIds(ObjContent target) {
    vaoIdBuffer = Buffers.newDirectIntBuffer(1);
    gl.glGenVertexArrays(1, vaoIdBuffer);

    vboIdBuffer = Buffers.newDirectIntBuffer(4);
    gl.glGenBuffers(4, vboIdBuffer);

    target.setVaoID(vaoIdBuffer.get(0));

    target.setVertexBufferID(vboIdBuffer.get(0));
    target.setTextureBufferID(vboIdBuffer.get(1));
    target.setNormalBufferID(vboIdBuffer.get(2));
    target.setElementBufferID(vboIdBuffer.get(3));
  }
}
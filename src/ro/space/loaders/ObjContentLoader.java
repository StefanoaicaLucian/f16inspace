
package ro.space.loaders;

import static javax.media.opengl.GL.GL_ARRAY_BUFFER;
import static javax.media.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.GL.GL_STATIC_DRAW;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_NORMAL_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import javax.media.opengl.GL2;

import ro.space.contents.ObjContent;


public class ObjContentLoader {

  private GL2 gl;

  public ObjContentLoader(GL2 gl) {
    this.gl = gl;
  }

  public void loadInVideoCard(ObjContent data) {

    gl.glBindVertexArray(data.getVaoID());

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getVertexBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.getVertexBufferSize(), data.getVertexBufferData(), GL_STATIC_DRAW);
    gl.glVertexPointer(3, GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_VERTEX_ARRAY);

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getTextureBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.getTextureBufferSize(), data.getTextureBufferData(), GL_STATIC_DRAW);
    gl.glTexCoordPointer(2, GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getNormalBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.getNormalBufferSize(), data.getNormalBufferData(), GL_STATIC_DRAW);
    gl.glNormalPointer(GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_NORMAL_ARRAY);

    gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.getElementBufferID());
    gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.getElementBufferSize(), data.getElementBufferData(), GL_STATIC_DRAW);
  }
}
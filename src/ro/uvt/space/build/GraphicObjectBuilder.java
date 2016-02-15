
package ro.uvt.space.build;

import static javax.media.opengl.GL.GL_ARRAY_BUFFER;
import static javax.media.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.GL.GL_STATIC_DRAW;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_NORMAL_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import java.nio.IntBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.Texture;

import ro.uvt.api.util.MaterialProperties;
import ro.uvt.space.graphic_objects.Floor;
import ro.uvt.space.graphic_objects.JetPlane;
import ro.uvt.space.util.ObjContent;
import ro.uvt.space.util.Parser;
import ro.uvt.space.util.TextureReader;

public class GraphicObjectBuilder {

  private GL2 gl;

  private Parser parser = new Parser("resources/");

  private TextureReader textureParser;

  public GraphicObjectBuilder(GL2 gl) {
    this.gl = gl;
    textureParser = new TextureReader(gl);
  }

  public JetPlane buildJetPlane() {
    JetPlane jetPlane = new JetPlane(gl);

    jetPlane.addComponent(buildComponent("bodyMat.obj", "camo.png"));
    jetPlane.addComponent(buildComponent("bombsMat.obj", "metal.png"));
    jetPlane.addComponent(buildComponent("glassMat.obj", "glass.png"));

    return jetPlane;
  }

  public Floor buildFloor() {
    Floor floor = new Floor(gl);

    floor.addComponent(buildComponent("floor.obj", "floor.png"));

    return floor;
  }

  private GraphicComponent buildComponent(String objFileName, String textureFileName) {

    ObjContent objContent = parser.fetchObj(objFileName);

    injectIds(objContent);

    loadInVideoCard(objContent);

    MaterialProperties mtlContent = parser.fetchMaterial(objContent.getMtlFileName());

    Texture texture = textureParser.readTexture(textureFileName, ".png");

    return new GraphicComponent(objContent.getVaoID(), mtlContent, texture, objContent.computeTotalElements());
  }

  private void loadInVideoCard(ObjContent data) {

    gl.glBindVertexArray(data.getVaoID());

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getVertexBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.computeVertexBufferSize(), data.getVertexBufferData(), GL_STATIC_DRAW);
    gl.glVertexPointer(3, GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_VERTEX_ARRAY);

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getTextureBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.computeTextureBufferSize(), data.getTextureBufferData(), GL_STATIC_DRAW);
    gl.glTexCoordPointer(2, GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    gl.glBindBuffer(GL_ARRAY_BUFFER, data.getNormalBufferID());
    gl.glBufferData(GL_ARRAY_BUFFER, data.computeNormalBufferSize(), data.getNormalBufferData(), GL_STATIC_DRAW);
    gl.glNormalPointer(GL_FLOAT, 0, 0);
    gl.glEnableClientState(GL_NORMAL_ARRAY);

    gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.getElementBufferID());
    gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.computeElementBufferSize(), data.getElementBufferData(), GL_STATIC_DRAW);
  }

  private void injectIds(ObjContent target) {
    IntBuffer vaoIdBuffer = Buffers.newDirectIntBuffer(1);
    gl.glGenVertexArrays(1, vaoIdBuffer);

    IntBuffer vboIdBuffer = Buffers.newDirectIntBuffer(4);
    gl.glGenBuffers(4, vboIdBuffer);

    target.setVaoID(vaoIdBuffer.get(0));

    target.setVertexBufferID(vboIdBuffer.get(0));
    target.setTextureBufferID(vboIdBuffer.get(1));
    target.setNormalBufferID(vboIdBuffer.get(2));
    target.setElementBufferID(vboIdBuffer.get(3));
  }
}
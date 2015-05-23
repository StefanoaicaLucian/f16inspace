
package ro.uvt.space.build;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import ro.uvt.api.particles.Material;
import ro.uvt.space.util.MtlFileParser;
import ro.uvt.space.util.ObjContent;
import ro.uvt.space.util.ObjFileParser;
import ro.uvt.space.util.TextureReader;

public class GraphicComponentBuilder {

  private GL2 gl;

  private ObjFileParser objParser = new ObjFileParser("resources/");
  private MtlFileParser mtlParser = new MtlFileParser("resources/");
  
  private TextureReader textureParser;
  private BufferIdGenerator generator;
  private ObjContentLoader objLoader;

  public GraphicComponentBuilder(GL2 gl) {
    this.gl = gl;
    textureParser = new TextureReader(this.gl, "res/");
    generator = new BufferIdGenerator(this.gl);
    objLoader = new ObjContentLoader(gl);
  }

  public GraphicComponent buildComponent(String objFileName, String textureFileName) {

    ObjContent objContent = objParser.fetchContent(objFileName);
    
    generator.injectIds(objContent);

    objLoader.loadInVideoCard(objContent);

    Material mtlContent = mtlParser.fetchContent(objContent.getMtlFileName());

    Texture texture = textureParser.readTexture(textureFileName, ".png");

    return new GraphicComponent(objContent.getVaoID(), mtlContent, texture, objContent.getTotalElements());
  }
}

package ro.space.load.builders;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import ro.space.display.graphic_objects.GraphicComponent;
import ro.space.load.contents.MtlContent;
import ro.space.load.contents.ObjContent;
import ro.space.load.loaders.BufferIdGenerator;
import ro.space.load.loaders.ObjContentLoader;
import ro.space.read.parsers.MtlFileParser;
import ro.space.read.parsers.ObjFileParser;
import ro.space.read.textures.TextureReader;

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

    MtlContent mtlContent = mtlParser.fetchContent(objContent.getMtlFileName());

    Texture texture = textureParser.createTexture(textureFileName, ".png");

    return new GraphicComponent(objContent.getVaoID(), mtlContent, texture, objContent.getTotalElements());
  }
}
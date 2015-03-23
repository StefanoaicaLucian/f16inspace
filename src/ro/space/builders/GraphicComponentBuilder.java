
package ro.space.builders;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import ro.space.contents.MtlContent;
import ro.space.contents.ObjContent;
import ro.space.contents.ObjContentLoader;
import ro.space.display.GraphicComponent;
import ro.space.loaders.BufferIdGenerator;
import ro.space.parsers.MtlFileParser;
import ro.space.parsers.ObjFileParser;
import ro.space.parsers.TextureParser;

public class GraphicComponentBuilder {

  private GL2 gl;

  private ObjFileParser objParser = new ObjFileParser("resources/");
  private MtlFileParser mtlParser = new MtlFileParser("resources/");
  private TextureParser textureParser;
  private BufferIdGenerator generator;
  private ObjContentLoader objLoader;

  public GraphicComponentBuilder(GL2 gl) {
    this.gl = gl;
    textureParser = new TextureParser(this.gl, "res/");
    generator = new BufferIdGenerator(this.gl);
    objLoader = new ObjContentLoader(gl);
  }

  public GraphicComponent buildComponent(String objFileName, String textureFileName) {

    ObjContent objContent = objParser.fetchContent(objFileName);
    generator.injectIds(objContent);
    objLoader.loadInVideoCard(objContent);

    MtlContent mtlContent = mtlParser.fetchContent(objParser.getMtlFileName());

    Texture texture = textureParser.createTexture(textureFileName, ".png");

    return new GraphicComponent(objContent.getVaoID(), mtlContent, texture, objContent.getTotalElements());
  }
}
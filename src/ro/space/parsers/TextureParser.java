
package ro.space.parsers;

import static javax.media.opengl.GL.GL_NEAREST;
import static javax.media.opengl.GL.GL_REPEAT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_S;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_T;

import java.net.URL;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureParser {

  private GL2 gl;
  
  private String filesLocation;

  public TextureParser(GL2 gl, String filesLocation) {
    this.gl = gl;
    this.filesLocation = filesLocation;
  }

  public Texture createTexture(String fileName, String fileType) {
    try {
      String filePath = filesLocation + fileName;

      URL texturePicture = getClass().getClassLoader().getResource(filePath);
      
      // TODO not sure if these are meant to be here, better move them somewhere else in the future
      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

      Texture resultTexture = TextureIO.newTexture(texturePicture, false, fileType);

      resultTexture.enable(gl);
      
      return resultTexture;

    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }
}
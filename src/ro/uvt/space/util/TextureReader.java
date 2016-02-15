
package ro.uvt.space.util;

import java.io.File;
import javax.media.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import static javax.media.opengl.GL.GL_NEAREST;
import static javax.media.opengl.GL.GL_REPEAT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_S;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_T;

public class TextureReader {

  private GL2 gl;

  public TextureReader(GL2 gl) {
    this.gl = gl;
    setGLTextureFlags();
  }

  public Texture readTexture(String fileName, String fileType) {
    try {

      // Texture resultTexture = TextureIO.newTexture(texturePicture, false, fileType);

      Texture resultTexture = TextureIO.newTexture(new File("textures/" + fileName), true);

      resultTexture.enable(gl);

      return resultTexture;

    } catch (Exception e) {
      System.err.println(e.toString());
      return null;
    }
  }

  public void setGLTextureFlags() {
    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
  }
}
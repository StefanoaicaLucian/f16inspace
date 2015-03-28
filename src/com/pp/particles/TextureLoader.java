
package com.pp.particles;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {

  private Texture texture;
  private float textureTop, textureBottom, textureLeft, textureRight;

  private String textureFileName = "/res/fireParticle.png";
  private String textureFileType = ".png";

  private GL2 gl;

  public TextureLoader(GL2 gl) {
    this.gl = gl;
    loadTexture();
    enableAndBindTex();
  }

  private void loadTexture() {
    try {
      texture = TextureIO.newTexture(this.getClass().getResource(textureFileName), false, textureFileType);
    } catch (Exception e) {
      e.printStackTrace();
    }

    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    TextureCoords textureCoords = texture.getImageTexCoords();
    textureTop = textureCoords.top();
    textureBottom = textureCoords.bottom();
    textureLeft = textureCoords.left();
    textureRight = textureCoords.right();
  }

  public void enableAndBindTex() {
    texture.enable(gl);
    texture.bind(gl);
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public float getTextureTop() {
    return textureTop;
  }

  public void setTextureTop(float textureTop) {
    this.textureTop = textureTop;
  }

  public float getTextureBottom() {
    return textureBottom;
  }

  public void setTextureBottom(float textureBottom) {
    this.textureBottom = textureBottom;
  }

  public float getTextureLeft() {
    return textureLeft;
  }

  public void setTextureLeft(float textureLeft) {
    this.textureLeft = textureLeft;
  }

  public float getTextureRight() {
    return textureRight;
  }

  public void setTextureRight(float textureRight) {
    this.textureRight = textureRight;
  }
}
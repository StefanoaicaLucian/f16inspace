
package ro.space.listeners;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import javax.media.opengl.GL2;

public class LightHandler {

  private GL2 gl;

  public LightHandler(GL2 gl) {
    this.gl = gl;
  }

  public void setupLights() {
    float lightPosition[] = {20f, 20f, 20f, 1f};
    float lightDiffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float lightSpecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float lightAmbient[] = {0.6f, 0.6f, 0.6f, 1.0f};

    gl.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition, 0);
    gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse, 0);
    gl.glLightfv(GL_LIGHT0, GL_SPECULAR, lightSpecular, 0);
    gl.glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient, 0);
  }
}
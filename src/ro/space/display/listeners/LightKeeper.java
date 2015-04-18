
package ro.space.display.listeners;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import javax.media.opengl.GL2;

public class LightKeeper {

  private GL2 gl;

  public LightKeeper(GL2 gl) {
    this.gl = gl;
  }

  public void setupLights() {
    // TODO hard-coded for now, maybe later I will do something about it 
    float firstLightPosition[] = {20f, 20f, -20f, 1f};
    float secondLightPosition[] = {20f, 20f, 20f, 1f};

    float lightDiffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float lightSpecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float lightAmbient[] = {0.6f, 0.6f, 0.6f, 1.0f};

    gl.glLightfv(GL_LIGHT0, GL_POSITION, firstLightPosition, 0);
    gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse, 0);
    gl.glLightfv(GL_LIGHT0, GL_SPECULAR, lightSpecular, 0);
    gl.glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient, 0);

    gl.glLightfv(GL_LIGHT1, GL_POSITION, secondLightPosition, 0);
    gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0);
    gl.glLightfv(GL_LIGHT1, GL_SPECULAR, lightSpecular, 0);
    gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0);

    gl.glEnable(GL_LIGHTING);
    gl.glEnable(GL_LIGHT0);
    gl.glEnable(GL_LIGHT1);
  }
}
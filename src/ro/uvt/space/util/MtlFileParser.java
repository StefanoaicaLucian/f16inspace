
package ro.uvt.space.util;

import java.util.ArrayList;

import ro.uvt.api.particles.Material;


public class MtlFileParser extends Parser {

  private float ns[] = new float[1];
  private float ka[] = new float[4];
  private float kd[] = new float[4];
  private float ks[] = new float[4];

  public MtlFileParser(String mtlFileName) {
    super(mtlFileName);
  }

  public Material fetchContent(String file) {

    ArrayList<String> lines = readLines(file);

    for (String line : lines) {

      String[] splitedLine = line.split("\\s+");

      switch (splitedLine[0]) {
        case "Ns":
          ns[0] = Float.parseFloat(splitedLine[1]);
          break;

        case "Ka":
          putValues(splitedLine, ka);
          break;

        case "Kd":
          putValues(splitedLine, kd);
          break;

        case "Ks":
          putValues(splitedLine, ks);
          break;
      }
    }

    return new Material(ka, kd, ks, ns);
  }

  private void putValues(String[] source, float target[]) {
    for (int i = 1; i < source.length; ++i) {
      target[i - 1] = Float.parseFloat(source[i]);
    }
    target[3] = 1.0f;
  }
}
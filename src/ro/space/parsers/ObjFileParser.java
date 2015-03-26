
package ro.space.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ro.space.contents.ObjContent;
import ro.space.utils.Converter;
import ro.space.wavefront.Vertex;
import ro.space.wavefront.VertexIndices;

public class ObjFileParser extends Parser {

  private ArrayList<Float> positionData = new ArrayList<>();
  private ArrayList<Float> normalData = new ArrayList<>();
  private ArrayList<Float> textureData = new ArrayList<>();

  private Map<Integer, Vertex> outputVertices = new HashMap<>();
  private ArrayList<Integer> vertexIndices = new ArrayList<>();

  private int currentVertexIndex = 0;

  public ObjFileParser(String filesLocation) {
    super(filesLocation);
  }

  public ObjContent fetchContent(String objFileName) {
    clean();

    readLines(objFileName);

    ObjContent result = new ObjContent();

    for (String line : lines) {

      String[] splitedLine = line.split("\\s+");

      switch (splitedLine[0]) {

        case "mtllib":
          result.setMtlFileName(splitedLine[1]);
          break;

        case "v":
          putLineDataIntoList(splitedLine, positionData);
          break;

        case "vt":
          putLineDataIntoList(splitedLine, textureData);
          break;

        case "vn":
          putLineDataIntoList(splitedLine, normalData);
          break;

        case "f":
          handleFace(splitedLine);

        default:
          break;
      }
    }

    putDataInTarget(result);

    return result;
  }

  private void clean() {
    positionData = new ArrayList<>();
    normalData = new ArrayList<>();
    textureData = new ArrayList<>();

    outputVertices = new HashMap<>();
    vertexIndices = new ArrayList<>();

    currentVertexIndex = 0;
  }

  private void putDataInTarget(ObjContent target) {
    ArrayList<Float> positionBuffer = new ArrayList<>();
    ArrayList<Float> normalBuffer = new ArrayList<>();
    ArrayList<Float> textureBuffer = new ArrayList<>();

    for (int index : vertexIndices) {
      Vertex tempVertex = outputVertices.get(index);

      positionBuffer.add(tempVertex.getPositionX());
      positionBuffer.add(tempVertex.getPositionY());
      positionBuffer.add(tempVertex.getPositionZ());

      textureBuffer.add(tempVertex.getTextureU());
      textureBuffer.add(tempVertex.getTextureV());

      normalBuffer.add(tempVertex.getNormalX());
      normalBuffer.add(tempVertex.getNormalY());
      normalBuffer.add(tempVertex.getNormalZ());
    }

    target.setVertexBufferData(Converter.convertToFloatArray(positionBuffer));

    target.setTextureBufferData(Converter.convertToFloatArray(textureBuffer));

    target.setNormalBufferData(Converter.convertToFloatArray(normalBuffer));

    target.setElementBufferData(Converter.convertToIntArray(vertexIndices));
  }

  private void putLineDataIntoList(String[] splitedLine, ArrayList<Float> targetList) {

    for (int i = 1; i < splitedLine.length; ++i) {
      targetList.add(Float.parseFloat(splitedLine[i]));
    }
  }

  private void handleFace(String[] splitedLine) {
    for (int i = 1; i < splitedLine.length; ++i) {
      VertexIndices indices = parseWord(splitedLine[i]);
      Vertex aVertex = composeVertex(indices);
      fillIndexArray(aVertex);
    }
  }

  private VertexIndices parseWord(String word) {
    StringBuilder partOne = new StringBuilder();
    StringBuilder partTwo = new StringBuilder();
    StringBuilder partThree = new StringBuilder();

    int i = 0;
    for (; i < word.length(); ++i) {
      if (word.charAt(i) == '/') {
        ++i;
        break;
      }
      partOne.append(word.charAt(i));
    }

    for (; i < word.length(); ++i) {
      if (word.charAt(i) == '/') {
        ++i;
        break;
      }
      partTwo.append(word.charAt(i));
    }

    for (; i < word.length(); ++i) {
      partThree.append(word.charAt(i));
    }

    int positionIndex = 0;
    if (!partOne.toString().equals("")) {
      positionIndex = Integer.parseInt(partOne.toString());
    }

    int textureIndex = 0;
    if (!partTwo.toString().equals("")) {
      textureIndex = Integer.parseInt(partTwo.toString());
    }

    int normalIndex = 0;
    if (!partThree.toString().equals("")) {
      normalIndex = Integer.parseInt(partThree.toString());
    }

    VertexIndices indices = new VertexIndices(positionIndex, textureIndex, normalIndex);

    return indices;
  }

  private Vertex composeVertex(VertexIndices indices) {

    Vertex vert = new Vertex();

    vert.setPositionX(positionData.get((indices.getPositionIndex() - 1) * 3));
    vert.setPositionY(positionData.get(((indices.getPositionIndex() - 1) * 3) + 1));
    vert.setPositionZ(positionData.get(((indices.getPositionIndex() - 1) * 3) + 2));

    vert.setTextureU(textureData.get((indices.getTextureIndex() - 1) * 2));
    vert.setTextureV(textureData.get(((indices.getTextureIndex() - 1) * 2) + 1));

    vert.setNormalX(normalData.get((indices.getNormalIndex() - 1) * 3));
    vert.setNormalY(normalData.get(((indices.getNormalIndex() - 1) * 3) + 1));
    vert.setNormalZ(normalData.get(((indices.getNormalIndex() - 1) * 3) + 2));

    return vert;
  }

  private void fillIndexArray(Vertex inputVertex) {
    if (outputVertices.containsValue(inputVertex)) {
      inputVertex.setIndex(currentVertexIndex);
      vertexIndices.add(currentVertexIndex);
    } else {
      inputVertex.setIndex(currentVertexIndex);
      outputVertices.put(currentVertexIndex, inputVertex);
      vertexIndices.add(currentVertexIndex);
      ++currentVertexIndex;
    }
  }
}
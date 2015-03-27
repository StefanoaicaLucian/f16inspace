
package ro.space.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.space.contents.ObjContent;
import ro.space.utils.Converter;
import ro.space.wavefront.Vertex;
import ro.space.wavefront.VertexIndices;

public class ObjFileParser extends Parser {

  private List<Float> coordinatesBuffer = new ArrayList<>();
  private List<Float> normalsBuffer = new ArrayList<>();
  private List<Float> texturesBuffer = new ArrayList<>();

  private Map<Integer, Vertex> outputVertices = new HashMap<>();
  private ArrayList<Integer> vertexIndices = new ArrayList<>();

  private int currentVertexIndex = 0;

  private String mtlFileName;

  public ObjFileParser(String filesLocation) {
    super(filesLocation);
  }

  public ObjContent fetchContent(String objFileName) {
    resetState();

    ArrayList<String> lines = readLines(objFileName);

    for (String line : lines) {

      String[] splitedLine = line.split("\\s+");

      switch (splitedLine[0]) {

        case "mtllib":
          mtlFileName = splitedLine[1];
          break;

        case "v":
          putLineDataIntoList(splitedLine, coordinatesBuffer);
          break;

        case "vt":
          putLineDataIntoList(splitedLine, texturesBuffer);
          break;

        case "vn":
          putLineDataIntoList(splitedLine, normalsBuffer);
          break;

        case "f":
          handleFace(splitedLine);

        default:
          break;
      }
    }

    return buildObjContent();
  }

  private void resetState() {
    coordinatesBuffer = new ArrayList<>();
    normalsBuffer = new ArrayList<>();
    texturesBuffer = new ArrayList<>();

    vertexIndices = new ArrayList<>();

    outputVertices = new HashMap<>();

    currentVertexIndex = 0;
  }

  private ObjContent buildObjContent() {
    ArrayList<Float> coordinates = new ArrayList<>();
    ArrayList<Float> normals = new ArrayList<>();
    ArrayList<Float> textures = new ArrayList<>();

    for (int index : vertexIndices) {
      Vertex tempVertex = outputVertices.get(index);

      coordinates.add(tempVertex.getPositionX());
      coordinates.add(tempVertex.getPositionY());
      coordinates.add(tempVertex.getPositionZ());

      textures.add(tempVertex.getTextureU());
      textures.add(tempVertex.getTextureV());

      normals.add(tempVertex.getNormalX());
      normals.add(tempVertex.getNormalY());
      normals.add(tempVertex.getNormalZ());
    }

    ObjContent result = new ObjContent();

    result.setMtlFileName(mtlFileName);
    result.setVertexBufferData(Converter.convertToFloatArray(coordinates));
    result.setTextureBufferData(Converter.convertToFloatArray(textures));
    result.setNormalBufferData(Converter.convertToFloatArray(normals));
    result.setElementBufferData(Converter.convertToIntArray(vertexIndices));

    return result;
  }

  private void putLineDataIntoList(String[] splitedLine, List<Float> targetList) {
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

    StringBuilder[] parts = {partOne, partTwo, partThree};
    int partIndex = 0;

    for (char oneChar : word.toCharArray()) {
      if (oneChar == '/') {
        ++partIndex;
        continue;
      }

      parts[partIndex].append(oneChar);
    }

    int indices[] = {0, 0, 0};

    for (int i = 0; i < indices.length; ++i) {
      String part = parts[i].toString();

      if (part.equals("") == false) {
        indices[i] = Integer.parseInt(part);
      }
    }

    return new VertexIndices(indices[0], indices[1], indices[2]);
  }

  private Vertex composeVertex(VertexIndices indices) {
    Vertex result = new Vertex();

    result.setPositionX(coordinatesBuffer.get((indices.getPositionIndex() - 1) * 3));
    result.setPositionY(coordinatesBuffer.get(((indices.getPositionIndex() - 1) * 3) + 1));
    result.setPositionZ(coordinatesBuffer.get(((indices.getPositionIndex() - 1) * 3) + 2));

    result.setTextureU(texturesBuffer.get((indices.getTextureIndex() - 1) * 2));
    result.setTextureV(texturesBuffer.get(((indices.getTextureIndex() - 1) * 2) + 1));

    result.setNormalX(normalsBuffer.get((indices.getNormalIndex() - 1) * 3));
    result.setNormalY(normalsBuffer.get(((indices.getNormalIndex() - 1) * 3) + 1));
    result.setNormalZ(normalsBuffer.get(((indices.getNormalIndex() - 1) * 3) + 2));

    return result;
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
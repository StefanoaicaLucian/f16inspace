
package ro.space.util.converters;

import java.util.List;

public class ListConverter {

  private int[] intArray;
  private float[] floatArray;

  private int arraySize;

  public int[] convertToIntArray(List<Integer> container) {
    arraySize = container.size();
    intArray = new int[arraySize];

    int i = 0;
    for (int val : container) {
      intArray[i] = val;
      ++i;
    }

    return intArray;
  }

  public float[] convertToFloatArray(List<Float> container) {
    arraySize = container.size();
    floatArray = new float[arraySize];

    int i = 0;
    for (float val : container) {
      floatArray[i] = val;
      ++i;
    }

    return floatArray;
  }
}
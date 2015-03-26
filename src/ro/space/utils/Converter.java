
package ro.space.utils;

import java.util.ArrayList;

public class Converter {

  public static int[] convertToIntArray(ArrayList<Integer> container) {
    int size = container.size();
    int[] result = new int[size];
    
    int i = 0;
    for (int val : container) {
      result[i] = val;
      ++i;
    }
    
    return result;
  }
  
  public static float[] convertToFloatArray(ArrayList<Float> container) {
    int size = container.size();
    float[] result = new float[size];

    int i = 0;
    for (float val : container) {
      result[i] = val;
      ++i;
    }
    
    return result;
  }
}
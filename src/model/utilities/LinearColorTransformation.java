package model.utilities;

import java.util.Hashtable;
import java.util.Map;

/**
 * Helper class to contain various linear color transformations.
 * Transformation data is stored here and is accessible by name.
 */
public class LinearColorTransformation {

  //contains LCT kernels, hashed by name
  private final Map<String, double[][]> linearTransformations = new Hashtable<>();

  /**
   * The constructor for a linear color transformation object.
   */
  public LinearColorTransformation() {

    //Greyscale transformation matrix values
    double[][] greyscale = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
    linearTransformations.put("greyscale", greyscale);

    //Sepia transformation matrix values
    double[][] sepia = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    linearTransformations.put("sepia", sepia);
  }

  /**
   * Method to retrieve a linear transformation matrix by name.
   *
   * @param name the name of the linear color transformation
   * @return Double[][] the requested linear color transformation matrix
   * @throws IllegalArgumentException if the requested transformation matrix does not exist
   */
  public double[][] getLinearTransformation(String name) throws IllegalArgumentException {
    String lowerName = name.toLowerCase();

    if (!linearTransformations.containsKey(lowerName)) {
      throw new IllegalArgumentException("Invalid request. Transformation " + lowerName
              + "does not exist");
    }
    return linearTransformations.get(lowerName);
  }

}

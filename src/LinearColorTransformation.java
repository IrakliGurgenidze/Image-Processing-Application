import java.util.Hashtable;

/**
 * Helper class to contain various linear color transformations.
 * Transformation data is stored here and is accessible by name.
 */
public class LinearColorTransformation {
  Hashtable<String, double[][]> linearTransformations = new Hashtable<>();

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
          {0.272, 0.534, 0.131},
          {0.349, 0.686, 0.168},
          {0.393, 0.769, 0.189}
    };
    linearTransformations.put("sepia",sepia);
  }

  /**
   * Method to retrieve a linear transformation matrix by name.
   * @param name the name of the linear color transformation
   * @return Double[][] the requested linear color transformation matrix
   * @throws IllegalArgumentException if the requested transformation matrix does not exist
   */
  double[][] getLinearTransformation(String name) throws IllegalArgumentException{
    if(!linearTransformations.containsKey(name)) {
      throw new IllegalArgumentException("Invalid request. " + name
      + "does not exist");
    }
    return linearTransformations.get(name);
  }

}

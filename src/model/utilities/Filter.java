package model.utilities;

import java.util.Hashtable;

/**
 * Helper static class to contain filters and their information.
 * model.utilities.Filter data is stored here, and can be accessed by name.
 */
public class Filter {
  Hashtable<String, double[][]> filters = new Hashtable<>();

  /**
   * Constructor for a filter object.
   */
  public Filter() {
    double[][] blur = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    filters.put("blur", blur);

    double[][] sharpen = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1.0, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    filters.put("sharpen", sharpen);
  }

  /**
   * Method to retrieve a filter array by name.
   *
   * @param name String, name of the filter to be retrieved
   * @return Double[][] the filter associated with provided name
   * @throws IllegalArgumentException if requested filer does not exist
   */
  public double[][] getFilter(String name) throws IllegalArgumentException {
    String lowerName = name.toLowerCase();

    if (!filters.containsKey(lowerName)) {
      throw new IllegalArgumentException("Invalid request. Filter " + lowerName
              + " does not exist.");
    }
    return filters.get(lowerName);
  }
}
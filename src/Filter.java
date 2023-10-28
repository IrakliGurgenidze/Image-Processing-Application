import java.util.Hashtable;

/**
 * Helper static class to contain filters and their information.
 * Filter data is stored here, and can be accessed by name.
 */
public class Filter {
  Hashtable<String, double[][]> filters = new Hashtable<>();

  /**
   * Constructor for a filter object.
   */
  public Filter() {
    double[][] blur = {
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
            {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}
    };
    filters.put("blur", blur);

    double[][] sharpen = {
            {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}
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
  double[][] getFilter(String name) throws IllegalArgumentException {
    if (!filters.containsKey(name)) {
      throw new IllegalArgumentException("Invalid request. Filter " + name + " does not exist.");
    }

    return filters.get(name);
  }
}

/**
 * Implementation of a color image.
 */
public class ColorImage extends AbstractImage {

  /**
   * Public constructor for a color image.
   *
   * @param width  int, width of image
   * @param height int, height of image
   * @param name   String, name of image
   */
  public ColorImage(int width, int height, String name) {
    super(width, height, name);
  }

  //FIXME NEED to check this
  @Override
  public Image applyFilter(double[][] filter, String editedName) {
    int filterSize = filter.length;

    ColorImage filteredImage = new ColorImage(getWidth(), getHeight(), editedName);
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        double redSum = 0.0;
        double greenSum = 0.0;
        double blueSum = 0.0;

        for (int i = -filterSize / 2; i <= filterSize / 2; i++) {
          for (int j = -filterSize / 2; j <= filterSize / 2; j++) {
            if (x + j >= 0 && x + j < getWidth() && y + i >= 0 && y + i < getHeight()) {
              Pixel neighbor = getPixel(x + j, y + i);

              redSum += neighbor.getRed() * filter[i + filterSize / 2][j + filterSize / 2];
              greenSum += neighbor.getGreen() * filter[i + filterSize / 2][j + filterSize / 2];
              blueSum += neighbor.getBlue() * filter[i + filterSize / 2][j + filterSize / 2];
            }
          }
        }
        Pixel filteredPixel = new Pixel((int) redSum, (int) greenSum, (int) blueSum);
        filteredImage.setPixel(x, y, filteredPixel);
      }
    }
    return filteredImage;
  }

  //FIXME NEED to check this
  @Override
  public Image applyLinearColorTransformation(double[][] transformation, String editedName) {
    ColorImage transformedImage = new ColorImage(getWidth(), getHeight(), editedName);
    int red;
    int green;
    int blue;

    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Pixel currPixel = getPixel(x, y);

        red = (int) (transformation[0][0] * currPixel.getRed() + transformation[0][1] * currPixel.getGreen()
                + transformation[0][2] * currPixel.getGreen());
        green = (int) (transformation[1][0] * currPixel.getRed() + transformation[1][1] * currPixel.getGreen()
                + transformation[1][2] * currPixel.getGreen());
        blue = (int) (transformation[2][0] * currPixel.getRed() + transformation[2][1] * currPixel.getGreen()
                + transformation[2][2] * currPixel.getGreen());

        transformedImage.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    return transformedImage;
  }

  @Override
  public Image applyBrighten(int increment, String editedName) {
    return null;
  }
}

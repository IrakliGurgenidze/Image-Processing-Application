
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


  @Override
  public Image applyFilter(double[][] filter, String editedName) {
    int filterSize = filter.length;

    ColorImage filteredImage = new ColorImage(getWidth(), getHeight(), editedName);
    double redSum;
    double greenSum;
    double blueSum;

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        redSum = 0.0;
        greenSum = 0.0;
        blueSum = 0.0;

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
    ColorImage brightenedImage = new ColorImage(getWidth(), getHeight(), editedName);

    int red;
    int green;
    int blue;

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        red = currPixel.getRed() + increment;
        green = currPixel.getGreen() + increment;
        blue = currPixel.getBlue() + increment;

        brightenedImage.setPixel(x,y, new Pixel(red,green,blue));
      }
    }

    return brightenedImage;
  }

  @Override
  public Image getLumaImage(String editedName){
    ColorImage lunaImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        lunaImage.setPixel(x, y, new Pixel((int)currPixel.getLuma()));
      }
    }
    return lunaImage;
  }

  @Override
  public Image getIntensityImage(String editedName) {
    ColorImage intensityImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        intensityImage.setPixel(x, y, new Pixel((int)currPixel.getIntensity()));
      }
    }
    return intensityImage;
  }

  @Override
  public Image getValueImage(String editedName) {
    ColorImage valueImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        valueImage.setPixel(x, y, new Pixel(currPixel.getValue()));
      }
    }
    return valueImage;
  }

  @Override
  public Image getRedImage(String editedName) {
    ColorImage redImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        redImage.setPixel(x, y, new Pixel(currPixel.getRed(),0,0));
      }
    }
    return redImage;
  }

  @Override
  public Image getGreenImage(String editedName) {
    ColorImage greenImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        greenImage.setPixel(x, y, new Pixel(0,currPixel.getGreen(),0));
      }
    }
    return greenImage;
  }

  @Override
  public Image getBlueImage(String editedName) {
    ColorImage blueImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        blueImage.setPixel(x, y, new Pixel(0,0,currPixel.getBlue()));
      }
    }
    return blueImage;
  }

  @Override
  public Image getHorizontalFlip(String editedName) {
    ColorImage newImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        //horizontal flip -> new x coordinate
        int flippedX = getWidth() - x - 1;

        newImage.setPixel(flippedX, y, currPixel);
      }
    }
    return newImage;
  }

  @Override
  public Image getVerticalFlip(String editedName) {
    ColorImage newImage = new ColorImage(getWidth(), getHeight(), editedName);

    for(int y = 0; y < getHeight(); y++) {
      for(int x = 0; x < getWidth(); x++) {
        Pixel currPixel = getPixel(x,y);

        //vertical flip -> new y coordinate
        int flippedY = getHeight() - y - 1;

        newImage.setPixel(x, flippedY, currPixel);
      }
    }
    return newImage;
  }


}

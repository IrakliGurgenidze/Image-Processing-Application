import java.util.Hashtable;

/**
 * This class represents the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public class ImageModel implements Model {

  //photo filters and transformations
  private final Filter filters = new Filter();
  private final LinearColorTransformation transformations = new LinearColorTransformation();

  //list of images loaded during this session
  private final Hashtable<String, Image> images = new Hashtable<>();

  @Override
  public Image getImage(String name) {
    return images.get(name);
  }

  @Override
  public void insertImage(Image image) {
    images.put(image.getName(), image);
  }

  @Override
  public void removeImage(String imageName) throws IllegalArgumentException {
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with name "
              + imageName);
    }

    images.remove(imageName);
  }

  @Override
  public void applyFilter(String imageName, String editedName, String filterName)
          throws IllegalArgumentException {

    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with name "
              + imageName);
    }

    //retrieve filter and edited image
    double[][] filter = filters.getFilter(filterName);
    Image editedImage = getImage(imageName).applyFilter(filter, editedName);

    //insert edited image
    this.insertImage(editedImage);
  }

  @Override
  public void applyLinearColorTransformation(String imageName, String editedName,
                                             String transformationName)
      throws IllegalArgumentException {

    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with name "
      + imageName);
    }

    double[][] transformation = transformations.getLinearTransformation(transformationName);
    Image editedImage = getImage(imageName).applyLinearColorTransformation(transformation,
            editedName);

    this.insertImage(editedImage);

  }

  @Override
  public void applyBrighten(int increment, String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with name " + imageName);
    }
    Image editedImage = getImage(imageName).applyBrighten(increment, editedName);
    this.insertImage(editedImage);
  }

  @Override
  public void lumaImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image lumaImg = getImage(imageName).getLumaImage(editedName);
    this.insertImage(lumaImg);
  }

  @Override
  public void intensityImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image intensityImg = getImage(imageName).getIntensityImage(editedName);
    this.insertImage(intensityImg);
  }

  @Override
  public void valueImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image valueImg = getImage(imageName).getValueImage(editedName);
    this.insertImage(valueImg);
  }

  @Override
  public void redImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image redImg = getImage(imageName).getRedImage(editedName);
    this.insertImage(redImg);
  }

  @Override
  public void greenImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image greenImg = getImage(imageName).getGreenImage(editedName);
    this.insertImage(greenImg);
  }

  @Override
  public void blueImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image blueImg = getImage(imageName).getBlueImage(editedName);
    this.insertImage(blueImg);
  }

  @Override
  public void horizontalFlipImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image horzImg = getImage(imageName).getHorizontalFlip(editedName);
    this.insertImage(horzImg);
  }

  @Override
  public void verticalFlipImage(String imageName, String editedName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Invalid request. Could not find image with the name + " + imageName);
    }
    Image vertImg = getImage(imageName).getVerticalFlip(editedName);
    this.insertImage(vertImg);
  }

  @Override
  public int getSize() {
    return this.images.size();
  }


}

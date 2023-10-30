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
  public int getSize() {
    return this.images.size();
  }


}

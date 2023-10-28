import java.util.Hashtable;

/**
 * This class represents the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public class ImageStore {

  //photo filters
  private final Filter filters = new Filter();

  //list of images loaded during this session
  private final Hashtable<String, Image> images = new Hashtable<>();

  /**
   * Method to fetch an image by name.
   *
   * @param name String, the name of the image to be retrieved
   * @return the image with a matching name, or null if no such image exists
   */
  public Image getImage(String name) {
    return images.get(name);
  }

  /**
   * Method to insert an image into the database. If duplicate name is entered, existing
   * image is overwritten.
   *
   * @param image Image, image to be inserted
   */
  public void insertImage(Image image) {
    images.put(image.getName(), image);
  }

  /**
   * Method to apply a filter to a specified image, and store the result.
   *
   * @param imageName  String, name of image to be edited
   * @param editedName String, name of resulting image
   * @param filterName String, name of filter (passed from the user)
   * @throws IllegalArgumentException if image by specified name doesn't exist, or it specified
   *                                  filter doesn't exist
   */
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

  /**
   * Method to retrieve number of images currently stored.
   *
   * @return int, the number of images in images
   */
  public int getSize() {
    return this.images.size();
  }


}

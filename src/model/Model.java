package model;

/**
 * This interface represents generic methods the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public interface Model {

  /**
   * Method to fetch an image by name.
   *
   * @param name String, the name of the image to be retrieved
   * @return the image with a matching name, or null if no such image exists
   */
  public Image getImage(String name) ;

  /**
   * Method to insert an image into the database. If duplicate name is entered, existing
   * image is overwritten.
   *
   * @param image model.Image, image to be inserted
   */
  public void insertImage(Image image);

  /**
   * Method to remove an image from the database.
   *
   * @param imageName String, name of image to be removed
   * @throws IllegalArgumentException if image by specified name doesn't exist
   */
  public void removeImage(String imageName) throws IllegalArgumentException;

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
          throws IllegalArgumentException;

  /**
   * Method to apply a linear color transformation to a specified image and store the result.
   *
   * @param imageName String, name of image to be edited
   * @param editedName String, name of edited image
   * @param transformationName String, name of transformation (passed from user)
   * @throws IllegalArgumentException if image specified does not exist or the specified linear
   *                                  color transformation does not exist
   */
  public void applyLinearColorTransformation(String imageName, String editedName,
                                             String transformationName)
          throws IllegalArgumentException;

  /**
   * Method to apply a brightening function to a specified image and store the result.
   *
   * @param increment int, the value by which each pixel will be brightened
   * @param imageName String, name of image
   * @param editedName String, name of edited (resulting) image
   * @throws IllegalArgumentException if image specified does not exist or increment is invalid
   */
  void applyBrighten(int increment, String imageName, String editedName) throws IllegalArgumentException;

  void lumaImage(String imageName, String editedName) throws IllegalArgumentException;

  void intensityImage(String imageName, String editedName) throws IllegalArgumentException;

  void valueImage(String imageName, String editedName) throws IllegalArgumentException;

  void redImage(String imageName, String editedName) throws IllegalArgumentException;

  void blueImage(String imageName, String editedName) throws IllegalArgumentException;

  void greenImage(String imageName, String editedName) throws IllegalArgumentException;

  void horizontalFlipImage(String imageName, String editedName) throws IllegalArgumentException;

  void verticalFlipImage(String imageName, String editedName) throws IllegalArgumentException;

  /**
   * Method to retrieve number of images currently stored.
   *
   * @return int, the number of images in images
   */
  int getSize();


}

package model;

import java.io.IOException;

/**
 * This interface represents generic methods the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public interface StorageModel {

  /**
   * Method to fetch an image by name.
   *
   * @param name String, the name of the image to be retrieved
   * @return the image with a matching name, or null if no such image exists
   */
  Image getImage(String name);

  /**
   * Method to insert an image into the database. If duplicate name is entered, existing
   * image is overwritten.
   *
   * @param image Image, image to be inserted
   */
  void insertImage(Image image);

  /**
   * Method to insert an image into the database by a separate name. If duplicate name is entered,
   * existing image is overwritten.
   *
   * @param image Image, image to be inserted
   * @param saveName String, name to save image under
   */
  void insertImage(Image image, String saveName);

  /**
   * Method to remove an image from the database.
   *
   * @param imageName String, name of image to be removed
   * @throws IllegalArgumentException if image by specified name doesn't exist
   */
  void removeImage(String imageName) throws IllegalArgumentException;


  /**
   * Method to retrieve number of images currently stored.
   *
   * @return int, the number of images in images
   */
  int getSize();


}

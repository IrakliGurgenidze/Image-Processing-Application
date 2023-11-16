package model;

import java.io.IOException;

/**
 * This interface represents generic methods the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public interface StorageModel {

  /**
   * Method to load an image into the model from a filepath.
   *
   * @param loadPath  String, the filepath of the image
   * @param imageName String, name by which to store the image in the database
   * @throws IllegalArgumentException if an image cannot be loaded
   */
  void loadImage(String loadPath, String imageName) throws IllegalArgumentException;

  /**
   * Method to save an image from the model to a given filepath.
   *
   * @param savePath  String, the filepath to be saved
   * @param imageName String, name of image in database
   * @throws IllegalArgumentException if an image name is invalid
   * @throws IOException              if a directory cannot be found/created
   */
  void saveImage(String savePath, String imageName) throws IllegalArgumentException, IOException;

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
   * @param image model.Image, image to be inserted
   */
  void insertImage(Image image);

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

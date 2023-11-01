package model;

import java.util.Hashtable;

/**
 * This class represents the "model" of an image processing application.
 * It contains a list of active images, and also provides methods to access and modify those images.
 */
public class ImageStorageStorageModel implements StorageModel {

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
  public int getSize() {
    return this.images.size();
  }


}

package model.gui;
import model.Image;

/**
 * This interface represents a storage model for this application's GUI.
 */
public interface GUIModel {

  /**
   * Return the base image currently loaded.
   *
   * @return Image, the base image
   */
  Image getBaseImage();

  /**
   * Return the modified version of the currently loaded image.
   * This is what the user sees.
   *
   * @return Image, the current image
   */
  Image getCurrentImage();


}

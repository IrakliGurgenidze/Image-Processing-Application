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
   * Set the base image of the GUI model. Also resets current image to mirror the new base image.
   *
   * @param baseImage image to be set as baseImage. Automatically updates currentImage to
   *                  reflect the new base image.
   */
  void setBaseImage(Image baseImage);

  /**
   * Return the modified version of the currently loaded image.
   * This represents "persistent" changes, such as those made by using the buttons.
   *
   * @return Image, the current image
   */
  Image getCurrentImage();

  /**
   * Set the current image of the GUI model.
   *
   * @param currentImage the base image, including persistent edits made.
   */
  void setCurrentImage(Image currentImage);

  /**
   * Return the display image. This is the current image, with variable modifications (sliders)
   * applied over top. This is what the user sees, and what gets saved if the user hits save.
   * This DOES NOT reflect the split operation.
   *
   * @return displayImage, the image to be displayed to the user not including the split-view
   *          slider.
   */
  Image getDisplayImage();

  /**
   * Set the display image of the GUI model.
   *
   * @param displayImage the current image, with non-persistent edits
   */
  void setDisplayImage(Image displayImage);


}

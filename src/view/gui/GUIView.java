package view.gui;

import java.awt.image.BufferedImage;
import java.util.Map;

import controller.gui.Features;

/**
 * Represents the graphical view of this application.
 */
public interface GUIView {

  /**
   * Adds the relevant features to the view.
   *
   * @param features features to be added
   */
  void addFeatures(Features features);

  /**
   * Displays the provided image to the screen, as well as the histogram of that image.
   * Should also update top bar description.
   *
   * @param displayImage image to be displayed. Can be null.
   * @param histogram    the histogram to be displayed on screen
   * @param displayName  String, name of image including its changelog
   */
  void displayImage(BufferedImage displayImage, BufferedImage histogram, String displayName);

  /**
   * This method returns the current values of specific UI elements that reflect non-persistent
   * user input. Examples are the compression slider and brighten element. They can be retrieved by
   * name, e.g. "compression-ratio".
   *
   * @return returns a map of current UI element values, referenced by their UI names.
   */
  Map<String, Double> getSliderValues();

}

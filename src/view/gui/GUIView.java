package view.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
   * user input.
   *
   * @return returns a map of current UI element values, referenced by their UI names.
   */
  Map<String, Double> getSliderValues();

  /**
   * This method is primarily used in the testing of the controller within a mock view.
   * It is used to call a feature directly, but has no use in the prod implementation of it.
   * In the mock View, it serves as an alternative to a callback function.
   *
   * @param featureName the name of the feature to be called
   */
  void callFeature(String featureName) throws IOException;
}

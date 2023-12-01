package controller_tests.feature_tests;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Mock View to test the GUI controller (Features) with.
 */
public interface FeatureTestView {

  /**
   * Calls a specific feature method by name.
   *
   * @param featureName the name/identifier of the feature method to be called
   */
  void callFeature(String featureName);

  /**
   * Displays the provided image to the screen, as well as the histogram of that image.
   * Should also update top bar description.
   * For this mock class, it returns the name of displayImage when called.
   * We just want proof that this function was called.
   *
   * @param displayImage image to be displayed. Can be null.
   * @param histogram    the histogram to be displayed on screen
   * @param displayName  String, name of image including its changelog
   * @return the name of displayImage
   */
  void displayImage(BufferedImage displayImage, BufferedImage histogram, String displayName);

  /**
   * This method returns the current values of specific UI elements that reflect non-persistent
   * user input. Examples are the compression slider and brighten element. They can be retrieved by
   * name, e.g. "compression-ratio".
   * For this mock class, we hard code these values. The controller then uses those in calculating
   * compression and brighten.
   *
   * @return returns a map of current UI element values, referenced by their UI names.
   */
  Map<String, Double> getSliderValues();
}

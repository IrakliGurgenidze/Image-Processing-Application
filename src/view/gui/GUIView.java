package view.gui;

import java.awt.image.BufferedImage;

import controller.gui.Features;
import model.Image;
import java.util.Map;

/**
 * Represents the graphical view of this application.
 */
public interface GUIView {

  /**
   * Set the label that displays the name of the current image.
   * @param imageLabel the name of the image being worked on
   */
  void setImageLabel(String imageLabel);



  /**
   * Gets the input from the relevant text field.
   */
  void getInputString();

  /**
   * Clears the input from the relevant text field.
   */
  void clearInputString();

  /**
   * Adds the relevant features to the view.
   * @param features features to be added
   */
  void addFeatures(Features features);

  /**
   * Displays the provided image to the screen.
   * Should also update top bar description.
   *
   * @param image image to be displayed. Can be null.
   * @param displayName String, name of image including its changelog
   */
  void displayImage(BufferedImage image, String displayName);

  /**
   * This method returns the current values of specific UI elements that reflect non-persistent
   * user input. Examples are the compression slider and brighten element. They can be retrieved by
   * name, e.g. "compression-ratio".
   *
   * @return returns a map of current UI element values, referenced by their UI names.
   */
  Map<String, Double> getSliderValues();

}
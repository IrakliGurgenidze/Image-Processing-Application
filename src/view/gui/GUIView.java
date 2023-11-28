package view.gui;

import controller.gui.Features;
import model.Image;

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
   */
  void displayImage(Image image);



}

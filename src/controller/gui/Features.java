package controller.gui;

/**
 * The features provided by the application's GUI.
 */
public interface Features {

  /**
   * Load an image into the application.
   */
  void loadImage();

  /**
   * Save an image onto local device.
   */
  void saveImage();

  /**
   * Visualize the red component of an image.
   */
  void visualizeRed();

  /**
   * Visualize the green component of an image.
   */
  void visualizeGreen();

  /**
   * Visualize the blue component of an image.
   */
  void visualizeBlue();

  /**
   * Flip an image horizontally.
   */
  void flipHorizontal();

  /**
   * Flip an image vertically.
   */
  void flipVertical();

  /**
   * Blur an image.
   */
  void blurImage();

  /**
   * Sharpen an image.
   */
  void sharpenImage();

  /**
   * Convert an image to greyscale.
   */
  void convertGreyscale();

  /**
   * Convert an image to sepia.
   */
  void convertSepia();

  /**
   * Run levels-adjustment preview on an image.
   */
  void runLevelsAdjustment();

  /**
   * Run compression preview on an image.
   */
  void runCompression();

  /**
   * Run color-correction preview on an image.
   */
  void runColorCorrection();

  /**
   * Toggle split view.
   */
  void toggleSplitView();

  /**
   * Exits program.
   */
  void exitProgram();
}

package controller.gui;

import java.io.IOException;

/**
 * The features provided by the application's GUI.
 */
public interface Features {

  /**
   * Load an image into the application.
   */
  void loadImage(String filePath, String imageName);

  /**
   * Save an image onto local device.
   *
   * @param savePath String, path for image to be saved.
   */
  void saveImage(String savePath) throws IOException;

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
   * Run levels adjustment on an image.
   *
   * @param b int, black value for adjustment
   * @param m int, mid value for adjustment
   * @param w int, white value for adjustment
   */
  void runLevelsAdjustment(int b, int m, int w);

  /**
   * Run brighten on an image.
   */
  void brighten();

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
   *
   * @param op           String, name of operation to be performed in split
   * @param pct          int, split percentage
   * @param levelAdjArgs int, variable argument that is passed when levels-adjust is run in split view
   */
  void toggleSplitView(String op, int pct, int... levelAdjArgs);

  /**
   * Resets the operations performed on an image.
   */
  void clear();

  /**
   * Applies the operation previewed when in split view.
   */
  void applySplitOp();

  /**
   * Exits program.
   */
  void exitProgram();
}

package controller.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import controller.ImageUtil;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.StorageModel;
import model.gui.GUIModel;
import model.utilities.Filter;
import model.utilities.LinearColorTransformation;
import view.gui.GUIView;

public class GUIControllerImpl implements GUIController, Features {

  //application model and view
  GUIModel model;
  GUIView view;

  /**
   * Public constructor for the application's GUI controller.
   *
   * @param model application's model.
   * @param view application's GUI view.
   */
  public GUIControllerImpl(GUIModel model, GUIView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Helper method for converting an Image to a BufferedImage, as used by the GUI view.
   *
   * @param image image to be converted
   */
  private BufferedImage convertToBufferedImage(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();

    //create a BufferedImage with the same dimensions as the image
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //get the graphics object from the buffered image
    Graphics g = bufferedImage.getGraphics();

    if (g != null) {
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Pixel pixel = image.getPixel(x, y);
          Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
          bufferedImage.setRGB(x, y, color.getRGB());
        }
      }
    }

    return bufferedImage;
  }

  /**
   * Helper method to apply non-persistent edits to the current image for display purposes.
   * Basically slider support.
   *
   * @param currentImage current image, with persistent edits
   * @return the image to be displayed to the user
   */
  private Image renderNonPersistentChanges(Image currentImage) {

    //fetch slider values
    Map<String, Double> sliderValues = view.getSliderValues();
    int compressionRatio = sliderValues.get("compression-ratio").intValue();
    int brightenIncrement = sliderValues.get("brighten-increment").intValue();

    //render final image
    Image displayImage = currentImage.compressImage(compressionRatio, currentImage.getName());
    displayImage = displayImage.brighten(brightenIncrement, currentImage.getName());

    return displayImage;
  }

  @Override
  public void loadImage(String filePath, String imageName) {
    Image baseImage = ImageUtil.loadImage(filePath, imageName);
    model.setBaseImage(baseImage);
    view.displayImage(convertToBufferedImage(model.getDisplayImage()),
            model.getDisplayImage().getName());
  }

  @Override
  public void saveImage() {

  }

  @Override
  public void visualizeRed() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getRedComponent(currentImage.getName()
              + " -> red-component");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());
    }
  }

  @Override
  public void visualizeGreen() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getGreenComponent(currentImage.getName()
              + " -> green-component");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void visualizeBlue() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getBlueComponent(currentImage.getName()
              + " -> blue-component");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void flipHorizontal() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getHorizontalFlip(currentImage.getName()
              + " -> horizontal-flip");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void flipVertical() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getVerticalFlip(currentImage.getName()
              + " -> vertical-flip");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void blurImage() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("blur"),
              currentImage.getName() + " -> blur");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void sharpenImage() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("sharpen"),
              currentImage.getName() + " -> sharpen");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void convertGreyscale() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.getLumaComponent(currentImage.getName() + " -> greyscale");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void convertSepia() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      LinearColorTransformation lct = new LinearColorTransformation();
      currentImage = currentImage.applyLinearColorTransformation(lct.getLinearTransformation("sepia"),
              currentImage.getName() + " -> sepia");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runLevelsAdjustment() {

  }

  @Override
  public void brighten() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runCompression() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runColorCorrection() {
    Image currentImage = model.getCurrentImage();
    if(currentImage != null) {
      currentImage = currentImage.colorCorrectImage(currentImage.getName()
              + " -> color-correct");

      model.setCurrentImage(currentImage);
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void toggleSplitView() {

  }

  @Override
  public void clear(){
    model.setCurrentImage(model.getBaseImage());
    Image currentImage = model.getCurrentImage();
    view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
  }

  @Override
  public void exitProgram() {

  }
}

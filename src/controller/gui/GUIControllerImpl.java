package controller.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import controller.ImageUtil;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.StorageModel;
import model.utilities.Filter;
import model.utilities.LinearColorTransformation;
import view.gui.GUIView;

public class GUIControllerImpl implements GUIController, Features {

  //application model and view
  StorageModel model;
  GUIView view;

  //the base image that has been loaded into the application. Can be null if no image loaded.
  private Image baseImage = null;

  //the base image, with modifications applied. This is the "preview" image.
  private Image currentImage = baseImage;

  /**
   * Public constructor for the application's GUI controller.
   *
   * @param model application's model.
   * @param view application's GUI view.
   */
  public GUIControllerImpl(StorageModel model, GUIView view) {
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

  @Override
  public void loadImage(String filePath, String imageName) {
    //FIXME will not work on windows?
    model.loadImage(filePath, imageName);
    baseImage = model.getImage(imageName);
    currentImage = baseImage;
    view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
  }

  @Override
  public void saveImage() {

  }

  @Override
  public void visualizeRed() {
    if(currentImage != null) {
      currentImage = currentImage.getRedComponent(currentImage.getName()
              + " -> red-component");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void visualizeGreen() {
    if(currentImage != null) {
      currentImage = currentImage.getGreenComponent(currentImage.getName()
              + " -> green-component");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void visualizeBlue() {
    if(currentImage != null) {
      currentImage = currentImage.getBlueComponent(currentImage.getName()
              + " -> blue-component");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void flipHorizontal() {
    if(currentImage != null) {
      currentImage = currentImage.getHorizontalFlip(currentImage.getName()
              + " -> horizontal-flip");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void flipVertical() {
    if(currentImage != null) {
      currentImage = currentImage.getVerticalFlip(currentImage.getName()
              + " -> vertical-flip");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void blurImage() {
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("blur"),
              currentImage.getName() + " -> blur");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void sharpenImage() {
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("sharpen"),
              currentImage.getName() + " -> sharpen");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void convertGreyscale() {
    if(currentImage != null) {
      currentImage = currentImage.getLumaComponent(currentImage.getName() + " -> greyscale");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void convertSepia() {
    if(currentImage != null) {
      LinearColorTransformation lct = new LinearColorTransformation();
      currentImage = currentImage.applyLinearColorTransformation(lct.getLinearTransformation("sepia"),
              currentImage.getName() + " -> sepia");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void runLevelsAdjustment() {

  }

  @Override
  public void runCompression() {

  }

  @Override
  public void runColorCorrection() {
    if(currentImage != null) {
      currentImage = currentImage.colorCorrectImage(currentImage.getName()
              + " -> color-correct");

      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
    }
  }

  @Override
  public void toggleSplitView() {

  }

  @Override
  public void clear(){
    currentImage = baseImage;
    view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
  }

  @Override
  public void exitProgram() {

  }
}

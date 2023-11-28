package controller.gui;

import model.Image;
import model.ImageStorageModel;
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

  @Override
  public void loadImage() {

  }

  @Override
  public void saveImage() {

  }

  @Override
  public void visualizeRed() {
    if(currentImage != null) {
      currentImage = currentImage.getRedComponent(currentImage.getName()
              + " -> red-component");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void visualizeGreen() {
    if(currentImage != null) {
      currentImage = currentImage.getGreenComponent(currentImage.getName()
              + " -> green-component");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void visualizeBlue() {
    if(currentImage != null) {
      currentImage = currentImage.getBlueComponent(currentImage.getName()
              + " -> blue-component");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void flipHorizontal() {
    if(currentImage != null) {
      currentImage = currentImage.getHorizontalFlip(currentImage.getName()
              + " -> horizontal-flip");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void flipVertical() {
    if(currentImage != null) {
      currentImage = currentImage.getVerticalFlip(currentImage.getName()
              + " -> vertical-flip");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void blurImage() {
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("blur"),
              currentImage.getName() + " -> blur");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void sharpenImage() {
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("sharpen"),
              currentImage.getName() + " -> sharpen");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void convertGreyscale() {
    if(currentImage != null) {
      currentImage = currentImage.getLumaComponent(currentImage.getName() + " -> greyscale");

      view.displayImage(currentImage);
    }
  }

  @Override
  public void convertSepia() {
    if(currentImage != null) {
      LinearColorTransformation lct = new LinearColorTransformation();
      currentImage = currentImage.applyFilter(lct.getLinearTransformation("sepia"),
              currentImage.getName() + " -> sepia");

      view.displayImage(currentImage);
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

      view.displayImage(currentImage);
    }
  }

  @Override
  public void toggleSplitView() {

  }

  @Override
  public void exitProgram() {

  }
}

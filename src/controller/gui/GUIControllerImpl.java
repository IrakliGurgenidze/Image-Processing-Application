package controller.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.imageio.ImageIO;

import controller.ImageUtil;
import controller.SplitUtil;
import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;
import model.StorageModel;
import model.utilities.Filter;
import model.utilities.LinearColorTransformation;
import view.gui.GUIView;

public class GUIControllerImpl implements GUIController, Features {

  //application model and view
  StorageModel model;
  GUIView view;

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
    model.insertImage(baseImage, "base");
    model.insertImage(baseImage, "current");
    view.displayImage(convertToBufferedImage(model.getImage("current")),
            model.getImage("current").getName());
  }

  @Override
  public void saveImage(String savePath) throws IOException {
    Image image = model.getImage("current");
    image = renderNonPersistentChanges(image);
    ImageUtil.saveImage(image, savePath);
  }

  @Override
  public void visualizeRed() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getRedComponent(currentImage.getName()
              + " -> red-component");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());
    }
  }

  @Override
  public void visualizeGreen() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getGreenComponent(currentImage.getName()
              + " -> green-component");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void visualizeBlue() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getBlueComponent(currentImage.getName()
              + " -> blue-component");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void flipHorizontal() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getHorizontalFlip(currentImage.getName()
              + " -> horizontal-flip");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void flipVertical() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getVerticalFlip(currentImage.getName()
              + " -> vertical-flip");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void blurImage() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("blur"),
              currentImage.getName() + " -> blur");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void sharpenImage() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      Filter filters = new Filter();
      currentImage = currentImage.applyFilter(filters.getFilter("sharpen"),
              currentImage.getName() + " -> sharpen");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void convertGreyscale() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.getLumaComponent(currentImage.getName() + " -> greyscale");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void convertSepia() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      LinearColorTransformation lct = new LinearColorTransformation();
      currentImage = currentImage.applyLinearColorTransformation(lct.getLinearTransformation("sepia"),
              currentImage.getName() + " -> sepia");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runLevelsAdjustment(int b, int m, int w) {
    Image currentImage = model.getImage("current");
    if (currentImage != null) {
      currentImage = currentImage.adjustLevels(currentImage.getName(), b, m, w);
      view.displayImage(convertToBufferedImage(currentImage), currentImage.getName()
              + " -> levels-adjust");
    }
  }

  @Override
  public void brighten() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runCompression() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void runColorCorrection() {
    Image currentImage = model.getImage("current");
    if(currentImage != null) {
      currentImage = currentImage.colorCorrectImage(currentImage.getName()
              + " -> color-correct");

      model.insertImage(currentImage, "current");
      view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
              currentImage.getName());    }
  }

  @Override
  public void toggleSplitView(String op, int pct) {
    Image currentImage = model.getImage("current");
    Filter filter = new Filter();
    switch(op){
      case "reset":
        model.insertImage(model.getImage("current"), "split");
        view.displayImage(convertToBufferedImage(renderNonPersistentChanges(currentImage)),
                currentImage.getName());

      case "blur":
        Image blurredImage = currentImage.applyFilter(filter.getFilter("blur"), currentImage.getName());
        Image blurImage = SplitUtil.splitImage(currentImage, blurredImage, pct, blurredImage.getName());
        model.insertImage(blurImage, "split");
        view.displayImage(convertToBufferedImage(blurImage), blurImage.getName());
        break;
      default:
        //no default case
        break;
    }
  }

  @Override
  public void clear(){
    model.insertImage(model.getImage("base"), "current");
    Image currentImage = model.getImage("current");
    view.displayImage(convertToBufferedImage(currentImage), currentImage.getName());
  }

  @Override
  public void applySplitOp() {
    model.insertImage(model.getImage("split"), "current");
    view.displayImage(convertToBufferedImage(model.getImage("current")),
            model.getImage("current").getName());
  }

  @Override
  public void exitProgram() {

  }
}

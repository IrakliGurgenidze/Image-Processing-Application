package controllertests.featuretests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.SplitUtil;
import controller.gui.Features;
import model.Image;
import model.Pixel;
import model.SimpleImage;
import model.utilities.Filter;
import model.utilities.LinearColorTransformation;
import view.gui.GUIView;

import static controllertests.commandtests.AbstractCommandTest.isEqual;
import static controllertests.commandtests.AbstractCommandTest.setWd;
import static org.junit.Assert.assertTrue;

/**
 * Implementation of the FeatureTestView interface. A mock view, basically.
 */
public class FeatureTestViewImpl implements GUIView {
  private final Image baseImage;
  //hard coded values
  private final int BRIGHTEN_INCREMENT = 0;
  private final int COMPRESSION_RATIO = 0;
  private final int B_VALUE = 5;
  private final int M_VALUE = 50;
  private final int W_VALUE = 200;
  private final int SPLIT_PERCENTAGE = 50;
  private final Map<String, Image> resultImages = new HashMap<String, Image>();
  private Features features;

  /**
   * Public constructor for this mock View.
   * Stores a Feature object internally to be called via specific keywords.
   *
   * @param baseImage the base image of this test class
   */
  public FeatureTestViewImpl(Image baseImage) {
    this.baseImage = baseImage;
  }

  //helper method to populate resultImages with expected results of various operations
  protected void populateResultImages() {
    Filter filters = new Filter();
    LinearColorTransformation lct = new LinearColorTransformation();

    resultImages.clear();

    //calculate resultImages, expected results of various tests
    resultImages.put("base", renderNonPersistentChanges(baseImage));
    resultImages.put("current", renderNonPersistentChanges(baseImage));


    //load()
    resultImages.put("loaded-base", renderNonPersistentChanges(baseImage));

    //save()
    resultImages.put("loaded-save",
            renderNonPersistentChanges(renderNonPersistentChanges(baseImage)));

    //getRedComponent()
    Image visualizeRed = baseImage.getRedComponent(baseImage.getName()
            + " -> red-component");
    resultImages.put(visualizeRed.getName(), renderNonPersistentChanges(visualizeRed));

    //getGreenComponent()
    Image visualizeGreen = baseImage.getGreenComponent(baseImage.getName()
            + " -> green-component");
    resultImages.put(visualizeGreen.getName(), renderNonPersistentChanges(visualizeGreen));

    //getRedComponent()
    Image visualizeBlue = baseImage.getBlueComponent(baseImage.getName()
            + " -> blue-component");
    resultImages.put(visualizeBlue.getName(), renderNonPersistentChanges(visualizeBlue));

    //getHorizontalFlip()
    Image horizontalFlip = baseImage.getHorizontalFlip(baseImage.getName()
            + " -> horizontal-flip");
    resultImages.put(horizontalFlip.getName(), renderNonPersistentChanges(horizontalFlip));

    //getVerticalFlip()
    Image verticalFlip = baseImage.getVerticalFlip(baseImage.getName()
            + " -> vertical-flip");
    resultImages.put(verticalFlip.getName(), renderNonPersistentChanges(verticalFlip));

    //getFilter("blur")
    Image blur = baseImage.applyFilter(filters.getFilter("blur"),
            baseImage.getName() + " -> blur");
    resultImages.put(blur.getName(), renderNonPersistentChanges(blur));

    //getFilter("sharpen")
    Image sharpen = baseImage.applyFilter(filters.getFilter("sharpen"),
            baseImage.getName() + " -> sharpen");
    resultImages.put(sharpen.getName(), renderNonPersistentChanges(sharpen));

    //getLumaComponent()
    Image greyscale = baseImage.getLumaComponent(baseImage.getName()
            + " -> greyscale");
    resultImages.put(greyscale.getName(), renderNonPersistentChanges(greyscale));

    //applyLinearColorTransformation("sepia")
    Image sepia = baseImage.applyLinearColorTransformation(
            lct.getLinearTransformation("sepia"),
            baseImage.getName() + " -> sepia");
    resultImages.put(sepia.getName(), renderNonPersistentChanges(sepia));

    //adjustLevels()
    Image adjustLevels = baseImage.adjustLevels(baseImage.getName()
                    + " -> levels-adjust"
                    + B_VALUE + "/" + M_VALUE + "/" + W_VALUE,
            B_VALUE, M_VALUE, W_VALUE);
    resultImages.put("run-levels-adjustment", renderNonPersistentChanges(adjustLevels));

    //brighten()
    Image brighten = baseImage.brighten(BRIGHTEN_INCREMENT, baseImage.getName());
    resultImages.put(brighten.getName(), renderNonPersistentChanges(brighten));

    //compressImage()
    Image compress = baseImage.compressImage(COMPRESSION_RATIO, baseImage.getName());
    resultImages.put(compress.getName(), renderNonPersistentChanges(compress));

    //colorCorrectImage()
    Image colorCorrect = baseImage.colorCorrectImage(baseImage.getName());
    resultImages.put(colorCorrect.getName() + " -> color-correct",
            renderNonPersistentChanges(colorCorrect));

    //split blur
    Image splitBlurImage = baseImage.applyFilter(filters.getFilter("blur"),
            baseImage.getName() + " -> blur (split)");
    Image splitBlur = SplitUtil.splitImage(baseImage, splitBlurImage, SPLIT_PERCENTAGE,
            splitBlurImage.getName());
    resultImages.put(splitBlur.getName(), renderNonPersistentChanges(splitBlur));

    //sharpen blur
    Image splitSharpenImage = baseImage.applyFilter(filters.getFilter("sharpen"),
            baseImage.getName() + " -> sharpen (split)");
    Image splitSharpen = SplitUtil.splitImage(baseImage, splitSharpenImage, SPLIT_PERCENTAGE,
            splitSharpenImage.getName());
    resultImages.put(splitSharpen.getName(), renderNonPersistentChanges(splitSharpen));

    //sepia blur
    Image splitSepiaImage = baseImage.applyLinearColorTransformation(
            lct.getLinearTransformation("sepia"),
            baseImage.getName() + " -> sepia (split)");
    Image splitSepia = SplitUtil.splitImage(baseImage, splitSepiaImage, SPLIT_PERCENTAGE,
            splitSepiaImage.getName());
    resultImages.put(splitSepia.getName(), renderNonPersistentChanges(splitSepia));

    //greyscale blur
    Image splitGreyscaleImage = baseImage.getLumaComponent(baseImage.getName()
            + " -> greyscale (split)");
    Image splitGreyscale = SplitUtil.splitImage(baseImage, splitGreyscaleImage, SPLIT_PERCENTAGE,
            splitGreyscaleImage.getName());
    resultImages.put(splitGreyscale.getName(), renderNonPersistentChanges(splitGreyscale));

    //color correct blur
    Image splitColorCorrectImage = baseImage.colorCorrectImage(baseImage.getName()
            + " -> color-correct (split)");
    Image splitColorCorrect = SplitUtil.splitImage(baseImage, splitColorCorrectImage,
            SPLIT_PERCENTAGE,
            splitColorCorrectImage.getName());
    resultImages.put(splitColorCorrect.getName(), renderNonPersistentChanges(splitColorCorrect));

    //levels adjust blur
    Image splitLevelsAdjustImage = baseImage.adjustLevels(baseImage.getName()
                    + " -> levels-adjust (split)",
            B_VALUE, M_VALUE, W_VALUE);
    Image splitLevelsAdjust = SplitUtil.splitImage(baseImage, splitLevelsAdjustImage,
            SPLIT_PERCENTAGE,
            splitLevelsAdjustImage.getName());
    resultImages.put(splitLevelsAdjust.getName(), renderNonPersistentChanges(splitLevelsAdjust));

  }

  /**
   * Calls a specific feature method by name.
   *
   * @param featureName the name/identifier of the feature method to be called
   */
  public void callFeature(String featureName) throws IOException {
    switch (featureName) {
      case "load":
        //path to the manhattan picture
        String loadImagePath = setWd()
                + "sample_images"
                + File.separator
                + "manhattan-small.png";

        features.loadImage(loadImagePath, "base");
        break;

      case "save":
        //path to save the manhattan picture to
        String saveImagePath = setWd()
                + "result_images"
                + File.separator
                + "manhattan-small-GUI.png";
        features.saveImage(saveImagePath);
        features.loadImage(saveImagePath, "loaded-save");

        break;

      case "clear":
        features.clear();
        break;

      case "visualize-red":
        features.visualizeRed();
        features.clear();
        break;

      case "visualize-green":
        features.visualizeGreen();
        features.clear();
        break;

      case "visualize-blue":
        features.visualizeBlue();
        features.clear();
        break;

      case "horizontal-flip":
        features.flipHorizontal();
        features.clear();
        break;

      case "vertical-flip":
        features.flipVertical();
        features.clear();
        break;

      case "blur":
        features.blurImage();
        features.clear();
        break;

      case "sharpen":
        features.sharpenImage();
        features.clear();
        break;

      case "convert-greyscale":
        features.convertGreyscale();
        features.clear();
        break;

      case "convert-sepia":
        features.convertSepia();
        features.clear();
        break;

      case "run-levels-adjustment":
        features.runLevelsAdjustment(B_VALUE, M_VALUE, W_VALUE);
        features.clear();
        break;

      case "brighten":
        features.brighten();
        features.clear();
        break;

      case "run-compression":
        features.runCompression();
        features.clear();
        break;

      case "run-color-correction":
        features.runColorCorrection();
        features.clear();
        break;

      case "toggle-split-view":
        //test all split view options
        features.toggleSplitView("reset", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("blur", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("sharpen", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("convert-sepia", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("convert-greyscale", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("run-levels-adjustment", SPLIT_PERCENTAGE);
        features.clear();

        features.toggleSplitView("run-color-correction", SPLIT_PERCENTAGE);
        features.clear();
        break;

      case "apply-split-op":
        features.toggleSplitView("blur", SPLIT_PERCENTAGE);
        features.applySplitOp();
        features.clear();
        break;

      default:
    }

  }

  //we don't need this, but it must be there due to the shared interface
  @Override
  public void addFeatures(Features features) {
    this.features = features;
    this.populateResultImages();
  }

  /**
   * Returns the name of displayImage when called and verifies result.
   *
   * @param displayImage image to be displayed. Can be null.
   * @param histogram    the histogram to be displayed on screen
   * @param displayName  String, name of image including its changelog
   */
  @Override
  public void displayImage(BufferedImage displayImage, BufferedImage histogram,
                           String displayName) {
    Image convertedResult = convertToImage(displayImage, displayName);
    Image convertedHistogram = convertToImage(histogram, "histogram");

    //assert that result is the same as expected
    assertTrue(isEqual(resultImages.get(displayName), convertedResult));
  }

  /**
   * This method returns the current values of specific UI elements that reflect non-persistent
   * user input.
   *
   * @return returns a map of current UI element values, referenced by their UI names.
   */
  @Override
  public Map<String, Double> getSliderValues() {
    Map<String, Double> sliderValues = new HashMap<>();

    //insert relevant UI elements
    sliderValues.put("compression-ratio", (double) COMPRESSION_RATIO);
    sliderValues.put("brighten-increment", (double) BRIGHTEN_INCREMENT);
    return sliderValues;
  }

  //helper method to render certain changes
  private Image renderNonPersistentChanges(Image currentImage) {

    //render final image
    Image displayImage = currentImage.compressImage(COMPRESSION_RATIO, currentImage.getName());
    displayImage = displayImage.brighten(BRIGHTEN_INCREMENT, currentImage.getName());

    return displayImage;
  }

  //helper method to convert a bufferedImage back into an Image
  private Image convertToImage(BufferedImage bufferedImage, String destName) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    int red;
    int green;
    int blue;

    Pixel[][] image = new Pixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = new Color(bufferedImage.getRGB(x, y));
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        Pixel pixel = new Pixel(red, green, blue);
        image[y][x] = pixel;
      }
    }
    return new SimpleImage(destName, image);
  }


}

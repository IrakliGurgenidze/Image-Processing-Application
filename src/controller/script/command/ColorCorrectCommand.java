package controller.script.command;

import controller.SplitUtil;
import model.Image;
import model.StorageModel;


/**
 * This command color corrects an image to align all histogram peaks.
 */
public class ColorCorrectCommand implements CommandController {
  private final StorageModel imageStorageModel;

  private boolean split;
  private int splitPcnt;

  /**
   * This is the constructor to initialize the command.
   *
   * @param imageStorageModel state of image database
   */
  public ColorCorrectCommand(StorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
    this.split = false;
    this.splitPcnt = 0;
  }

  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    checkSplit(args);

    String sourceImageName = args[1];
    String destImageName = args[2];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }

    Image colorCorrected = sourceImage.colorCorrectImage(destImageName);
    if (split) {
      colorCorrected = SplitUtil.splitImage(sourceImage, colorCorrected, splitPcnt, destImageName);
    }
    imageStorageModel.insertImage(colorCorrected);
    return "Color correction operation successful.";
  }

  @Override
  public String getUsage() {
    return "color-correct image-name dest-image-name: corrects the colors of an image by "
            + "aligning the meaningful peaks of its RGB histogram. Referred to henceforth as "
            + "dest-image-name. split p: may be added as two additional parameters if split "
            + "preview of operation is desired.";
  }

  //check for split keyword
  private void checkSplit(String[] args) throws IllegalArgumentException {
    if (args.length == 5 && args[3].equals("split")) {
      split = true;
      try {
        splitPcnt = Integer.parseInt(args[4]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }
      if (splitPcnt < 1 || splitPcnt > 99) {
        throw new IllegalArgumentException("Split percentage must be an integer between 0-100");
      }

    } else if (args.length != 3) {
      throw new IllegalArgumentException("Invalid input, looking for 3 or 5 arguments but "
              + "only found " + args.length + ". Correct usage: " + getUsage());
    }
  }
}

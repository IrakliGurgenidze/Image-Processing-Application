package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

/**
 * This command adjusts the color levels of an image.
 */
public class LevelsAdjustCommand implements CommandController{
  private final ImageStorageModel imageStorageModel;

  /**
   * This is the constructor to initialize the command.
   *
   * @param imageStorageModel state of image database
   */
  public LevelsAdjustCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }
  @Override
  public String execute(String[] args) throws IllegalArgumentException {
    if(args.length != 6){
      throw new IllegalArgumentException("Invalid input, looking for 6 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }
    int b;
    int m;
    int w;

    try {
       b = Integer.parseInt(args[1]);
       m = Integer.parseInt(args[2]);
       w = Integer.parseInt(args[3]);
    }catch(NumberFormatException e){
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    if(w < m || m < b){
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    if(b < 0 || w > 255){
      throw new IllegalArgumentException("b,m,w must be integers between 0-255 ascending in " +
              "listed order");
    }

    String sourceImageName = args[4];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);
    if(sourceImage == null){
      throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
              + " not found.");
    }
    String destImageName = args[5];
    Image adjustedImage = levelsAdjust(sourceImage, b, m, w, destImageName);
    imageStorageModel.insertImage(adjustedImage);
    return "Levels adjustment operation successful.";
  }

  @Override
  public String getUsage() {
    return "levels-adjust b m w image-name dest-image-name: where b, m and w are the three " +
            "relevant black, mid and white values respectively. These values should be ascending " +
            "in that order, and should be within 0 and 255 for this command to work correctly.";
  }

  private Image levelsAdjust(Image image, int b, int m, int w, String destImageName){
    int height = image.getHeight();
    int width = image.getWidth();
    Image adjustedImage = new SimpleImage(width, height, destImageName);

    int[] coefficients = adjQuadratic(b,m,w);

    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        Pixel currPixel = image.getPixel(j,i);
        int newRedVal = solveQuadratic(coefficients, currPixel.getRed());
        int newGreenVal = solveQuadratic(coefficients, currPixel.getGreen());
        int newBlueVal = solveQuadratic(coefficients, currPixel.getBlue());
        adjustedImage.setPixel(j,i,new Pixel(newRedVal, newGreenVal, newBlueVal));
      }
    }
    return adjustedImage;
  }

  private int solveQuadratic(int[] coefs, int x){
    //solves y = ax^2 + bx + c
    int a = coefs[0];
    int b = coefs[1];
    int c = coefs[2];

    return (a*(x*x) + b*x + c*x);
  }

  private int[] adjQuadratic(int b, int m, int w){
    //calculations to find coefficients
    double A = (b*b)*(m-w) - b*((m*m)-(w*w)) + w*(m*m) - m*(w*w);
    double Aa = -b*(128-255) + 128*w - 255*m;
    double Ab = (b*b)*(128-255) + 255*(m*m) - 128*(w*w);
    double Ac = (b*b)*(255*m - 128*w) - b*(255*(m*m)-128*(w*w));

    double coefA = Aa/A;
    double coefB = Ab/A;
    double coefC = Ac/A;

    //clamping the coefficients between 0-255
    coefA = Math.max(0, Math.min(255, coefA));
    coefB = Math.max(0, Math.min(255, coefB));
    coefC = Math.max(0, Math.min(255, coefC));

    return new int[]{(int)coefA,(int)coefB,(int)coefC};
  }
}

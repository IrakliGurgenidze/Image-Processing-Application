package model.utilities;
import model.Image;
import model.Pixel;
import model.SimpleImage;

import java.util.Arrays;

/**
 * This class contains utility methods for the LevelsAdjustCommand.
 */
public class LevelsAdjustUtil {

  /**
   * Method to perform a get a level adjusted image.
   * @param image Image, the image to be adjusted
   * @param b int, the black value of the adjustment
   * @param m int, the mid value of the adjustment
   * @param w int, the white value of the adjustment
   * @param destImageName String, the name of the adjusted image
   * @return the new adjusted image
   */
  public static Image levelsAdjust(Image image, int b, int m, int w, String destImageName) {
    int height = image.getHeight();
    int width = image.getWidth();

    Pixel[][] adjustedImage = new Pixel[height][width];

    double[] coefficients = adjQuadratic(b, m, w);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = image.getPixel(j, i);
        int newRedVal = solveQuadratic(coefficients, currPixel.getRed());
        int newGreenVal = solveQuadratic(coefficients, currPixel.getGreen());
        int newBlueVal = solveQuadratic(coefficients, currPixel.getBlue());
        adjustedImage[i][j] = new Pixel(newRedVal, newGreenVal, newBlueVal);
      }
    }
    return new SimpleImage(destImageName, adjustedImage);
  }

  private static int solveQuadratic(double[] coefs, int x) {
    //solves y = ax^2 + bx + c
    double a = coefs[0];
    double b = coefs[1];
    double c = coefs[2];
    return (int)Math.round((a * x * x + b * x + c));
  }

  //calculate quadratic coefficients
  private static double[] adjQuadratic(int b, int m, int w) {
    //calculations to find coefficients
    double A = (b * b) * (m - w)
            - b * ((m * m) - (w * w))
            + w * (m * m)
            - m * (w * w);

    double Aa = -b * (128 - 255)
            + 128 * w
            - 255 * m;

    double Ab = (b * b) * (128 - 255)
            + 255 * (m * m)
            - 128 * (w * w);

    double Ac = (b * b) * (255 * m - 128 * w)
            - b * (255 * (m * m)
            - 128 * (w * w));

    double coefA = Aa / A;
    double coefB = Ab / A;
    double coefC = Ac / A;

    return new double[]{coefA, coefB, coefC};
  }


}

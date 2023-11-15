package controller.command;

import java.util.Arrays;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

import java.util.Set;
import java.util.HashSet;

/**
 * This command compresses an image to facilitate more efficient storage.
 */
public class CompressCommand implements CommandController {
  private final ImageStorageModel imageStorageModel;

  /**
   * This constructor initializes the compress command.
   *
   * @param imageStorageModel state of image database
   */
  public CompressCommand(ImageStorageModel imageStorageModel) {
    this.imageStorageModel = imageStorageModel;
  }

  @Override
  public String execute(String[] args) {

    //command takes 4 args: compress, percentage (an int), image-name, dest-image-name
    if (args.length != 4) {
      throw new IllegalArgumentException("Invalid input, looking for 4 arguments but only found "
              + args.length + ". Correct usage: " + getUsage());
    }

    //extract compression ratio
    int compression;
    try {
      compression = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input. Compression value invalid. "
              + "Please enter an integer 0-100.");
    }

    //extract image names and fetch source image
    String sourceImageName = args[2];
    String destImageName = args[3];
    Image sourceImage = imageStorageModel.getImage(sourceImageName);

    //invalid image name provided, image does not exist or hasn't been loaded yet
    if (sourceImage == null) {
      throw new IllegalArgumentException("Invalid input. Source image does not exist in database.");
    }

    //compute and store resulting image
    Image destImage = compress(compression, sourceImage, destImageName);
    imageStorageModel.insertImage(destImage);

    //return status
    return "Compression of " + sourceImageName + "completed. Saved as: " + destImageName;
  }

  /**
   * Private helper function to "drive" image compression process.
   *
   * @param compressionRatio compression ratio, or the amount by which to decrease data size
   * @param source           source image
   * @param destImageName    name of resulting image
   * @return an image compressed to the specified ratio
   */
  private Image compress(int compressionRatio, Image source, String destImageName) {
    int height = source.getHeight();
    int width = source.getWidth();

    //get padding geometry, if necessary
    int maxDimension = Math.max(height, width);

    //image is not a power of two
    if (!((maxDimension > 0) && ((maxDimension & (maxDimension - 1)) == 0))) {

      //find the position of the most significant bit (MSB) and left shift by 1
      int msbPosition = 0;
      int temp = maxDimension;
      while (temp > 0) {
        temp = temp >> 1;
        msbPosition++;
      }

      maxDimension = 1 << msbPosition;

      System.out.println(maxDimension);
    }

    //get padded RGB channels, indexing order is [0]->R, [1]->G, [2]->B
    double[][][] paddedChannels = new double[3][maxDimension][maxDimension];

    //copy channel values into the new padded structure.
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = source.getPixel(x, y);
        paddedChannels[0][x][y] = currPixel.getRed();
        paddedChannels[1][x][y] = currPixel.getGreen();
        paddedChannels[2][x][y] = currPixel.getBlue();
      }
    }

    //apply transformation to each channel
    for(double[][] channel : paddedChannels) {
      haar(channel);
    }

    //filter out values based on compression ratio
    filterValues(paddedChannels, compressionRatio);

    //invert transformation
    for(double[][] channel : paddedChannels) {
      inverseHaar(channel);
    }

    //convert back into an image
    Image compressedImage = new SimpleImage(width, height, destImageName);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel currPixel = new Pixel(0);
        currPixel.setRed((int) Math.round(paddedChannels[0][x][y]));
        currPixel.setGreen((int) Math.round(paddedChannels[1][x][y]));
        currPixel.setBlue((int) Math.round(paddedChannels[2][x][y]));
        compressedImage.setPixel(x, y, currPixel);
      }
    }

    return compressedImage;
  }

  /*
    This method applies the Haar Wavelet Transform to a 2D array of values.
    It is assumed that the array has already been padded to the correct size.
  */
  private void haar(double[][] paddedChannel) {
    if (paddedChannel.length != paddedChannel[0].length) {
      throw new IllegalStateException("Haar Wavelet Transform cannot be applied to non-square"
              + " arrays.");
    }

    //bound of haar transformation
    int c = paddedChannel.length;
    while (c > 1) {

      //transform each row before the bound
      for (int row = 0; row < c; row++) {
        double[] paddedRowSubset = Arrays.copyOf(paddedChannel[row], c);
        double[] rowTransformResult = transform(paddedRowSubset);
        System.arraycopy(rowTransformResult, 0, paddedChannel[row],
                0, rowTransformResult.length);
      }

      //transform each column before the bound
      for (int col = 0; col < c; col++) {
        double[] paddedColumnSubset = new double[c];

        //populate column subset
        for (int row = 0; row < c; row++) {
          paddedColumnSubset[row] = paddedChannel[row][col];
        }

        //calculate transformed column and move results back to source array
        double[] columnTransformResult = transform(paddedColumnSubset);
        for (int row = 0; row < c; row++) {
          paddedChannel[row][col] = columnTransformResult[row];
        }
      }

      c = c / 2;
    }
  }

  /*
   This method performs the inverse of a Haar transform on an already
   transformed matrix of values.
  */
  public void inverseHaar(double[][] transformedChannel) {
    if (transformedChannel.length != transformedChannel[0].length) {
      throw new IllegalStateException("Haar Wavelet Transform cannot be applied to non-square"
              + " arrays.");
    }

    //length and bound
    int n = transformedChannel.length;
    int c = 2;
    while(c <= n) {

      //inverse columns
      for (int col = 0; col < c; col++) {
        double[] transformedColumnSubset = new double[c];

        //populate column subset
        for (int row = 0; row < c; row++) {
          transformedColumnSubset[row] = transformedChannel[row][col];
        }

        //calculate inverted column and move results back to source array
        double[] columnInverseResult = invert(transformedColumnSubset);
        for (int row = 0; row < c; row++) {
          transformedChannel[row][col] = columnInverseResult[row];
        }
      }

      //inverse rows
      for (int row = 0; row < c; row++) {
        double[] transformedRowSubset = Arrays.copyOf(transformedChannel[row], c);
        double[] rowInverseResult = invert(transformedRowSubset);
        System.arraycopy(rowInverseResult, 0, transformedChannel[row],
                0, rowInverseResult.length);
      }

      c = c*2;
    }
  }

  /*
   This method "transforms" a 1-dimensional sequence of values via the
   Haar Wavelet Transform. We are assuming that padding has already taken
   place, and that length is a power of 2.
  */
  private double[] transform(double[] sequence) {

    //number of elements in sequence
    int n = sequence.length;

    //containers for average and difference elements
    double[] result = new double[n];
    int midwayIndex = n / 2;

    //for each consecutive pair of numbers, calculate avg and diff
    for (int i = 0; i < n; i += 2) {
      result[i / 2] = (sequence[i] + sequence[i + 1]) / Math.sqrt(2);
      result[midwayIndex + i / 2] = (sequence[i] - sequence[i + 1]) / Math.sqrt(2);
    }

    return result;
  }

  /*
    This method "inverts" a 1-dimensional sequence of values, reverting the
    transformation applied by its sister function.
  */
  private double[] invert(double[] sequence) {

    //number of elements in sequence
    int n = sequence.length;

    //containers for average and difference elements
    double[] result = new double[n];
    int midwayIndex = n / 2;

    //invert sequence transformation
    for (int i = 0; i < midwayIndex; i++) {
      double a = sequence[i];
      double b = sequence[midwayIndex + i];
      result[i * 2] = (a + b) / Math.sqrt(2);
      result[i * 2 + 1] = (a - b) / Math.sqrt(2);
    }

    return result;
  }


  //helper method to filter out values below compression threshold
  private void filterValues(double[][][] channels, int compressionRatio) {
    Set<Double> uniqueValues = getUniqueValues(channels);
    double threshold = calculateThreshold(uniqueValues, compressionRatio);

    // Apply threshold to all channels
    for (int i = 0; i < 3; i++) {
      channels[i] = applyThreshold(channels[i], threshold);
    }
  }


  //helper function to extract unique values from the channels
  private Set<Double> getUniqueValues(double[][][] channels) {
    Set<Double> uniqueValues = new HashSet<>();
    for (double[][] channel : channels) {
      for (int row = 0; row < channel.length; row++) {
        for (int col = 0; col < channel[row].length; col++) {
          uniqueValues.add(Math.abs(channel[row][col]));
        }
      }
    }

    return uniqueValues;
  }

  //helper function to calculate threshold value
  private double calculateThreshold(Set<Double> uniqueValues, int compressionRatio) {
    //convert set to array for sorting
    Double[] sortedValues = uniqueValues.toArray(new Double[0]);
    Arrays.sort(sortedValues);

    //find the threshold value
    int thresholdIndex = (int) ((compressionRatio / 100.0) * sortedValues.length);
    return sortedValues[thresholdIndex];
  }

  //helper function to apply threshold value
  private double[][] applyThreshold(double[][] channel, double threshold) {
    double[][] result = new double[channel.length][channel[0].length];
    for (int row = 0; row < channel.length; row++) {
      for (int col = 0; col < channel[row].length; col++) {
        if (Math.abs(channel[row][col]) < threshold) {
          result[row][col] = 0.0;
        } else {
          result[row][col] = channel[row][col];
        }
      }
    }

    return result;
  }

  @Override
  public String getUsage() {
    return "compress percentage image-name dest-image-name: compress image by given percentage to "
            + "create a new image, referred to henceforth as the destination image name. "
            + "Percentages between 0 and 100 are considered valid.";
  }


}

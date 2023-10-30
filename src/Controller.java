import java.io.File;

/**
 * This interface outlines generic methods for the controller of an image editing application.
 */
public interface Controller {

  /**
   * Method to take in a single line of input, and split it into related keywords.
   *
   * @param command String, the command to be parsed
   * @return an array of strings containing tokenized command words from the input string
   */
  String[] parseCommand(String command);

  /**
   * Method to run the given command (passed from parseCommand)
   * @param args String[], the command and arguments to be run
   */
  void runCommand(String[] args);

  /**
   * Method to read a script file and process lines with commands
   * @param fileName String, name of the script file
   */
  public void readScriptFile(File fileName);

  /**
   * Load an image from the specified path, and refer to it in the program by the given
   * image name.
   *
   * @param imagePath String, path of image to be loaded
   * @param imageName String, name to store image under
   */
  void load(String imagePath, String imageName);

  /**
   * This method saves an image to a given path.
   * @param imagePath String, path of image
   * @param imageName String, name of image
   */
  public void save(String imagePath, String imageName);

  /**
   * Create an image with only the red-component of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void redComponent(String imageName, String destImageName);

  /**
   * Create an image with only the green-component of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void greenComponent(String imageName, String destImageName);

  /**
   * Create an image with only the blue-component of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void blueComponent(String imageName, String destImageName);

  /**
   * Create an image with only the value of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void valueComponent(String imageName, String destImageName);

  /**
   * Create an image with only the intensity of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void intensityComponent(String imageName, String destImageName);

  /**
   * Create an image with only the luma of the image with the given name, and refer to it
   * henceforth in the program by the given destination name.
   *
   * @param imageName String, name of image to filter
   * @param destImageName String, name of resulting image
   */
  void lumaComponent(String imageName, String destImageName);

  /**
   * Flips an image horizontally to create a new image, henceforth referred to by given destination
   * name.
   * @param imageName String, name of image to flip
   * @param destImageName String, name of resulting image
   */
  public void horizontalFlip(String imageName, String destImageName);

  /**
   * Flips an image vertically to create a new image, henceforth referred to by given destination
   * name.
   * @param imageName String, name of image to flip
   * @param destImageName String, name of resulting image
   */
  public void verticalFlip(String imageName, String destImageName);

  /**
   * Brightens the image by a given increment to create a new image, referred to henceforth by the
   * given destination name
   * @param increment int, the given increment, positive for brightening, negative for darkening
   * @param imageName String, name of image
   * @param destImageName String, name of resulting image
   */
  public void brighten(int increment, String imageName, String destImageName);

  /**
   * Split the given image into three images containing its red, green and blue components
   * respectively.
   *
   * @param imageName String, name of base image
   * @param destRedImageName String, name of image containing red component
   * @param destGreenImageName String, name of image containing green component
   * @param destBlueImageName String, name of image containing blue component
   */
  void rgbSplit(String imageName, String destRedImageName, String destGreenImageName,
                String destBlueImageName);

  /**
   * Combines the three greyscale images into a single image that gets its red,
   * green and blue components from the three images respectively.
   *
   * @param imageName String, name of the image
   * @param redImage String, name of image of red component
   * @param greenImage String, name of image of green component
   * @param blueImage String, name of image of blue component
   */
  public void rgbCombine(String imageName, String redImage, String greenImage, String blueImage);

  /**
   * Blurs the given image and stores the result in another image under the given name.
   *
   * @param imageName String, name of the base image
   * @param destImageName String, name of the blurred image
   */
  void blur(String imageName, String destImageName);

  /**
   * Sharpen the given image and store the result in another image with the given name.
   * @param imageName String, name of the image
   * @param destImageName String, name of the resulting image
   */
  public void sharpen(String imageName, String destImageName);

  /**
   * Produces a sepia-toned version of the given image, and stores the result under the given
   * destination name.
   *
   * @param imageName String, name of the base image
   * @param destImageName String, name of the blurred image
   */
  void sepia(String imageName, String destImageName);

  /**
   * Loads and runs the script commands in the given file
   * @param scriptFile File, the file to be loaded
   */
  public void run(File scriptFile);

}

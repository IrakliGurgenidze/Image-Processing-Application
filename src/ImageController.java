import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageController implements Controller {
  private final ImageModel imageStore;

  public ImageController(ImageModel imageStore) {
    this.imageStore = imageStore;
  }

  @Override
  public String[] parseCommand(String command) {
    return command.split(" ");
  }

  public void runCommand(String[] args) throws IOException {
    Image img;
    switch(args[0])  {
      case "load":
        img = load(args[0], args[1]);
        if(img == null) {
          break;
        }
        imageStore.insertImage(img);
        break;

      case "save":
        img = imageStore.getImage(args[1]);
        save(img, args[1], args[2]);
        break;

      case "brighten":
        break;
    }
  }

  @Override
  public Image load(String fileName, String imageName) {
      try {
        if(fileName.split("\\.")[1].equals("ppm")) {
          return ImageUtil.readColorPPM(fileName, imageName);
        } else {
          return ImageUtil.readColor(fileName, imageName);
        }
      }
      catch(IOException e) {
        System.out.println("File " + fileName + "does not exist.");
      }
      return null;
  }

  @Override
  public void save(Image image, String imagePath, String imageName) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    String ext = imagePath.split("\\.")[1];

    BufferedImage bufferedImage = new BufferedImage(width, height, 1);

    for(int i = 0; i < height; i++){
      for(int j = 0 ; j < width; j++) {
        Pixel pixel = image.getPixel(j, i);
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        bufferedImage.setRGB(j, i, color.getRGB());
      }
    }
    ImageIO.write(bufferedImage, ext, new File(imagePath));
  }

  @Override
  public void redComponent(String imageName, String destImageName) {

  }

  @Override
  public void greenComponent(String imageName, String destImageName) {

  }

  @Override
  public void blueComponent(String imageName, String destImageName) {

  }

  @Override
  public void valueComponent(String imageName, String destImageName) {

  }

  @Override
  public void intensityComponent(String imageName, String destImageName) {

  }

  @Override
  public void lumaComponent(String imageName, String destImageName) {

  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) {

  }

  @Override
  public void verticalFlip(String imageName, String destImageName) {

  }

  @Override
  public void brighten(int increment, String imageName, String destImageName) {

  }

  @Override
  public void rgbSplit(String imageName, String destRedImageName, String destGreenImageName,
                       String destBlueImageName) {

  }

  @Override
  public void rgbCombine(String imageName, String redImage, String greenImage, String blueImage) {

  }

  @Override
  public void blur(String imageName, String destImageName) {

  }

  @Override
  public void sharpen(String imageName, String destImageName) {

  }

  @Override
  public void sepia(String imageName, String destImageName) {

  }

  @Override
  public void run(File scriptFile) {
    try(BufferedReader br = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while((line = br.readLine())!= null && !line.startsWith("#")) {
        String[] args = parseCommand(line);
        runCommand(args);
      }
    } catch(IOException e) {
      System.out.println("Invalid script file.");
    }
  }


}

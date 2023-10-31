import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageController implements Controller {
  private final ImageModel imageStore;
  private final Map<String, CommandController> commands = new HashMap<>();

  public ImageController(ImageModel imageStore) {
    this.imageStore = imageStore;
    commands.put("load", new LoadImageCommand(imageStore));
    commands.put("save", new SaveImageCommand(imageStore));
    commands.put("blur", new FilterCommand(imageStore, "blur"));
    commands.put("sharpen", new FilterCommand(imageStore, "sharpen"));
  }

  @Override
  public String[] parseCommand(String command) {
    return command.split(" ");
  }

  public void runCommand(String[] args) throws IOException {
    CommandController command = commands.get(args[0]);
    command.execute(args);
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
  public void sepia(String imageName, String destImageName) {
    imageStore.applyLinearColorTransformation(imageName, destImageName, "sepia");
  }



}

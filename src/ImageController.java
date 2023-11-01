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
    commands.put("sepia", new LinearColorTransformationCommand(imageStore, "sepia"));
    commands.put("brighten", new BrightenCommand(imageStore));
    commands.put("value-component", new ValueComponentCommand(imageStore));
    commands.put("intensity-component", new IntensityComponentCommand(imageStore));
    commands.put("luma-component", new LumaComponentCommand(imageStore));
    commands.put("red-component", new RedComponentCommand(imageStore));
    commands.put("green-component", new GreenComponentCommand(imageStore));
    commands.put("blue-component", new BlueComponentCommand(imageStore));
    commands.put("horizontal-flip", new HorizontalFlipCommand(imageStore));
    commands.put("vertical-flip", new VerticalFlipCommand(imageStore));
    commands.put("rgb-split", new RGBSplitCommand(imageStore));
    commands.put("rgb-combine", new RGBCombineCommand(imageStore));
  }

  @Override
  public String[] parseCommand(String command) {
    return command.split(" ");
  }

  public void runCommand(String[] args) throws IOException {
    if(args[0].equals("run")) {
      run(new File(args[1]));
    } else {
      try{
        CommandController command = commands.get(args[0]);
        command.execute(args);
      } catch(Exception e){
        System.out.println("Invalid command.");
      }
    }
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

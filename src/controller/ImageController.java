package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.command.BlueComponentCommand;
import controller.command.BrightenCommand;
import controller.command.CommandController;
import controller.command.FilterCommand;
import controller.command.GreenComponentCommand;
import controller.command.HelpCommand;
import controller.command.HorizontalFlipCommand;
import controller.command.IntensityComponentCommand;
import controller.command.LinearColorTransformationCommand;
import controller.command.LoadImageCommand;
import controller.command.LumaComponentCommand;
import controller.command.QuitCommand;
import controller.command.RGBCombineCommand;
import controller.command.RGBSplitCommand;
import controller.command.RedComponentCommand;
import controller.command.SaveImageCommand;
import controller.command.ValueComponentCommand;
import controller.command.VerticalFlipCommand;
import model.ImageStorageModel;

public class ImageController implements Controller {
  private final ImageStorageModel imageStore;
  private final Map<String, CommandController> commands = new HashMap<>();


  public ImageController(ImageStorageModel imageStore) {
    this.imageStore = imageStore;

    commands.put("load", new LoadImageCommand(imageStore));
    commands.put("save", new SaveImageCommand(imageStore));
    commands.put("blur", new FilterCommand(imageStore, "blur"));
    commands.put("sharpen", new FilterCommand(imageStore, "sharpen"));
    commands.put("sepia", new LinearColorTransformationCommand(imageStore,
            "sepia"));
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
    commands.put("help", new HelpCommand(commands));
    commands.put("quit", new QuitCommand());
  }

  @Override
  public String[] parseCommand(String command) {
    // Define a regular expression to split by spaces outside of double quotes
    Pattern pattern = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
    Matcher matcher = pattern.matcher(command);

    // Create a list to store the arguments
    List<String> arguments = new ArrayList<>();

    while (matcher.find()) {
      String arg = matcher.group(1);
      // Remove double quotes if present
      if (arg.startsWith("\"") && arg.endsWith("\"")) {
        arg = arg.substring(1, arg.length() - 1);
      }
      arguments.add(arg);
    }

    return arguments.toArray(new String[0]);
  }

  public String runCommand(String[] args) {
    if (args[0].equals("run")) {
      try {
        File file = new File(args[1]);
        return run(file);
      } catch (RuntimeException e) {
        return "Invalid file.";
      }
    } else {
      CommandController command = commands.get(args[0]);
      if (command == null) {
        return "Invalid command.";
      } else {
        return command.execute(args);
      }
    }
  }

  @Override
  public String run(File scriptFile) {
    try (BufferedReader br = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        if(line.startsWith("#")){
          continue;
        }
        String[] args = parseCommand(line);
        if(args.length > 0){
          runCommand(args);
        }
      }
    } catch (IOException e) {
      return "Invalid script file.";
    }
    return "Running file " + scriptFile.getName() + "...";
  }

}

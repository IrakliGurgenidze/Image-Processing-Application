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
import controller.command.ColorCorrectCommand;
import controller.command.CommandController;
import controller.command.CompressCommand;
import controller.command.FilterCommand;
import controller.command.GreenComponentCommand;
import controller.command.HelpCommand;
import controller.command.HistogramCommand;
import controller.command.HorizontalFlipCommand;
import controller.command.IntensityComponentCommand;
import controller.command.LevelsAdjustCommand;
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
import model.StorageModel;
import view.View;

/**
 * Implementation of Controller interface, controls an image store.
 */
public class ImageController implements Controller {
  private final Map<String, CommandController> commands = new HashMap<>();
  private View view;

  /**
   * Public constructor for an Image Controller. Populates list of commands.
   *
   * @param imageStore state of Image Model
   */
  public ImageController(StorageModel imageStore, View view) {
    this.view = view;

    commands.put("load", new LoadImageCommand(imageStore));
    commands.put("save", new SaveImageCommand(imageStore));
    commands.put("blur", new FilterCommand(imageStore, "blur"));
    commands.put("sharpen", new FilterCommand(imageStore, "sharpen"));
    commands.put("sepia", new LinearColorTransformationCommand(imageStore,
            "sepia"));
    commands.put("greyscale", new LinearColorTransformationCommand(imageStore,
            "greyscale"));
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
    commands.put("compress", new CompressCommand(imageStore));
    commands.put("levels-adjust", new LevelsAdjustCommand(imageStore));
    commands.put("histogram", new HistogramCommand(imageStore));
    commands.put("color-correct", new ColorCorrectCommand(imageStore));
    commands.put("help", new HelpCommand(commands));
    commands.put("quit", new QuitCommand());
  }

  @Override
  public void go() throws IOException {
    view.displayStatus("Image Processing Application by Rocky and Griffin");

    String executionStatus;

    //run until break detected
    while (true) {
      view.displayStatus(">> ");
      String commandLine = view.getInput();
      if(commandLine.isEmpty()) {
        continue;
      }

      String[] parsedCommand = this.parseCommand(commandLine);

      //run command but catch exceptions
      try {
        executionStatus = this.runCommand(parsedCommand);
      } catch (Exception e) {
        executionStatus = e.getMessage();
      }

      //quit program
      if (executionStatus.equals("quit")) {
        view.displayStatus("Quitting program...");
        break;
      }

      view.displayStatus(executionStatus);
    }
  }

  @Override
  public void go(String scriptPath) throws IOException {

    //ensure file exists
    try {
      File scriptFile = new File(scriptPath);
    } catch (RuntimeException e) {
      view.displayStatus("Command line argument file not found.");

      //exit program if file not found
      System.exit(1);
    }

    //run arguments in script file
    String[] runArgs = {"run", scriptPath};

    try {
      view.displayStatus(this.runCommand(runArgs));
    } catch (Exception e) {
      //display exceptions from script file (if any) then quit program
      view.displayStatus(e.getMessage());
      System.exit(1);
    }

    //exit program after script file is run
    view.displayStatus("Run of script file complete.");
    System.exit(0);
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

  @Override
  public String runCommand(String[] args) throws IllegalArgumentException {
    if (args[0].equals("run")) {
      try {
        File file = new File(args[1]);
        return run(file);
      } catch (RuntimeException e) {
        throw new IllegalArgumentException("Invalid file name.");
      }
    }

    CommandController command = commands.get(args[0]);
    if (command == null) {
      throw new IllegalArgumentException("Invalid command. Please type help for a "
              + "list of valid commands");
    }
    return command.execute(args);
  }

  @Override
  public String run(File scriptFile) {
    try (BufferedReader br = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("#")) {
          continue;
        }
        String[] args = parseCommand(line);
        if (args.length > 0) {
          runCommand(args);
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid script file.");
    }
    return "Running file " + scriptFile.getName() + "...";
  }

}

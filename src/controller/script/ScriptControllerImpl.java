package controller.script;

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

import controller.script.command.BlueComponentCommand;
import controller.script.command.BrightenCommand;
import controller.script.command.ColorCorrectCommand;
import controller.script.command.CommandController;
import controller.script.command.CompressCommand;
import controller.script.command.FilterCommand;
import controller.script.command.GreenComponentCommand;
import controller.script.command.HelpCommand;
import controller.script.command.HistogramCommand;
import controller.script.command.HorizontalFlipCommand;
import controller.script.command.IntensityComponentCommand;
import controller.script.command.LevelsAdjustCommand;
import controller.script.command.LinearColorTransformationCommand;
import controller.script.command.LoadImageCommand;
import controller.script.command.LumaComponentCommand;
import controller.script.command.QuitCommand;
import controller.script.command.RGBCombineCommand;
import controller.script.command.RGBSplitCommand;
import controller.script.command.RedComponentCommand;
import controller.script.command.SaveImageCommand;
import controller.script.command.ValueComponentCommand;
import controller.script.command.VerticalFlipCommand;
import model.StorageModel;
import view.script.ScriptView;

/**
 * Implementation of ScriptController interface, controls an image store.
 */
public class ScriptControllerImpl implements ScriptController {
  private final Map<String, CommandController> commands = new HashMap<>();
  private final ScriptView view;

  /**
   * Public constructor for an Image ScriptController. Populates list of commands.
   *
   * @param imageStore state of Image Model
   */
  public ScriptControllerImpl(StorageModel imageStore, ScriptView view) {
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
  public void controllerGo() throws IOException {
    view.displayStatus("Image Processing Application by Rocky and Griffin");

    String executionStatus;

    //run until break detected
    while (true) {
      view.displayStatus(">> ");
      String commandLine = view.getInput();
      if (commandLine.isEmpty()) {
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
  public void controllerGo(String scriptPath) throws IOException {

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

import controller.gui.Features;
import controller.gui.GUIControllerImpl;
import controller.script.ScriptController;
import controller.script.ScriptControllerImpl;
import model.ImageStorageModel;
import model.StorageModel;
import model.gui.GUIModel;
import model.gui.GUIModelImpl;
import view.gui.GUIView;
import view.gui.GUIViewImpl;
import view.script.ScriptView;
import view.script.ScriptViewImpl;

/**
 * Main driver class for this project.
 */
public class Main {

  /**
   * Main method. Run this.
   *
   * @param args command line arguments, of which there are none.
   * @throws Exception if exception encountered.
   */
  public static void main(String[] args) throws Exception {

    //run with gui
    if (args.length == 0) {
      GUIModel model = new GUIModelImpl();
      GUIView view = new GUIViewImpl(model);
      Features controller = new GUIControllerImpl(model, view);
      view.addFeatures(controller);
    }

    //script-based
    else {
      //initialize MVC
      StorageModel scriptStorageModel = new ImageStorageModel();
      ScriptView scriptView = new ScriptViewImpl(System.in, System.out);
      ScriptController scriptController = new ScriptControllerImpl(scriptStorageModel, scriptView);

      //if command line arg -file exists
      if (args.length > 1 && args[0].equals("-file")) {
        scriptController.controllerGo(args[1]);
      } else if (args.length == 1 && args[0].equals("-text")) {
        scriptController.controllerGo();
      }
    }
  }


}

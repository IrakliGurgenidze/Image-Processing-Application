package controller_tests.feature_tests;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import controller.gui.Features;
import controller.gui.GUIControllerImpl;
import controller.script.ScriptController;
import controller.script.ScriptControllerImpl;
import model.ImageStorageModel;
import model.StorageModel;
import view.script.ScriptView;
import view.script.ScriptViewImpl;
import static controller_tests.command_tests.AbstractCommandTest.setWd;
import static org.junit.Assert.assertEquals;

/**
 * Class to drive the testing of the FeatureTestViewImpl.
 */
public class GUIControllerImplTest {
  private final String workingDirectory = setWd();

  /**
   * Public constructor for this class.
   * @throws IOException if the file is not properly loaded
   */
  @Test
  public void BasicGUIControllerImplTest() throws IOException{

    //instantiate the model, mock view, and controller/features
    StorageModel imageStorageModel = new ImageStorageModel();
    loadImage(imageStorageModel);
    assertEquals(1, imageStorageModel.getSize());

    FeatureTestViewImpl view = new FeatureTestViewImpl(imageStorageModel.getImage("base"));
    Features controller = new GUIControllerImpl(imageStorageModel, view);
    view.addFeatures(controller);

    //run test
    callAllFeatures(view);
  }

  private void callAllFeatures(FeatureTestViewImpl featureOp) throws IOException {
    featureOp.callFeature("load");
    featureOp.callFeature("save");
    featureOp.callFeature("clear");
//    featureOp.callFeature("visualize-red");
//    featureOp.callFeature("visualize-green");
//    featureOp.callFeature("visualize-blue");
//    featureOp.callFeature("horizontal-flip");
//    featureOp.callFeature("vertical-flip");
//    featureOp.callFeature("blur");
//    featureOp.callFeature("sharpen");
//    featureOp.callFeature("convert-greyscale");
//    featureOp.callFeature("convert-sepia");
//    featureOp.callFeature("brighten");
//    featureOp.callFeature("run-levels-adjustment");
//    featureOp.callFeature("run-compression");
//    featureOp.callFeature("run-color-correction");
//    featureOp.callFeature("toggle-split-view");
//    featureOp.callFeature("apply-split-op");
  }

  private void loadImage(StorageModel imageStorageModel){
    ScriptView scriptView = new ScriptViewImpl(System.in,System.out);
    ScriptController controller = new ScriptControllerImpl(imageStorageModel, scriptView);

    controller.runCommand(controller.parseCommand("load " + workingDirectory + "sample_images" + File.separator
            + "manhattan-small.png" + " base"));
  }
}

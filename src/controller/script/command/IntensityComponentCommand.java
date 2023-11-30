package controller.script.command;

import model.Image;
import model.StorageModel;


/**
 * This command computes and stores the intensity component of an image.
 */
public class IntensityComponentCommand implements CommandController {

    //model state
    private final StorageModel imageStorageModel;

    /**
     * This constructor initializes the command.
     *
     * @param imageStorageModel state of the image database
     */
    public IntensityComponentCommand(StorageModel imageStorageModel) {
        this.imageStorageModel = imageStorageModel;
    }

    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
                    + args.length + ". Correct usage: " + getUsage());
        }

        String sourceImageName = args[1];
        String resultImageName = args[2];

        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if (sourceImage == null) {
            throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                    + " not found.");
        }

        Image resultImage = sourceImage.getIntensityComponent(resultImageName);
        imageStorageModel.insertImage(resultImage);
        return "Completed intensity-component operation.";
    }

    @Override
    public String getUsage() {
        return "intensity-component image-name dest-image-name: Create an image with the\n "
                + "intensity-component of the image with the given name, and refer to it henceforth "
                + "in\n the program by the given destination name.";
    }


}

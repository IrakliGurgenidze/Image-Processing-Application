package controller.script.command;

import model.Image;
import model.StorageModel;


/**
 * This command computes and stores the red component of an image.
 */
public class RedComponentCommand implements CommandController {

    //state of image database
    private final StorageModel imageStorageModel;

    /**
     * This constructor initializes the command.
     *
     * @param imageStorageModel state of image database
     */
    public RedComponentCommand(StorageModel imageStorageModel) {
        this.imageStorageModel = imageStorageModel;
    }

    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
                    + args.length + ". Correct usage: " + getUsage());
        } else {
            String sourceImageName = args[1];
            String resultImageName = args[2];

            Image sourceImage = imageStorageModel.getImage(sourceImageName);
            if (sourceImage == null) {
                throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                        + " not found.");
            }
            Image resultImage = sourceImage.getRedComponent(resultImageName);
            imageStorageModel.insertImage(resultImage);
            return "Completed red-component operation.";
        }
    }

    @Override
    public String getUsage() {
        return "red-component image-name dest-image-name: Create an image with the\n "
                + "red-component of the image with the given name, and refer to it henceforth in\n"
                + "the program by the given destination name.";
    }
}

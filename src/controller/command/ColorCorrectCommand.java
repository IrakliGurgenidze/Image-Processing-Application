package controller.command;

import model.Image;
import model.ImageStorageModel;

public class ColorCorrectCommand implements CommandController{
    private final ImageStorageModel imageStorageModel;

    /**
     * This is the constructor to initialize the command.
     *
     * @param imageStorageModel state of image database
     */
    public ColorCorrectCommand(ImageStorageModel imageStorageModel) {
        this.imageStorageModel = imageStorageModel;
    }

    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if(args.length != 3) {
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found "
                    + args.length + ". Correct usage: " + getUsage());
        }

        String sourceImageName = args[1];
        String destImageName = args[2];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);
        if (sourceImage == null) {
            throw new IllegalArgumentException("Invalid request. Image with name " + sourceImageName
                    + " not found.");
        }

        Image colorCorrected = colorCorrect(sourceImage, destImageName);
        imageStorageModel.insertImage(colorCorrected);
        return "Color correction operation successful.";
    }

    @Override
    public String getUsage() {
        return null;
    }

    private Image colorCorrect(Image sourceImage, String destImageName){
        return null;
    }
}

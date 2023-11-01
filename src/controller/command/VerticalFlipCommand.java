package controller.command;

import model.ImageModel;

public class VerticalFlipCommand implements CommandController {
    private final ImageModel imageModel;

    public VerticalFlipCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.verticalFlipImage(sourceImage, destImage);
    }
}

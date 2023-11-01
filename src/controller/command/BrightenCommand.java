package controller.command;

import model.ImageModel;

public class BrightenCommand implements CommandController {
    private final ImageModel imageModel;

    public BrightenCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        int increment = Integer.parseInt(args[1]);
        String sourceImage = args[2];
        String destImage = args[3];
        imageModel.applyBrighten(increment, sourceImage, destImage);
    }
}

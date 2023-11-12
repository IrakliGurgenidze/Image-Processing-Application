package controller.command;

import model.Image;
import model.ImageStorageModel;

public class HistogramCommand implements CommandController{

    private final ImageStorageModel imageStorageModel;

    public HistogramCommand(ImageStorageModel imageStorageModel){
        this.imageStorageModel = imageStorageModel;
    }
    @Override
    public String execute(String[] args) throws IllegalArgumentException {
        if(args.length != 3){
            throw new IllegalArgumentException("Invalid input, looking for 3 arguments but only found"
                    + args.length + ". Correct usage: " + getUsage());
        }
        String sourceImageName = args[1];
        String destImageName = args[2];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);

        return "Completed histogram creation. File saved as: " + destImageName;
    }

    @Override
    public String getUsage() {
        return null;
    }
}

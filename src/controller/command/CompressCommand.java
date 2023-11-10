package controller.command;

import model.Image;
import model.ImageStorageModel;
import model.Pixel;
import model.SimpleImage;

public class CompressCommand implements CommandController{
    private final ImageStorageModel imageStorageModel;

    public CompressCommand(ImageStorageModel imageStorageModel){
        this.imageStorageModel = imageStorageModel;
    }
    @Override
    public String execute(String[] args) {
        if(args.length != 4) {
            throw new IllegalArgumentException("Invalid input, looking for 4 arguments but only found "
                    + args.length + ". Correct usage: " + getUsage());
        }

        int compression;

        try {
            compression = Integer.parseInt(args[1]);
        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Compression value invalid. Please enter an integer " +
                    "0-100.");
        }

        String sourceImageName = args[2];
        String destImageName = args[3];
        Image sourceImage = imageStorageModel.getImage(sourceImageName);

        if(sourceImage == null) {
            throw new IllegalArgumentException("Invalid input. Source image does not exist in database.");
        }

        Image destImage = compress(compression, sourceImage, destImageName);
        imageStorageModel.insertImage(destImage);

        return "Compression of " + sourceImageName + "completed. Saved as: " + destImageName;
    }

    @Override
    public String getUsage() {
        return "compress percentage image-name dest-image-name: compress image by given percentage to create" +
                "a new image, referred to henceforth as the destination image name. " +
                "Percentages between 0 and 100 are considered valid.";
    }

    private Image compress(int compression, Image source, String destImageName){
        int height = source.getHeight();
        int width = source.getWidth();

        Image compressedImage = new SimpleImage(width, height, destImageName);
        return null;
    }

    private Pixel[] transform(Pixel[] row) {
        return null;
    }
}

public class BlueComponentCommand implements CommandController{
    private final ImageModel imageModel;

    public BlueComponentCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.blueImage(sourceImage, destImage);
    }
}

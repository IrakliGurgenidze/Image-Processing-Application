public class HorizontalFlipCommand implements CommandController{
    private final ImageModel imageModel;

    public HorizontalFlipCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.horizontalFlipImage(sourceImage, destImage);
    }
}

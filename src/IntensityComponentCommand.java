public class IntensityComponentCommand implements CommandController{
    private final ImageModel imageModel;

    public IntensityComponentCommand(ImageModel imageModel){
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.intensityImage(sourceImage, destImage);
    }
}

public class ValueComponentCommand implements CommandController{
    private final ImageModel imageModel;

    public ValueComponentCommand(ImageModel imageModel){
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.valueImage(sourceImage, destImage);
    }
}

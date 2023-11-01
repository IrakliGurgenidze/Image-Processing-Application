public class LumaComponentCommand implements CommandController{
    private final ImageModel imageModel;

    public LumaComponentCommand(ImageModel imageModel){
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage = args[2];
        imageModel.lumaImage(sourceImage, destImage);
    }
}

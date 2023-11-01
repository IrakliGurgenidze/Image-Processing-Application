public class LinearColorTransformationCommand implements CommandController{
    private final ImageModel imageModel;

    private final String transformationName;

    public LinearColorTransformationCommand(ImageModel imageModel, String transformationName) {
        this.imageModel = imageModel;
        this.transformationName = transformationName;
    }
    @Override
    public void execute(String[] args) {
        String sourceImage = args[1];
        String destImage  = args[2];
        imageModel.applyLinearColorTransformation(sourceImage, destImage, transformationName);
    }
}

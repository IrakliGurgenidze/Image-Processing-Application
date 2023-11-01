public class RGBSplitCommand implements CommandController{
    private final ImageModel imageModel;

    public RGBSplitCommand(ImageModel imageModel){
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {

        String sourceImageName = args[1];
        String destImageRedName = args[2];
        String destImageGreenName = args[3];
        String destImageBlueName = args[4];

        redSplit(sourceImageName, destImageRedName);
        greenSplit(sourceImageName, destImageGreenName);
        blueSplit(sourceImageName, destImageBlueName);

    }

    private void redSplit(String imageName, String redName){
        String[] args = {null, imageName, redName};
        RedComponentCommand redComponentCommand = new RedComponentCommand(imageModel);
        redComponentCommand.execute(args);
    }

    private void greenSplit(String imageName, String greenName) {
        String[] args = {null, imageName, greenName};
        GreenComponentCommand greenComponentCommand = new GreenComponentCommand(imageModel);
        greenComponentCommand.execute(args);
    }

    private void blueSplit(String imageName, String blueName) {
        String[] args = {null, imageName, blueName};
        BlueComponentCommand blueComponentCommand = new BlueComponentCommand(imageModel);
        blueComponentCommand.execute(args);
    }
}

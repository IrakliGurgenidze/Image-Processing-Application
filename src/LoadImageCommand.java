import java.io.IOException;

public class LoadImageCommand implements CommandController {
    private final ImageModel imageModel;

    public LoadImageCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    @Override
    public void execute(String[] args) {
        String imagePath = args[1];
        String imageName = args[2];

        Image image = loadImage(imagePath, imageName);
        if(image != null) {
            imageModel.insertImage(image);
        }
    }

    private Image loadImage(String fileName, String imageName) {
        try {
            if(fileName.split("\\.")[1].equals("ppm")) {
                return ImageUtil.readColorPPM(fileName, imageName);
            } else {
                return ImageUtil.readColor(fileName, imageName);
            }
        }
        catch(IOException e) {
            System.out.println("File " + fileName + "does not exist.");
        }
        return null;
    }
}

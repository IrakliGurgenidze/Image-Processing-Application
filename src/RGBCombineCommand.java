public class RGBCombineCommand implements CommandController {
    private final ImageModel imageModel;

    public RGBCombineCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public void execute(String[] args) {
        String combinedImageName = args[1];
        String redImageName = args[2];
        String greenImageName = args[3];
        String blueImageName = args[4];

        Image combinedImage = combine(redImageName, greenImageName, blueImageName, combinedImageName);
        imageModel.insertImage(combinedImage);
    }

    private Image combine(String red, String green, String blue, String combined) {
        Image redImage = imageModel.getImage(red);
        Image greenImage = imageModel.getImage(green);
        Image blueImage = imageModel.getImage(blue);
        Image combinedImage = new ColorImage(redImage.getWidth(), redImage.getHeight(), combined);

        for (int y = 0; y < redImage.getHeight(); y++) {
            for (int x = 0; x < redImage.getWidth(); x++) {
                Pixel currRedPixel = redImage.getPixel(x, y);
                Pixel currGreenPixel = greenImage.getPixel(x, y);
                Pixel currBluePixel = blueImage.getPixel(x, y);

                int redComp = currRedPixel.getRed();
                int greenComp = currGreenPixel.getGreen();
                int blueComp = currBluePixel.getBlue();

                Pixel combinedPixel = new Pixel(redComp, greenComp, blueComp);

                combinedImage.setPixel(x, y, combinedPixel);
            }
        }
        return combinedImage;
    }
}

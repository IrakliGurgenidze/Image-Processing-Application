import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageCommand implements CommandController {
    private final ImageModel imageModel;

    public SaveImageCommand(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public void execute(String[] args) {
//        String imagePath = args[1];
//        String imageName = args[2];
//
//        Image image = imageModel.getImage(imageName);
//        if (image == null) {
//            System.out.println("Image with name " + imageName + " not found.");
//        } else {
//            try {
//                saveImage(image, imagePath);
//            } catch (IOException e) {
//                System.out.println("Invalid path.");
//            }
//        }
//    }
//
//    private void saveImage(Image image, String imagePath) throws IOException {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        String ext = imagePath.split("\\.")[1];
//
//        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
//
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                Pixel pixel = image.getPixel(j, i);
//                Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
//                bufferedImage.setRGB(j, i, color.getRGB());
//            }
//        }
//        ImageIO.write(bufferedImage, ext, new File(imagePath));
    }
}

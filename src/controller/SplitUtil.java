package controller;

import model.Image;
import model.Pixel;
import model.SimpleImage;

import javax.xml.crypto.dsig.SignatureMethod;

/**
 * This class provides the utility to split an image based on a given percentage.
 */
public class SplitUtil {

    /**
     * Method to perform the split operation by combining a portion of one image with another.
     * @param sourceImage Image, the original image to be maintained on the right
     * @param operatedImage Image, the image operated on to be previewed on left
     * @param percentage int, the split line percentage
     * @param destImageName String, the name of the resulting image
     * @return the resulting image after split operation
     */
    public static Image splitImage(Image sourceImage, Image operatedImage, int percentage, String destImageName){
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int split = (int)(width * (percentage/100.0));

        Pixel[][] splitImage = new Pixel[height][width];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Pixel currPixel;
                if(x < split){
                    currPixel = operatedImage.getPixel(x,y);
                }else{
                    currPixel = sourceImage.getPixel(x,y);
                }
                splitImage[y][x] = currPixel;
            }
        }
        return new SimpleImage(destImageName, splitImage);
    }
}

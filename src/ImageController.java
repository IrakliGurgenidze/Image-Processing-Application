import java.io.IOException;

public class ImageController {
  private final ImageModel imageStore;

  public ImageController(ImageModel imageStore) {
    this.imageStore = imageStore;
  }

  public void runCommand(String[] args) {
    switch(args[0])  {
      case "load":
        Image img = ImageUtil.loadImage(args[0], args[1]);
        if(img == null) {
          break;
        }

//
//      case "save":
//        imageStore.insertImage(args[]);
//        break;
    }
  }


}

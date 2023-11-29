package model.gui;

import model.Image;
import model.SimpleImage;

/**
 * This class represents a simple implementation of a GUI model.
 * It is meant to only have one image loaded at a time, and stores both the base image
 * that was loaded and any changes made to the image.
 */
public class GUIModelImpl implements GUIModel {

  //the three images used by the application
  private Image baseImage = null; //the base image. this is what is loaded by the user, before any edits.
  private Image currentImage = null; //the base image including persistent edits, such as "blur"
  private Image splitImage = null;


  @Override
  public Image getBaseImage() {
    return baseImage;
  }

  @Override
  public void setBaseImage(Image baseImage) {
    this.baseImage = baseImage;
    this.currentImage = baseImage;
    this.splitImage = currentImage;
  }

  @Override
  public Image getCurrentImage() {
    return currentImage;
  }

  @Override
  public void setCurrentImage(Image currentImage) {
    this.currentImage = currentImage;
  }

  @Override
  public Image getSplitImage() {
    return splitImage;
  }

  @Override
  public void setSplitImage(Image displayImage) {
    this.splitImage = displayImage;
  }
}

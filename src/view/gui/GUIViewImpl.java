package view.gui;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.gui.Features;
import model.Image;
import model.Pixel;
import model.StorageModel;

/**
 * Implements GUIView to act as view for Image Processor.
 */
public class GUIViewImpl extends JFrame implements GUIView {
  private JButton load;
  private JButton save;
  private JButton blur;
  private JButton sharpen;
  private JButton sepia;
  private JButton hFlip;
  private JButton vFlip;
  private JButton rComp;
  private JButton gComp;
  private JButton bComp;
  private JButton greyscale;
  private JButton colorCorrect;

  private JButton levelsAdj;
  private JButton clear;

  private JLabel operationPath;

  private JPanel histogramPanel;
  private JPanel imagePreview;
  private JScrollPane imageScrollPane;


  /**
   * Public constructor for the GUI view.
   * @param imageStorageModel the application model
   */
  public GUIViewImpl(StorageModel imageStorageModel){
    super("Image Processing Application");
    setLocation(0, 0);

    //set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    initButtons();

    //creates utility bar in top portion of frame
    JPanel utilityBar = new JPanel();
    utilityBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    utilityBar.add(load);
    utilityBar.add(save);
    utilityBar.add(clear);
    operationPath = new JLabel("Placeholder"); //image operation path
    utilityBar.add(operationPath);
    this.add(utilityBar, BorderLayout.NORTH);

    //define dimensions of east toolbar
    JPanel additionalFeatures = new JPanel();
    additionalFeatures.setLayout(new BoxLayout(additionalFeatures, BoxLayout.Y_AXIS));
    additionalFeatures.setBackground(Color.GREEN);
    additionalFeatures.setPreferredSize(new Dimension(256, getHeight()));

    //define dimensions of histogram box
    histogramPanel = new JPanel();
    histogramPanel.setBackground(Color.BLACK);
    histogramPanel.setPreferredSize(new Dimension(256,256));
    histogramPanel.setMaximumSize(new Dimension(256,256));
    additionalFeatures.add(histogramPanel);

    //add buttons to east toolbar layout
    JPanel featureButtons = new JPanel();
    featureButtons.setLayout(new GridLayout(5,2));
    featureButtons.setBorder(BorderFactory.createEmptyBorder(25,5,5,5));
    featureButtons.add(rComp);
    featureButtons.add(gComp);
    featureButtons.add(bComp);
    featureButtons.add(hFlip);
    featureButtons.add(vFlip);
    featureButtons.add(blur);
    featureButtons.add(sharpen);
    featureButtons.add(greyscale);
    featureButtons.add(sepia);
    featureButtons.add(colorCorrect);
    additionalFeatures.add(featureButtons);

    //add sliders to east toolbar
    JPanel sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
    JLabel brightenLabel = new JLabel("Brighten");
    JLabel compressLabel = new JLabel("Compression");
    JSlider brighten = new JSlider(JSlider.CENTER, -100, 100, 0);
    JSlider compression = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    sliders.add(brightenLabel);
    sliders.add(brighten);
    sliders.add(compressLabel);
    sliders.add(compression);
    sliders.setBorder(BorderFactory.createEmptyBorder(55, 7,0, 7));
    additionalFeatures.add(sliders);

    //add levels adjust panel to east toolbar
    JPanel levelsAdjPanel = new JPanel(new GridLayout(8,1));
    levelsAdjPanel.setBorder(BorderFactory.createEmptyBorder(55,20,5,20));
    JTextField bVal = new JTextField();
    JTextField mVal = new JTextField();
    JTextField wVal = new JTextField();
    bVal.setMaximumSize(new Dimension(128,5));
    mVal.setMaximumSize(new Dimension(128,5));
    wVal.setMaximumSize(new Dimension(128,5));
    JLabel params = new JLabel("Parameters: b < m < w");
    params.setHorizontalAlignment(JLabel.CENTER);
    levelsAdjPanel.add(params);
    levelsAdjPanel.add(bVal);
    levelsAdjPanel.add(mVal);
    levelsAdjPanel.add(wVal);
    levelsAdjPanel.add(new JPanel());
    levelsAdjPanel.add(levelsAdj);
    additionalFeatures.add(levelsAdjPanel);

    //set button dimensions
    Dimension maxButtonSize = new Dimension(Integer.MAX_VALUE, rComp.getPreferredSize().height);
    rComp.setMaximumSize(maxButtonSize);
    gComp.setMaximumSize(maxButtonSize);
    bComp.setMaximumSize(maxButtonSize);
    hFlip.setMaximumSize(maxButtonSize);
    vFlip.setMaximumSize(maxButtonSize);
    blur.setMaximumSize(maxButtonSize);
    sharpen.setMaximumSize(maxButtonSize);
    greyscale.setMaximumSize(maxButtonSize);
    sepia.setMaximumSize(maxButtonSize);

    //set image panel layout
    imagePreview = new JPanel();
    imagePreview.setBackground(Color.BLUE);
    imageScrollPane = new JScrollPane(imagePreview);

    //add panels to frame
    this.add(imageScrollPane, BorderLayout.CENTER);
    this.add(additionalFeatures, BorderLayout.EAST);

    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);
  }


  @Override
  public void setImageLabel(String imageLabel) {

  }

  @Override
  public void getInputString() {

  }

  @Override
  public void clearInputString() {

  }

  @Override
  public void addFeatures(Features features) {
    //load
    load.addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      FileFilter filter = new FileNameExtensionFilter("jpg","jpeg",
              "ppm", "png");
      fc.setFileFilter(filter);
      int res = fc.showOpenDialog(GUIViewImpl.this);
      if(res == JFileChooser.APPROVE_OPTION){
       String filePath = fc.getSelectedFile().getAbsolutePath();
       features.loadImage(filePath, fc.getSelectedFile().getName());
      }
    });

    //save

    //visualize red component
    rComp.addActionListener(evt -> features.visualizeRed());

    //visualize green component
    gComp.addActionListener(evt -> features.visualizeGreen());

    //visualize blue component
    bComp.addActionListener(evt -> features.visualizeBlue());

    //flip horizontally
    hFlip.addActionListener(evt -> features.flipHorizontal());

    //flip vertically
    vFlip.addActionListener(evt -> features.flipVertical());

    //blur image
    blur.addActionListener(evt -> features.blurImage());

    //sharpen image
    sharpen.addActionListener(evt -> features.sharpenImage());

    //convert to greyscale
    //FIXME button needs to be renamed

    //convert to sepia
    sepia.addActionListener(evt -> features.convertSepia());

    //run levels adjustment

    //run compression

    //run color correction
    colorCorrect.addActionListener(evt -> features.runColorCorrection());

    //toggle split view

    //clear
    clear.addActionListener(evt -> features.clear());

    //exit program

    //keyboard events, if we want them
  }

  @Override
  public void displayImage(Image image) {
    /**
    Graphics g = imageScrollPane.getGraphics();
    for(int x = 0; x < image.getWidth(); x++){
      for(int y =0; y < image.getHeight(); y++){
        Pixel pixel = image.getPixel(x, y);
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        g.setColor(color);
        g.drawRect(x,y,1,1);
      }
    }
    //imagePreview.paint(g);
    operationPath.setText(image.getName());
     */

    int width = image.getWidth();
    int height = image.getHeight();

    // Create a BufferedImage with the same dimensions as the image
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Get the graphics object from the buffered image
    Graphics g = bufferedImage.getGraphics();

    if (g != null) {
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Pixel pixel = image.getPixel(x, y);
          Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
          bufferedImage.setRGB(x, y, color.getRGB());
        }
      }

      // Draw the entire image at once
      imagePreview.getGraphics().drawImage(bufferedImage, 0, 0,
              imagePreview.getWidth(), imagePreview.getHeight(), null);

      // Dispose of the graphics object to release resources
      g.dispose();
    }

    operationPath.setText(image.getName());
  }


  private void initButtons(){
    load = new JButton("Load");
    load.setActionCommand("Load Button");

    save = new JButton("Save");
    save.setActionCommand("Save Button");

    clear = new JButton("Clear");
    clear.setActionCommand("Clear Button");

    hFlip = new JButton("Horizontal Flip");
    hFlip.setActionCommand("Horizontal Flip Button");

    vFlip = new JButton("Vertical Flip");
    vFlip.setActionCommand("Vertical Flip Button");

    rComp = new JButton("Red Component");
    rComp.setActionCommand("Red Component Button");

    gComp = new JButton("Green Component");
    gComp.setActionCommand("Green Component Button");

    bComp = new JButton("Blue Component");
    bComp.setActionCommand("Blue Component Button");

    blur = new JButton("Blur");
    blur.setActionCommand("Blur Button");

    sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("Sharpen Button");

    greyscale = new JButton("Greyscale");
    greyscale.setActionCommand("Greyscale Button");

    sepia = new JButton("Sepia");
    sepia.setActionCommand("Sepia Button");

    levelsAdj = new JButton("Levels Adjust");
    levelsAdj.setActionCommand("Levels Adjust Button");

    colorCorrect = new JButton("Color Correct");
    colorCorrect.setActionCommand("Color Correct Button");
  }

}
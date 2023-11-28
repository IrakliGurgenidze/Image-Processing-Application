package view.gui;
import java.awt.*;

import javax.swing.*;

import controller.gui.Features;
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
  private JButton luma;


  /**
   * Public constructor for the GUI view.
   * @param imageStorageModel the application model
   */
  public GUIViewImpl(StorageModel imageStorageModel){
    super("Image Processing Application");
    //setSize(1920, 1080);
    setLocation(0, 0);

    //set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //set top button panel layout
    JPanel featureButtons = new JPanel();
    GridLayout gridLayout = new GridLayout();
    gridLayout.setHgap(15);
    featureButtons.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    featureButtons.setLayout(gridLayout);


    //set right panel layout (histogram, sliders)
    JPanel additionalFeatures = new JPanel();
    additionalFeatures.setLayout(new BoxLayout(additionalFeatures, BoxLayout.Y_AXIS));
    additionalFeatures.setBackground(Color.GREEN);

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBackground(Color.BLACK);
    additionalFeatures.add(histogramPanel);

    JPanel sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
    JLabel brightenLabel = new JLabel("Brighten");
    JLabel compressLabel = new JLabel("Compression");
    JSlider brighten = new JSlider();
    JSlider compression = new JSlider();
    sliders.add(brightenLabel);
    sliders.add(brighten);
    sliders.add(compressLabel);
    sliders.add(compression);
    sliders.setBorder(BorderFactory.createEmptyBorder(25, 0,0, 0));
    additionalFeatures.add(sliders);

    JPanel levelsAdjPanel = new JPanel();
    JTextField bVal = new JTextField();
    JTextField mVal = new JTextField();
    JTextField wVal = new JTextField();
    bVal.setPreferredSize(new Dimension(128,50));
    mVal.setPreferredSize(new Dimension(128,50));
    wVal.setPreferredSize(new Dimension(128,50));
    levelsAdjPanel.add(bVal);
    levelsAdjPanel.add(mVal);
    levelsAdjPanel.add(wVal);
    additionalFeatures.add(levelsAdjPanel);

    //set image panel layout
    JPanel imagePreview = new JPanel();
    imagePreview.setBackground(Color.BLUE);

    //add buttons to top panel
    blur = new JButton("Blur");
    blur.setActionCommand("Blur Button");

    sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("Sharpen Button");

    sepia = new JButton("Sepia");
    sepia.setActionCommand("Sepia Button");

    load = new JButton("Load");
    load.setActionCommand("Load Button");

    save = new JButton("Save");
    save.setActionCommand("Save Button");

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

    luma = new JButton("Luma");
    luma.setActionCommand("Luma Button");

    featureButtons.add(load);
    featureButtons.add(save);
    featureButtons.add(rComp);
    featureButtons.add(gComp);
    featureButtons.add(bComp);
    featureButtons.add(sepia);
    featureButtons.add(sharpen);
    featureButtons.add(blur);
    featureButtons.add(luma);
    featureButtons.add(hFlip);
    featureButtons.add(vFlip);

    //add panels to frame
    this.add(featureButtons, BorderLayout.NORTH);
    featureButtons.setPreferredSize(new Dimension(1920, 100));
    this.add(imagePreview, BorderLayout.CENTER);
    this.add(additionalFeatures, BorderLayout.EAST);
    additionalFeatures.setPreferredSize(new Dimension(256, 1080));

    pack();
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

  }
}
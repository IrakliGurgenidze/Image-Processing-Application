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
  private JButton colorCorrect;

  private JButton levelsAdj;
  private JButton clear;


  /**
   * Public constructor for the GUI view.
   * @param imageStorageModel the application model
   */
  public GUIViewImpl(StorageModel imageStorageModel){
    super("Image Processing Application");
    setLocation(0, 0);
    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

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
    JLabel operationPath = new JLabel("Placeholder");
    utilityBar.add(operationPath);
    this.add(utilityBar, BorderLayout.NORTH);



    JPanel additionalFeatures = new JPanel();
    additionalFeatures.setLayout(new BoxLayout(additionalFeatures, BoxLayout.Y_AXIS));
    additionalFeatures.setBackground(Color.GREEN);
    additionalFeatures.setPreferredSize(new Dimension(256, getHeight()));

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBackground(Color.BLACK);
    histogramPanel.setPreferredSize(new Dimension(256,256));
    histogramPanel.setMaximumSize(new Dimension(256,256));
    additionalFeatures.add(histogramPanel);

    JPanel featureButtons = new JPanel();
    featureButtons.setLayout(new GridLayout(5,2));
    featureButtons.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    featureButtons.add(rComp);
    featureButtons.add(gComp);
    featureButtons.add(bComp);
    featureButtons.add(sepia);
    featureButtons.add(sharpen);
    featureButtons.add(blur);
    featureButtons.add(luma);
    featureButtons.add(colorCorrect);
    featureButtons.add(hFlip);
    featureButtons.add(vFlip);
    additionalFeatures.add(featureButtons);


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
    levelsAdjPanel.add(levelsAdj);
    additionalFeatures.add(levelsAdjPanel);

    Dimension maxButtonSize = new Dimension(Integer.MAX_VALUE, rComp.getPreferredSize().height);
    rComp.setMaximumSize(maxButtonSize);
    gComp.setMaximumSize(maxButtonSize);
    bComp.setMaximumSize(maxButtonSize);
    sepia.setMaximumSize(maxButtonSize);
    sharpen.setMaximumSize(maxButtonSize);
    blur.setMaximumSize(maxButtonSize);
    luma.setMaximumSize(maxButtonSize);
    hFlip.setMaximumSize(maxButtonSize);
    vFlip.setMaximumSize(maxButtonSize);

    //set image panel layout
    JPanel imagePreview = new JPanel();
    imagePreview.setBackground(Color.BLUE);


    //add panels to frame
    this.add(imagePreview, BorderLayout.CENTER);
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

  }

  private void initButtons(){
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

    luma = new JButton("Luma");
    luma.setActionCommand("Luma Button");

    levelsAdj = new JButton("Levels Adjust");
    levelsAdj.setActionCommand("Levels Adjust Button");

    colorCorrect = new JButton("Color Correct");
    colorCorrect.setActionCommand("Color Correct Button");
  }
}
package view.gui;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.gui.Features;
import model.StorageModel;
import model.gui.GUIModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implements GUIView to act as view for Image Processor.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  //buttons
  private JButton load;

  private JToggleButton split;
  private JButton apply;
  private boolean isSplitEnabled = false;

  private boolean imageLoaded = false;
  private JTextField splitPct;
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
  private JTextField bVal;
  private JTextField mVal;
  private JTextField wVal;
  private JButton clear;

  //sliders
  private JSlider brighten;
  private JSlider compression;

  private JLabel operationPath;

  private JPanel histogramPanel;
  private JScrollPane imagePreview;


  /**
   * Public constructor for the GUI view.
   * @param imageStorageModel the application model
   */
  public GUIViewImpl(GUIModel imageStorageModel){
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
    operationPath = new JLabel("Image not loaded."); //image operation path
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
    featureButtons.setLayout(new GridLayout(5,1));
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

    JPanel splitView = new JPanel();
    splitView.setLayout(new GridLayout(3,1));
    splitView.add(split);
    splitPct = new JTextField();
    splitView.add(splitPct);
    JLabel splitParams = new JLabel("Split Percentage must be between 0-100.");
    splitParams.setHorizontalAlignment(JLabel.CENTER);
    Font font = splitParams.getFont();
    float fontSize = 11;
    splitParams.setFont(font.deriveFont(fontSize));
    splitView.add(splitParams);
    splitView.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
    additionalFeatures.add(splitView);

    //add sliders to east toolbar
    JPanel sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
    JLabel brightenLabel = new JLabel("Brighten");
    JLabel compressLabel = new JLabel("Compression");
    brighten = new JSlider(JSlider.CENTER, -100, 100, 0);
    compression = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    sliders.add(brightenLabel);
    sliders.add(brighten);
    sliders.add(compressLabel);
    sliders.add(compression);
    sliders.setBorder(BorderFactory.createEmptyBorder(15, 7,0, 7));
    additionalFeatures.add(sliders);

    //add levels adjust panel to east toolbar
    JPanel levelsAdjPanel = new JPanel(new GridLayout(4,2));
    levelsAdjPanel.setBorder(BorderFactory.createEmptyBorder(15,20,5,20));
    bVal = new JTextField();
    mVal = new JTextField();
    wVal = new JTextField();
    bVal.setMaximumSize(new Dimension(128,5));
    mVal.setMaximumSize(new Dimension(128,5));
    wVal.setMaximumSize(new Dimension(128,5));
    levelsAdjPanel.add(new JLabel("b: "));
    levelsAdjPanel.add(bVal);
    bVal.setHorizontalAlignment(JTextField.CENTER);
    levelsAdjPanel.add(new JLabel("m: "));
    levelsAdjPanel.add(mVal);
    mVal.setHorizontalAlignment(JTextField.CENTER);
    levelsAdjPanel.add(new JLabel("w: "));
    levelsAdjPanel.add(wVal);
    wVal.setHorizontalAlignment(JTextField.CENTER);

    levelsAdjPanel.add(new JLabel("b < m < w"));
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
    split.setMaximumSize(maxButtonSize);

    //set image panel layout
    imagePreview = new JScrollPane();
    imagePreview.getViewport().setBackground(Color.DARK_GRAY);
    imagePreview.setBackground(Color.DARK_GRAY);

    //add panels to frame
    this.add(imagePreview, BorderLayout.CENTER);
    this.add(additionalFeatures, BorderLayout.EAST);

    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    updateButtonStates();
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
  public Map<String, Double> getSliderValues() {
    Map<String, Double> sliderValues = new HashMap<String, Double>();

    //insert relevant UI elements
    sliderValues.put("compression-ratio", (double) compression.getValue());
    sliderValues.put("brighten-increment", (double) brighten.getValue());
    return sliderValues;
  }

  @Override
  public void addFeatures(Features features) {
    //load
    load.addActionListener(e -> {
      imageLoaded = true;
      updateButtonStates();
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
    save.addActionListener(e -> {
      imageLoaded = false;
      updateButtonStates();
    });

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
    levelsAdj.addActionListener(evt -> {
      int b;
      int m;
      int w;
      try{
        b = Integer.parseInt(bVal.getText());
        m = Integer.parseInt(mVal.getText());
        w = Integer.parseInt(wVal.getText());
        if (b > m || m > w || w > 255 || b < 1) {
          throw new NumberFormatException();
        }
        features.runLevelsAdjustment(b,m,w);
      }catch(NumberFormatException nfe){
        errorPopup("b,m,w must be integers between 0-255 in ascending order.");
      }
    });

    //run brighten
    brighten.addChangeListener(e -> {
      features.brighten();
    });

    //run compression
    compression.addChangeListener(e -> {
      features.runCompression();
    });


    //run color correction
    colorCorrect.addActionListener(evt -> features.runColorCorrection());

    //toggle split view

    //clear
    clear.addActionListener(evt -> features.clear());

    //exit program

    //keyboard events, if we want them
  }

  @Override
  public void displayImage(BufferedImage image, String displayName) {
    ImageIcon imgIcon = new ImageIcon(image);
    JLabel label = new JLabel(imgIcon);
    imagePreview.setViewportView(label);
    int x = (imagePreview.getWidth() - image.getWidth()) / 2;
    int y = (imagePreview.getHeight() - image.getHeight()) / 2;
    label.setLocation(x,y);
    operationPath.setText(displayName);
  }



  //helper function to initialize the GUI's buttons
  private void initButtons(){
    load = new JButton("Load");
    load.setActionCommand("Load Button");

    save = new JButton("Save");
    save.setActionCommand("Save Button");

    clear = new JButton("Clear");
    clear.setActionCommand("Clear Button");

    split = new JToggleButton("Split View");
    split.setActionCommand("Split Button");
    apply = new JButton("Apply Previewed Changes");

    //FIXME does it belong ??
    split.addItemListener(e -> {
      int s;
      try{
        s = Integer.parseInt(splitPct.getText());
        if(s > 99 || s < 1) {
          throw new NumberFormatException();
        }
        isSplitEnabled = e.getStateChange() == ItemEvent.SELECTED;
        updateButtonStates();
      }catch(NumberFormatException nfe){
        errorPopup("Split percentage must be an integer between 0-100.");
      }
    });

    apply.addActionListener(e -> {
      split.setSelected(false);
      splitPct.setText("");
      isSplitEnabled = false;
      updateButtonStates();
    });

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

  //disables buttons not compatible with split view
  private void updateButtonStates(){
    hFlip.setEnabled(!isSplitEnabled && imageLoaded);
    vFlip.setEnabled(!isSplitEnabled && imageLoaded);
    rComp.setEnabled(!isSplitEnabled && imageLoaded);
    gComp.setEnabled(!isSplitEnabled && imageLoaded);
    bComp.setEnabled(!isSplitEnabled && imageLoaded);
    greyscale.setEnabled(!isSplitEnabled && imageLoaded);
    splitPct.setEnabled(!isSplitEnabled && imageLoaded);
    brighten.setEnabled(!isSplitEnabled && imageLoaded);
    compression.setEnabled(!isSplitEnabled && imageLoaded);
    split.setEnabled(imageLoaded);
    colorCorrect.setEnabled(imageLoaded);
    blur.setEnabled(imageLoaded);
    sepia.setEnabled(imageLoaded);
    sharpen.setEnabled(imageLoaded);
    levelsAdj.setEnabled(imageLoaded);
    splitPct.setEnabled(imageLoaded);
    bVal.setEnabled(imageLoaded);
    mVal.setEnabled(imageLoaded);
    wVal.setEnabled(imageLoaded);
  }

  private void errorPopup(String message){
    JOptionPane.showMessageDialog(null, message, "Invalid input!",
            JOptionPane.ERROR_MESSAGE);
  }

}
package view.gui;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.gui.Features;
import model.StorageModel;


/**
 * Implements GUIView to act as view for Image Processor.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  //buttons
  private JButton load;

  private JToggleButton split;
  //applies changes from split view
  private JButton apply;
  //checks to see if in split view
  private boolean isSplitEnabled = false;
  //makes sure only 1 operation can be applied when in split view
  private int splitOps = 0;
  private final JLabel splitParams;

  private boolean imageLoaded = false;
  private final JTextField splitPct;
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
  private final JTextField bVal;
  private final JTextField mVal;
  private final JTextField wVal;
  private JButton clear;

  //sliders
  private final JSlider brighten;
  private final JSlider compression;

  private final JLabel operationPath;
  private final JScrollPane opPathPane;

  private JPanel histogramPanel;
  private final JPanel splitView;
  private final JScrollPane imagePreview;

  private final JPanel additionalFeatures;
  private final JScrollPane additionalFeatureScrollPlane;


  /**
   * Public constructor for the GUI view.
   *
   * @param imageStorageModel the application model
   */
  public GUIViewImpl(StorageModel imageStorageModel) {
    super("Image Processing Application");
    setLocation(0, 0);

    //set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    initButtons();

    //creates utility bar in top portion of frame
    JButton[] utilityButtons = {load, save, clear};
    JPanel utilityBar = new JPanel();
    utilityBar.setLayout(new BoxLayout(utilityBar, BoxLayout.X_AXIS));
    for (JButton button : utilityButtons) {
      utilityBar.add(Box.createRigidArea(new Dimension(10, 0)));
      utilityBar.add(button);
    }
    utilityBar.add(Box.createRigidArea(new Dimension(10, 0)));


    operationPath = new JLabel("Image not loaded."); //image operation path
    opPathPane = new JScrollPane();
    opPathPane.setViewportView(operationPath);
    opPathPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    opPathPane.setPreferredSize(new Dimension(1000, 50));
    opPathPane.getViewport().setOpaque(false);
    opPathPane.setOpaque(false);
    opPathPane.setBorder(null);
    utilityBar.add(opPathPane);
    this.add(utilityBar, BorderLayout.NORTH);

    //define dimensions of east toolbar
    additionalFeatures = new JPanel();
    additionalFeatures.setLayout(new BoxLayout(additionalFeatures, BoxLayout.Y_AXIS));
    additionalFeatures.setPreferredSize(null);
    additionalFeatures.add(Box.createRigidArea(new Dimension(0, 10)), 0);

    //define dimensions of histogram box
    histogramPanel = new JPanel();
    histogramPanel.setBackground(Color.DARK_GRAY);
    histogramPanel.setPreferredSize(new Dimension(256, 256));
    histogramPanel.setMaximumSize(new Dimension(256, 256));
    additionalFeatures.add(histogramPanel, 1);

    //add buttons to east toolbar layout
    JPanel featureButtons = new JPanel();
    featureButtons.setLayout(new GridLayout(5, 2));
    featureButtons.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
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

    splitView = new JPanel();
    splitView.setLayout(new GridLayout(3, 1));
    splitView.add(split);
    splitPct = new JTextField("50");
    splitView.add(splitPct);
    splitParams = new JLabel("Split Percentage must be between 1-99.");
    splitParams.setHorizontalAlignment(JLabel.CENTER);
    Font font = splitParams.getFont();
    float fontSize = 11;
    splitParams.setFont(font.deriveFont(fontSize));
    splitView.add(splitParams);
    splitView.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
    additionalFeatures.add(splitView);

    //add sliders to east toolbar
    JPanel sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
    JLabel brightenLabel = new JLabel("Brighten");
    brighten = new JSlider(JSlider.CENTER, -256, 256, 0);
    brighten.setMajorTickSpacing(256);
    brighten.setMinorTickSpacing(32);
    brighten.setPaintTicks(true);
    brighten.setPaintLabels(true);

    JLabel compressLabel = new JLabel("Compression");
    compression = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    compression.setMajorTickSpacing(50);
    compression.setMinorTickSpacing(10);
    compression.setPaintTicks(true);
    compression.setPaintLabels(true);

    sliders.add(brightenLabel);
    sliders.add(brighten);
    sliders.add(compressLabel);
    sliders.add(compression);
    sliders.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7));
    additionalFeatures.add(sliders);

    //add levels adjust panel to east toolbar
    JPanel levelsAdjPanel = new JPanel(new GridLayout(4, 2));
    levelsAdjPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
    bVal = new JTextField();
    mVal = new JTextField();
    wVal = new JTextField();
    bVal.setMaximumSize(new Dimension(128, 5));
    mVal.setMaximumSize(new Dimension(128, 5));
    wVal.setMaximumSize(new Dimension(128, 5));
    levelsAdjPanel.add(new JLabel("b: "));
    levelsAdjPanel.add(bVal);
    bVal.setHorizontalAlignment(JTextField.CENTER);
    levelsAdjPanel.add(new JLabel("m: "));
    levelsAdjPanel.add(mVal);
    mVal.setHorizontalAlignment(JTextField.CENTER);
    levelsAdjPanel.add(new JLabel("w: "));
    levelsAdjPanel.add(wVal);
    wVal.setHorizontalAlignment(JTextField.CENTER);

    levelsAdjPanel.add(new JLabel("b < m < w")  );
    levelsAdjPanel.add(levelsAdj);
    additionalFeatures.add(levelsAdjPanel);

    //set image panel layout
    imagePreview = new JScrollPane();
    imagePreview.getViewport().setBackground(Color.DARK_GRAY);
    imagePreview.setBackground(Color.DARK_GRAY);

    //additional features scrollpane
    additionalFeatureScrollPlane = new JScrollPane(additionalFeatures);
    additionalFeatureScrollPlane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    additionalFeatureScrollPlane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    //add panels to frame
    this.add(imagePreview, BorderLayout.CENTER);
//    this.add(additionalFeatures, BorderLayout.EAST);
    this.add(additionalFeatureScrollPlane, BorderLayout.EAST);

    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    updateButtonStates();
    setVisible(true);
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
      JFileChooser fc = new JFileChooser();
      FileFilter filter = new FileNameExtensionFilter("jpg", "jpeg",
              "ppm", "png", "jpg");
      fc.setFileFilter(filter);
      int res = fc.showOpenDialog(GUIViewImpl.this);
      if (res == JFileChooser.APPROVE_OPTION) {
        imageLoaded = true;
        updateButtonStates();
        String filePath = fc.getSelectedFile().getAbsolutePath();
        features.loadImage(filePath, fc.getSelectedFile().getName());
      }
    });

    //save
    save.addActionListener(e -> {
      updateButtonStates();
      JFileChooser fc = new JFileChooser();
      int res = fc.showSaveDialog(GUIViewImpl.this);
      if (res == JFileChooser.APPROVE_OPTION) {
        String filePath = fc.getSelectedFile().getAbsolutePath();
        try {
          features.saveImage(filePath);
          imageLoaded = false;
        } catch (IOException ioe) {
          errorPopup(ioe.getMessage());
        }
      }
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
    blur.addActionListener(evt -> {
      if (!isSplitEnabled) {
        features.blurImage();
      } else if (splitOps == 0) {
        splitOps++;
        updateButtonStates();
        features.toggleSplitView("blur", Integer.parseInt(splitPct.getText()));
      }
    });

    //sharpen image
    sharpen.addActionListener(evt -> {
      if (!isSplitEnabled) {
        features.sharpenImage();
      } else if (splitOps == 0) {
        splitOps++;
        updateButtonStates();
        features.toggleSplitView("sharpen", Integer.parseInt(splitPct.getText()));
      }
    });

    //convert to greyscale
    greyscale.addActionListener(evt -> {
      if (!isSplitEnabled) {
        features.convertGreyscale();
      } else if (splitOps == 0) {
        splitOps++;
        updateButtonStates();
        features.toggleSplitView("greyscale", Integer.parseInt(splitPct.getText()));
      }
    });

    //convert to sepia
    sepia.addActionListener(evt -> {
      if (!isSplitEnabled) {
        features.convertSepia();
      } else if (splitOps == 0) {
        splitOps++;
        updateButtonStates();
        features.toggleSplitView("sepia", Integer.parseInt(splitPct.getText()));
      }
    });

    //run levels adjustment
    levelsAdj.addActionListener(evt -> {
      int b;
      int m;
      int w;
      try {
        b = Integer.parseInt(bVal.getText());
        m = Integer.parseInt(mVal.getText());
        w = Integer.parseInt(wVal.getText());
        if (b > m || m > w || w > 255 || b < 1) {
          throw new NumberFormatException();
        }
        features.runLevelsAdjustment(b, m, w);
      } catch (NumberFormatException nfe) {
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

    //apply split op
    apply.addActionListener(e -> {
      split.setSelected(false);
      splitPct.setText("");
      isSplitEnabled = false;
      splitOps = 0;
      updateButtonStates();
      features.applySplitOp();
    });

    split.addItemListener(e -> {
      int s;
      try {
        s = Integer.parseInt(splitPct.getText());
        if (s > 99 || s < 1) {
          throw new NumberFormatException();
        }
        isSplitEnabled = e.getStateChange() == ItemEvent.SELECTED;
        if (isSplitEnabled) {
          splitView.remove(splitParams);
          splitView.add(apply);
          split.setText("Exit Split Preview");
        } else {
          splitView.remove(apply);
          splitView.add(splitParams);
          splitPct.setText("");
          split.setText("Split Preview");
          features.toggleSplitView("reset", 0);
          splitOps = 0;
        }
        splitView.revalidate();
        splitView.repaint();
        updateButtonStates();
      } catch (NumberFormatException nfe) {
        errorPopup("Split percentage must be an integer between 0-100.");
      }
    });


    //run color correction
    colorCorrect.addActionListener(evt -> {
      if (!isSplitEnabled) {
        features.runColorCorrection();
      } else if (splitOps == 0) {
        splitOps++;
        updateButtonStates();
        features.toggleSplitView("color-correct", Integer.parseInt(splitPct.getText()));
      }
    });

    //toggle split view

    //clear
    clear.addActionListener(evt -> {
      features.clear();
      brighten.setValue(0);
      compression.setValue(0);
    });

    //exit program

    //keyboard events, if we want them
  }

  @Override
  public void displayImage(BufferedImage image, BufferedImage histogram, String displayName) {

    //draw image
    ImageIcon imgIcon = new ImageIcon(image);
    JLabel label = new JLabel(imgIcon);
    imagePreview.setViewportView(label);
    int x = (imagePreview.getWidth() - image.getWidth()) / 2;
    int y = (imagePreview.getHeight() - image.getHeight()) / 2;
    label.setLocation(x, y);
    operationPath.setText(displayName);

    if (!isSplitEnabled) {
      ImageIcon histogramIcon = new ImageIcon(histogram);
      JLabel histogramLabel = new JLabel(histogramIcon);

      JPanel histogramPanel = new JPanel(new BorderLayout());
      histogramPanel.setPreferredSize(new Dimension(256, 256));
      histogramPanel.setMaximumSize(new Dimension(256, 256));
      histogramPanel.add(histogramLabel, BorderLayout.CENTER);

      renderHistogramPanel(this.histogramPanel, histogramPanel);
    } else {
      JLabel messageLabel = new JLabel("Histogram not supported in Split View");
      messageLabel.setHorizontalAlignment(JLabel.CENTER);

      JPanel messagePanel = new JPanel(new BorderLayout());
      messagePanel.setPreferredSize(new Dimension(256, 256));
      messagePanel.setMaximumSize(new Dimension(256, 256));
      messagePanel.add(messageLabel, BorderLayout.CENTER);

      renderHistogramPanel(this.histogramPanel, messagePanel);

    }
    additionalFeatures.revalidate();
    additionalFeatures.repaint();
  }

  //helper method to replace the histogram panel image
  private void renderHistogramPanel(JPanel oldPanel, JPanel newPanel) {
    additionalFeatures.remove(oldPanel);
    additionalFeatures.add(newPanel, 1);
    this.histogramPanel = newPanel;
  }


  //helper function to initialize the GUI's buttons
  private void initButtons() {
    load = new JButton("Load");
    load.setActionCommand("Load Button");

    save = new JButton("Save");
    save.setActionCommand("Save Button");

    clear = new JButton("Clear");
    clear.setActionCommand("Clear Button");

    split = new JToggleButton("Split View");
    split.setActionCommand("Split Button");
    apply = new JButton("Apply Previewed Changes");


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


  private void updateButtonStates() {
    for (JButton jButton : Arrays.asList(hFlip, vFlip, rComp, gComp, bComp)) {
      jButton.setEnabled(!isSplitEnabled && imageLoaded && splitOps == 0);
    }
    splitPct.setEnabled(!isSplitEnabled && imageLoaded && splitOps == 0);
    brighten.setEnabled(!isSplitEnabled && imageLoaded && splitOps == 0);
    compression.setEnabled(!isSplitEnabled && imageLoaded && splitOps == 0);
    split.setEnabled(imageLoaded);
    save.setEnabled(imageLoaded);
    clear.setEnabled(imageLoaded);
    colorCorrect.setEnabled(imageLoaded && splitOps == 0);
    blur.setEnabled(imageLoaded && splitOps == 0);
    greyscale.setEnabled(imageLoaded && splitOps == 0);
    sepia.setEnabled(imageLoaded && splitOps == 0);
    sharpen.setEnabled(imageLoaded && splitOps == 0);
    levelsAdj.setEnabled(imageLoaded && splitOps == 0);
    splitPct.setEnabled(imageLoaded && !isSplitEnabled && splitOps == 0);
    bVal.setEnabled(imageLoaded && splitOps == 0);
    mVal.setEnabled(imageLoaded && splitOps == 0);
    mVal.setEnabled(imageLoaded && splitOps == 0);
    wVal.setEnabled(imageLoaded && splitOps == 0);
  }

  private void errorPopup(String message) {
    JOptionPane.showMessageDialog(null, message, "Invalid input!",
            JOptionPane.ERROR_MESSAGE);
  }

}
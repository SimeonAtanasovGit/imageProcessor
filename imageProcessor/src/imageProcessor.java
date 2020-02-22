import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class imageProcessor extends javax.swing.JFrame {
//JMenuItems
    private boolean isThereNewImage = false;
    private boolean isImageProcessed = false;
    public imageProcessor() {
        initComponents();
        openImageMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Open Image".equals(e.getActionCommand())) {
                    openButton.doClick(0);
                }
            }
        });
        
        saveImageMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Save Image".equals(e.getActionCommand())) {
                    saveButton.doClick(0);
                }
            }
        });
        
        quitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Quit".equals(e.getActionCommand())) {
                    System.exit(NORMAL);
                }
            }
        });
        
        originalImage.setImage("Startup.jpg");
        processedImage.setImage("Startup2.jpg");
// OPEN BUTTON STRUCTURE
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("Which picture would you like to filter?");
                JFileChooser fileChooserOpen = new JFileChooser();
                fileChooserOpen.setCurrentDirectory(new File("./images"));
                fileChooserOpen.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        String[] acceptedExtensions = {".jpg", ".jpeg", ".png",
                                                       ".gif", ".tif", ".tiff" };
                        for (String ext : acceptedExtensions) {
                            if (pathname.getName().toLowerCase().endsWith(ext)) {
                                return true;
                            }
                        }
                        return false;
                    }
                    
                    @Override
                    public String getDescription() {
                        return "Image files";
                    }
                });
                
                int returnVal = fileChooserOpen.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooserOpen.getSelectedFile();
                    originalImage.setImage(file.getPath());
                    messageLabel.setText("Image selected! Select which filter you would like to apply!");
                    isThereNewImage = true;
                    isImageProcessed = false;
                    processedImage.setImage("Startup2.jpg");
                } 
            }
        });
// SAVE BUTTON STRUCTURE
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isImageProcessed) {
                    messageLabel.setText("Saving image! Select dirrectory and pick a name!");
                    JFileChooser fileChooserSave = new JFileChooser();
                    fileChooserSave.setCurrentDirectory(new File("./images"));
                    fileChooserSave.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            String[] acceptedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".tif", ".tiff" };
                            for (String ext : acceptedExtensions) {
                                if (pathname.getName().toLowerCase().endsWith(ext)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    
                        @Override
                        public String getDescription() {
                            return "Image files";
                        }
                    });

                    if (fileChooserSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooserSave.getSelectedFile();
                        try {
                            ImageIO.write(processedImage.getBufferedImage(), "jpg", file);
                            messageLabel.setText("Image Saved Succesfuly!");
                        } catch (IOException ex) {
                        }
                    }
                }
                else {
                    messageLabel.setText("The image was not processed! You cannot save this file!");
                }        
            } 
        });
// APPLY BUTTON STRUCTURE
        applyButton.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isThereNewImage) {
                    if((cb1.isSelected())&&(!cb2.isSelected())&&(!cb3.isSelected())&&(!cb4.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb1.isSelected())&&(cb2.isSelected())&&(!cb3.isSelected())&&(!cb4.isSelected())){
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGaussianBlur(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb1.isSelected())&&(cb2.isSelected())&&(cb3.isSelected())&&(!cb4.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGaussianBlur(processedImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb1.isSelected())&&(cb2.isSelected())&&(cb3.isSelected())&&(cb4.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGaussianBlur(processedImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
               
                    else if((cb2.isSelected())&&(!cb1.isSelected())&&(!cb3.isSelected())&&(!cb4.isSelected())) {
                        processedImage.setImage(applyGaussianBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb2.isSelected())&&(cb3.isSelected())&&(!cb1.isSelected())&&(!cb4.isSelected())) {
                        processedImage.setImage(applyGaussianBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                                
                    else if((cb2.isSelected())&&(cb3.isSelected())&&(cb4.isSelected())&&(!cb1.isSelected())) {
                        processedImage.setImage(applyGaussianBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }

                    else if((cb3.isSelected())&&(!cb1.isSelected())&&(!cb2.isSelected())&&(!cb4.isSelected())) {
                        processedImage.setImage(applyEdgeDetection(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                              
                    else if((cb3.isSelected())&&(cb4.isSelected())&&(!cb1.isSelected())&&(!cb2.isSelected())) {
                        processedImage.setImage(applyEdgeDetection(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb3.isSelected())&&(cb4.isSelected())&&(cb1.isSelected())&&(!cb2.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb3.isSelected())&&(!cb4.isSelected())&&(cb1.isSelected())&&(!cb2.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyEdgeDetection(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }

                    else if((cb4.isSelected())&&(!cb1.isSelected())&&(!cb2.isSelected())&&(!cb3.isSelected())) {
                        processedImage.setImage(applyGammaCorrection(originalImage.getBufferedImage(), sliderGamma.getValue()));
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb4.isSelected())&&(cb1.isSelected())&&(!cb2.isSelected())&&(!cb3.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue())); 
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                    
                    else if((cb4.isSelected())&&(cb1.isSelected())&&(cb2.isSelected())&&(!cb3.isSelected())) {
                        processedImage.setImage(applyBoxBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGaussianBlur(processedImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue())); 
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else if((cb4.isSelected())&&(!cb1.isSelected())&&(cb2.isSelected())&&(!cb3.isSelected())) {
                        processedImage.setImage(applyGaussianBlur(originalImage.getBufferedImage()));
                        processedImage.setImage(applyGammaCorrection(processedImage.getBufferedImage(), sliderGamma.getValue())); 
                        processedImage.setImage(convertToGreyScale(processedImage.getBufferedImage()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }   
                
                    else if((!cb4.isSelected())&&(!cb1.isSelected())&&(!cb2.isSelected())&&(!cb3.isSelected())&&(sliderGamma.getValue() != 100)) {
                        processedImage.setImage(applyGammaCorrection(originalImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("Filtering applied! Save the image or try new filters on the original image!");
                        isImageProcessed = true;
                    }
                
                    else {
                        processedImage.setImage(applyGammaCorrection(originalImage.getBufferedImage(), sliderGamma.getValue()));
                        messageLabel.setText("No filters were applied");
                        isImageProcessed = false;
                    }
                }
                else {
                    messageLabel.setText("No image was filtered nor selected!");
                }
            }             
        });  
    }
// METHODS FOR FILTERING
    public static BufferedImage applyBoxBlur(BufferedImage img) {
        final float boxKernel[][] = new float[][] {
            {0.11111111f, 0.11111111f, 0.11111111f},
            {0.11111111f, 0.11111111f, 0.11111111f},
            {0.11111111f, 0.11111111f, 0.11111111f},
        };
        
        return applyConvolutionFilter(img, boxKernel);
    }

    public static BufferedImage applyGaussianBlur(BufferedImage img) {
        final float gaussianKernel[][] = new float[][] {
            {0.00390625f, 0.01562500f, 0.02343750f, 0.01562500f, 0.00390625f},
            {0.01562500f, 0.06250000f, 0.09375000f, 0.06250000f, 0.01562500f},
            {0.02343750f, 0.09375000f, 0.14062500f, 0.09375000f, 0.02343750f},
            {0.01562500f, 0.06250000f, 0.09375000f, 0.06250000f, 0.01562500f},
            {0.00390625f, 0.01562500f, 0.02343750f, 0.01562500f, 0.00390625f},
        };

        return applyConvolutionFilter(img, gaussianKernel);
    }
    
    public static BufferedImage applyEdgeDetection(BufferedImage img) {
        final float edgeKernel[][] = new float[][] {
            {-1f, -1f, -1f},
            {-1f, 8f, -1f},
            {-1f, -1f, -1f},            
        };
        
        return applyConvolutionFilter(img, edgeKernel);
    }
    
    public static BufferedImage applyGammaCorrection(BufferedImage img, double value) {
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int j = 0; j < img.getHeight(); ++j) {
                for (int i = 0; i < img.getWidth(); ++i) {
                    Color color = new Color(img.getRGB(i, j));
                    float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                    float hue = hsb[0];
                    float saturation = hsb[1];
                    float brightness = hsb[2];
                    brightness = (float)Math.pow(brightness, value/100);
      
                    output.setRGB(i, j, Color.HSBtoRGB(hue, saturation, brightness));
                }
            }
        
        return output;
    }

    public static BufferedImage convertToGreyScale(final BufferedImage img) {
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int j=0; j<img.getHeight(); ++j){
            for (int i=0; i<img.getWidth(); ++i){
                int argb = img.getRGB(i, j);
                
                int alpha = (argb & 0xFF000000) >> 24;
                int red   = (argb & 0x00FF0000) >> 16;
                int green = (argb & 0x0000FF00) >> 8;
                int blue  = (argb & 0x000000FF);

                int grey = (red + green + blue) / 3;
                
                int greyARGB = alpha << 24 | grey << 16 | grey << 8 | grey;
                
                output.setRGB(i, j, greyARGB);
            }
        }
        
        return output;
    }
    
    private static BufferedImage applyConvolutionFilter(BufferedImage img, float[][] kernel) {
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int j=0; j<img.getHeight(); ++j) {
            for (int i=0; i<img.getWidth(); ++i) {
                Color pixelColor = applyKernel(img, kernel, i, j);
                output.setRGB(i, j, pixelColor.getRGB());                
            }
        }
        
        return output;
    }
    
    private static Color applyKernel(BufferedImage img, float[][] kernel, int row, int column) {
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;

        int minIndexH = -(kernel.length / 2);
        int maxIndexH = minIndexH + kernel.length;
        int minIndexV = -(kernel[0].length / 2);
        int maxIndexV = minIndexV + kernel[0].length;
        
        for (int i = minIndexH; i < maxIndexH; ++i) {
            for (int j=minIndexV; j<maxIndexV; ++j) {
                int indexH = wrapHorizontalIndex(img, row + i);
                int indexV = wrapVerticalIndex(img, column + j);
                Color col = new Color(img.getRGB(indexH, indexV));

                red += col.getRed() * kernel[i-minIndexH][j-minIndexV];
                green += col.getGreen() * kernel[i-minIndexH][j-minIndexV];
                blue += col.getBlue() * kernel[i-minIndexH][j-minIndexV];
            }
        }

        red = Math.min(Math.max(red, 0.0f), 255.0f);
        green = Math.min(Math.max(green, 0.0f), 255.0f);
        blue = Math.min(Math.max(blue, 0.0f), 255.0f);
        
        return new Color((int) red, (int) green, (int) blue);
    }
    
    private static int wrapHorizontalIndex(BufferedImage img, int i) {
        return (i + img.getWidth()) % img.getWidth();
    }

    private static int wrapVerticalIndex(BufferedImage img, int i) {
        return (i + img.getHeight()) % img.getHeight();
    }
// END OF CUSTOMIZATION OF PROGRAM

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        boxFilterLabel = new javax.swing.JLabel();
        gaussianFilterLabel = new javax.swing.JLabel();
        edgeDetectorFilterLabel = new javax.swing.JLabel();
        gammaCorrectionLabel = new javax.swing.JLabel();
        convertToGreyScalesLabel = new javax.swing.JLabel();
        cb1 = new javax.swing.JCheckBox();
        cb2 = new javax.swing.JCheckBox();
        cb3 = new javax.swing.JCheckBox();
        cb4 = new javax.swing.JCheckBox();
        sliderGamma = new javax.swing.JSlider();
        originalImage = new ImagePanel();
        processedImage = new ImagePanel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openImageMenu = new javax.swing.JMenuItem();
        saveImageMenu = new javax.swing.JMenuItem();
        quitMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Image Processor");

        openButton.setText("Open");

        saveButton.setText("Save");

        applyButton.setText("Apply");

        messageLabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageLabel.setText("Hello to Image Processor! Please pick an image and try the filters bellow! ");

        boxFilterLabel.setText("Box Filter:");

        gaussianFilterLabel.setText("Gaussian Filter:");

        edgeDetectorFilterLabel.setText("Edge Detector Filter:");

        gammaCorrectionLabel.setText("Gamma Correction:");

        convertToGreyScalesLabel.setText("Convert to Grey Scales:");

        sliderGamma.setMaximum(200);
        sliderGamma.setMinimum(1);
        sliderGamma.setValue(100);

        javax.swing.GroupLayout originalImageLayout = new javax.swing.GroupLayout(originalImage);
        originalImage.setLayout(originalImageLayout);
        originalImageLayout.setHorizontalGroup(
            originalImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        originalImageLayout.setVerticalGroup(
            originalImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout processedImageLayout = new javax.swing.GroupLayout(processedImage);
        processedImage.setLayout(processedImageLayout);
        processedImageLayout.setHorizontalGroup(
            processedImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        processedImageLayout.setVerticalGroup(
            processedImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        openImageMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openImageMenu.setText("Open Image");
        jMenu1.add(openImageMenu);

        saveImageMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveImageMenu.setText("Save Image");
        jMenu1.add(saveImageMenu);

        quitMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        quitMenu.setText("Quit");
        jMenu1.add(quitMenu);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(applyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(convertToGreyScalesLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cb4))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(edgeDetectorFilterLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cb3))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(gaussianFilterLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cb2))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(boxFilterLabel)
                                            .addGap(97, 97, 97)
                                            .addComponent(cb1)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(gammaCorrectionLabel)
                                        .addGap(43, 43, 43)
                                        .addComponent(sliderGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(originalImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(openButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(processedImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(originalImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(processedImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(openButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(messageLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxFilterLabel)
                    .addComponent(cb1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gaussianFilterLabel)
                    .addComponent(cb2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edgeDetectorFilterLabel)
                    .addComponent(cb3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gammaCorrectionLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb4)
                    .addComponent(convertToGreyScalesLabel))
                .addGap(18, 18, 18)
                .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(imageProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(imageProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(imageProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(imageProcessor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new imageProcessor().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JLabel boxFilterLabel;
    private javax.swing.JCheckBox cb1;
    private javax.swing.JCheckBox cb2;
    private javax.swing.JCheckBox cb3;
    private javax.swing.JCheckBox cb4;
    private javax.swing.JLabel convertToGreyScalesLabel;
    private javax.swing.JLabel edgeDetectorFilterLabel;
    private javax.swing.JLabel gammaCorrectionLabel;
    private javax.swing.JLabel gaussianFilterLabel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openImageMenu;
    private ImagePanel originalImage;
    private ImagePanel processedImage;
    private javax.swing.JMenuItem quitMenu;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveImageMenu;
    private javax.swing.JSlider sliderGamma;
    // End of variables declaration//GEN-END:variables
}
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class ImageViewer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
    
    /**
     * Creates the window that shows the image.
     * @throws IOException 
     */
    public ImageViewer() throws IOException {
        super("Cosy Viewer 2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Image img = ImageIO.read(new File("src/example1.png")).getScaledInstance(540, 540, Image.SCALE_SMOOTH);
        image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, null);
        
        getContentPane().add(new JComponent() {
			private static final long serialVersionUID = 2L;

			public Dimension getPreferredSize() {
                    return new Dimension(image.getWidth(), image.getHeight(null));
            }
            
            public void paintComponent(Graphics g) {
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                }
            }
        });
        pack();
        setVisible(true);
    }
}
package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


import javax.swing.JFrame;


import display.Display;
import render.ImageRender;


public class Main{
    

	 private static void createAndShowGUI() {
	    // Create and set up the window.
	    JFrame frame = new JFrame("Gooz Sprites v1.0");
	    // Center on screen
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = (int) (d.getWidth() * 0.92);
	    int height = (int) (d.getHeight() * 0.92);
	    int x = (int) ((d.getWidth() - width) * 0.5);
	    int y = (int) ((d.getHeight() - height) * 0.5);
	    
	    ImageRender render = new ImageRender();
	    BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		render.clear();
		render.renderButton(7,-1);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}
	    
	    frame.setIconImage(image);
	    frame.setBounds(x, y, width, height);
	    frame.setResizable(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
		
	    Display display = new Display();
		

	    // We only add components to the content pane, NEVER directly via
	    // add(...)!!!
	    frame.getContentPane().add(display,BorderLayout.CENTER);
	    // Display the window.
	   
	    frame.setVisible(true);
	    
	    
	}

	public static void main(String[] args) {
		
	    // Schedule a job for the event-dispatching thread:
	    // creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            createAndShowGUI();
	        }
	    });
	}
	
	
	}

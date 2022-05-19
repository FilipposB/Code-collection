package gui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import gui.display.Display;

public class Frame {

	private static void createAndShowGUI() {
	    // Create and set up the window.
	    JFrame frame = new JFrame("Binary Image Cryptography v0");
	    // Center on screen
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = 900;
	    int height = 600;
	    int x = (int) ((d.getWidth() - width) * 0.5);
	    int y = (int) ((d.getHeight() - height) * 0.5);
	    
	    
	    frame.setBounds(x, y, width, height);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		
	    Display display = new Display();
		

	    
	    frame.getContentPane().add(display,BorderLayout.CENTER);
	   
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

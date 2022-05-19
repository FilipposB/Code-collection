package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import display.Display;

public class Frame {

	private static void createAndShowGUI() {
	    // Create and set up the window.
	    JFrame frame = new JFrame("Astra Pixel Art v0");
	    // Center on screen
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = (int) (d.getWidth() * 0.92);
	    int height = (int) (d.getHeight() * 0.92);
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

package colorEvolve;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import display.Display;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
	    frame.setBounds(0, 0, 600, 600);
	    frame.setResizable(false);
	    frame.setUndecorated(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
		
	    Display display = new Display();
		

	    // We only add components to the content pane, NEVER directly via
	    // add(...)!!!
	    frame.getContentPane().add(display,BorderLayout.CENTER);
	    // Display the window.
	   
	    frame.setVisible(true);	}

}

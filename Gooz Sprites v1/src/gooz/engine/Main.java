package gooz.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import gooz.screen.Screen;

public class Main extends Canvas {
	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double screenWid = screenSize.getWidth();
 
	public static int width = (int) screenWid / 10 * 9;
	//public static int width = 1200;
	public static int height = width / 16 * 9;

	public static String title = "Gooz v1.0";

	public static void main(String args[]) {
		JFrame obj = new JFrame();
		Screen running = new Screen();
		obj.setBounds(0,0,width, height);

		obj.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		obj.setLocation(dim.width / 2 - obj.getSize().width / 2, dim.height / 2 - obj.getSize().height / 2);
		obj.setBackground(Color.WHITE);
		obj.setTitle(title);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setVisible(true);
		obj.add(running);
		

	}
	
	

}

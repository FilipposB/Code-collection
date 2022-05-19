package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import game.screen.Screen;

public class Main extends Canvas {
	private static final long serialVersionUID = 1L;

	public static int width = 1506;
	public static int height = (width+23) / 16 * 9;

	public static String  title = "Gooz";

	public static void main(String args[]) {
		JFrame obj = new JFrame();
		Screen running = new Screen();
		obj.setBounds(0, 0, width, height);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setBackground(Color.WHITE);
		obj.setTitle(title);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		obj.setLocation(dim.width / 2 - obj.getSize().width / 2, dim.height / 2 - obj.getSize().height / 2);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(running);
		
	}

}

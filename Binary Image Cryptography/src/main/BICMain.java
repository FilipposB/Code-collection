package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import decryption.BICDecryption;
import encryption.BICEncryption;

public class BICMain extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";

	// JPanel Options
	private JPanel panel;
	private int width = 1280;
	private int height = 720;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	private static boolean run = false;
	
	private static char characters[] = { 'E', 'T', 'A', 'O', 'I', 'N', 'S', 'R', 'H', 'D', 'L', 'U', 'C', 'M', 'F', 'Y',
			'W', 'G', 'P', 'B', 'V', 'K', 'X', 'Q', 'J', 'Z' };
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	// Game Loop Constructor
	public BICMain() {
		frame = new JFrame(title);
		panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
		setBounds(0, 0, width, height);
		panel.add(this);
		setIgnoreRepaint(true);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Key Bindings
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_0) {
					run = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}

	// Starts Game Loop
	private synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	// Stops Game Loop
	private synchronized void stop() {
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// Game Loop
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int ticks = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				frame.setTitle("FPS : " + frames + " | TICKS : " + ticks);
				timer += 1000;
				frames = 0;
				ticks = 0;

			}

			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}
		stop();
	}

	// Game Update
	private void tick() {

	}

	// Game Render
	private void render() {

		// Sets Buffered Strategy
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		if (run) {
			// clears screen
			g.clearRect(0, 0, width, height);
			run = false;
			BufferedImage bi;
			try {

				bi = ImageIO.read(new File("image.png"));
				File outputfile = new File("before.png");
				ImageIO.write(bi, "png", outputfile);
				bi = BICEncryption.convertBufferedImageToBIC(bi);
				bi = BICEncryption.encodeToImage(bi, 4, 4, "hgahjdfgjhkjshkjd", characters);
				bi = BICEncryption.encodeToImage(bi, 4, 4, "hgahjdfgjhkjshkjd", alphabet);

				outputfile = new File("after.png");
				ImageIO.write(bi, "png", outputfile);
				g.drawImage(bi, 0, 0, this);
				System.out.println("Decrypted :"+BICDecryption.decryptBIC(bi, BICEncryption.getRGBEncryption(), 4, 4, characters)+"::");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		g.dispose();
		bs.show();
	}

	// Main
	public static void main(String[] args) {

		BICMain game = new BICMain();

		game.start();
	}

}
package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

import neuralNetwork.NeuralNetwork;
import neuralNetwork.Tools;



public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private static int width = 1280;
	private static int height = 720;
	private static String title = "Game Loop";

	// Game Loop Stuff
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	// Frame Rate Options
	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	
	NeuralNetwork net;

	// Game Loop Constructor
	public Main() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow(false);
		setFocusTraversalKeysEnabled(false);

		// Key Bindings
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode()==KeyEvent.VK_2){
					try {
						System.out.println("lol");

						Tools.saveNetwork(net,"src/k.dat");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_1){
					try {
						System.out.println("lol");
						net = new NeuralNetwork(Tools.loadNetwork("birdBrain.dat"));
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
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
		
		try {
			net = new NeuralNetwork(Tools.loadNetwork("birdBrain.dat"));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
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
		long lastTime = System.nanoTime();
		long lastRender = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastTime;
			lastTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			lastRender += updateLength;
			frames++;

			if (lastRender >= 1000000000) {
				frame.setTitle(" Game Loop FPS: " + frames);
				lastRender = 0;
				frames = 0;
			}

			tick(delta);

			render();

		}
		stop();
	}

	// Game Update
	private void tick(double delta) {

		double input[]={0.8929,0.175,0.0671,0.2814};
		net.updateNetwork(input);
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
		
		// Displays Screen
		g.clearRect(0, 0, width, height);
		
		net.drawNetwork(g,100,100);
		g.dispose();
		bs.show();


	}
	
	// Main
	public static void main(String[] args) {

		Main game = new Main();
		game.frame.setResizable(true);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.frame.getKeyListeners();
		game.frame.setFocusable(true);
		game.frame.requestFocusInWindow();

		game.start();
	}

}
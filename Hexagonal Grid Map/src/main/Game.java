package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 1920;
	private static int height = width / 16 * 9;
	private static String title = "Grid Game";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	private int size=50;

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}

	private synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	private synchronized void stop() {

		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

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
				frame.setTitle("Game FPS: " + frames);
				lastRender = 0;
				frames = 0;
			}

			tick(delta);

			render();

		}
		stop();
	}

	private void tick(double delta) {
		// Check If Game Over

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Clear Screen
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GRAY.brighter());
		g.drawLine(0, 0, getWidth(), 0);
		
		g.setColor(Color.BLACK);
		for (int i=0;i<10;i++){
			g.drawPolygon(makeHexagon(i*(size*2+size/2),size));

		}

		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {

		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setUndecorated(true);
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

	private Polygon makeHexagon(int x,int y) {
        Polygon polygon = new Polygon();

        for (int i = 0; i < 6; i++)
            polygon.addPoint((int) (x + size * Math.cos(i * 2 * Math.PI / 6)),
              (int) (y + size * Math.sin(i * 2 * Math.PI / 6)));     
        return polygon;
    }


}

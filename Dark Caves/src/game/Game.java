package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Entity;
import entities.PlayerEntity;
import world.World;

public class Game extends Canvas implements Runnable {
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

	// Player
	public double worldX = 0;
	public double worldY = 0;

	// Tile Size
	public final int tileWidth = 32;
	public final int tileHeight = 32;
	
	//World
	public World world;

	private List<Entity> entities;

	// Game Loop Constructor
	public Game() {
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
				if (e.getKeyCode() == KeyEvent.VK_A) {
					((PlayerEntity) entities.get(0)).doAction(1,true);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					((PlayerEntity) entities.get(0)).doAction(0,true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					((PlayerEntity) entities.get(0)).doAction(1,false);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					((PlayerEntity) entities.get(0)).doAction(0,false);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});

		initialize();
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
				//Thread.sleep(10);
			} catch (Exception e) {
			}

		}
		stop();
	}

	// Game Update
	private void tick() {
		for (Entity e:entities){
			if(world.checkCollision(e.getHitBox())==1)e.isOnGround=true;
			e.tick();
		}
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

		// clears screen
		g.clearRect(0, 0, width, height);

		world.drawMap(worldX, worldY, width, height, g);

		// paint entities
		for (Entity e : entities)
			e.draw(g);

		bs.show();
		//g.dispose();

	}

	private void initialize() {
		entities = new ArrayList<Entity>();
		world = new World(this);
		// Player
		entities.add(new PlayerEntity(0, -400, 32, 64, 200, Color.BLACK, this));

	}

	// Main
	public static void main(String[] args) {

		Game game = new Game();

		game.start();
	}

}

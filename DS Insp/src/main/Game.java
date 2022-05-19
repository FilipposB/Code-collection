package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import camera.Camera;
import people.Person;
import people.Player;
import world.Floor;
import world.World;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 1920;
	private static int height = width / 16 * 9;
	private static String title = "Game FPS: 0";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	// World
	private World world;

	// Camera
	private Camera camera;

	// Floor
	private Floor floor;
	private List<Integer> colFloor = new ArrayList<Integer>();
	
	//Colors
	private Color letters=Color.GREEN;
	private Color map = Color.DARK_GRAY;
	// Player
	private Player player;
	

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow(false);
		setFocusTraversalKeysEnabled(true);

		player = new Player(193, -120, 15, 0, 64, 64, 34, 64, 0.1,false, 0);

		world = new World();
		world.loadWorld();
		camera = new Camera(player.getXPos(), player.getYPos());

		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					player.move[0] = true;
					if (!player.getLeft())player.setLeft(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					player.move[1] = true;
					if (player.getLeft())player.setLeft(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					player.move[2] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					player.move[3] = true;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					player.move[0] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					player.move[1] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					player.move[2] = false;
					player.jump = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					player.move[3] = false;
				}
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
		int lastFps = 0;
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastTime;
			lastTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			lastRender += updateLength;
			frames++;

			if (lastRender >= 1000000000) {
				lastFps = frames;
				lastRender = 0;
				frames = 0;
			}

			tick(delta);

			render(lastFps);

		}
		stop();
	}

	private void tick(double delta) {
		touchFloor(player);
		player.update(delta, touchGround());
		touchFloor(player);
		touchGround();
		camera.cameraMove(player.getXPos(), player.getYPos(), delta);
		animateHero(delta);
	}

	private void render(int frames) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		int currentX=camera.getX();
		int currentY=camera.getY();

		g.clearRect(0, 0, width, height);

		g.setColor(map);
		for (int i = 0; i < world.getFloorSize(); i++) {
			floor = world.getFloor(i);
			g.fillRect(floor.getX() - currentX, floor.getY() - currentY, floor.getWidth(), floor.getHeight());
		}

		if (player.getLeft())
		g.drawImage(world.getSprite(player.getSprite()), player.getXPos() - currentX +player.getSpriteWidth(),
				player.getYPos() -currentY, -player.getSpriteWidth(), player.getSpriteHeight(), this);
		else	g.drawImage(world.getSprite(player.getSprite()), player.getXPos() - currentX,
				player.getYPos() - currentY, player.getSpriteWidth(), player.getSpriteHeight(), this);
		// g.fillRect(player.getXPos()-camera.getX(),
		// player.getYPos()-camera.getY(), player.getSpriteWidth(),
		// player.getSpriteHeight());

		g.setColor(letters);
		g.setFont(g.getFont().deriveFont(22.0f));
		g.drawString("FPS : " + frames, 10, 20);
		
		//g.drawRect(player.getXHb()-camera.getX(), player.getYHb()-camera.getY(), player.getHitBoxWidth(), player.getHitBoxHeight());
		
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
		game.frame.setBackground(Color.BLACK);
		game.frame.requestFocusInWindow();

		game.start();
	}

	private void animateHero(double delta) {

		if (player.getPunchAnimation()){
			if ((delta > 12 || player.getAnimationTimer() > 5.6)) {
				player.animate();
				player.setAnimationTimer(0);
			}
			else {
				player.setAnimationTimer(player.getAnimationTimer()+delta);
			}
		}
		else if ((player.getMotion()) && (delta > 12 || player.getAnimationTimer() > 6)) {
			player.animate();
			player.setAnimationTimer(0);
		} else if (!player.getMotion()) {
			player.setSprite(0);
		} else {
			player.setAnimationTimer(player.getAnimationTimer()+delta);
		}
	}

	private void touchFloor(Person player) {
		colFloor.clear();
		Rectangle heroRect = new Rectangle(player.getXHb(), player.getYHb(), player.getHitBoxWidth(),
				player.getHitBoxHeight());
		for (int i = 0; i < world.getFloorSize(); i++) {
			floor = world.getFloor(i);
			Rectangle floorRect = new Rectangle(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
			if (heroRect.intersects(floorRect)) {
				colFloor.add(i);
			}
		}
	}

	private boolean touchGround() {
		Boolean res = false;
		for (int i = 0; i < colFloor.size(); i++) {
			floor = world.getFloor(colFloor.get(i));
			if ((floor.getY() >= player.getOldY() + player.getHitBoxHeight() - 1)) {
				player.setYPos(floor.getY() - player.getHitBoxHeight() + 1);
				res = true;
			} else if ((floor.getY() + floor.getHeight() <= player.getOldY()+1) && !floor.getPass()) {
				player.setYPos(floor.getY() + floor.getHeight());
				player.setYVel(0);
			} else if (!floor.getPass()) {
				player.setXPos(player.getOldX());
				player.setXVel(0);
			}
		}

		return res;
	}

}

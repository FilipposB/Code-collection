package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import hero.Hero;
import level.LevelChooser;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 1280;
	private static int height = width / 16 * 9;
	private static String title = "TDG";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	// Level Chooser
	private LevelChooser level = new LevelChooser();

	// Hero
	Hero hero;

	// Timer
	int tickTimerSprite = 0;

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);
		level.level0(true);
		hero = level.getHero();

		addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					hero.setMove(0, false);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					hero.setMove(1, false);
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					hero.setMove(2, false);
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					hero.setMove(3, false);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					hero.setMove(0, true);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					hero.setMove(1, true);
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					hero.setMove(2, true);
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					hero.setMove(3, true);
				}
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
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 30.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			boolean shouldRender = false;
			while (delta >= 1) {
				tick();
				render(frames);
				frames++;
				ticks++;
				delta--;
				shouldRender = true;
			}

			if (shouldRender) {
				render(frames);
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "  |   " + ticks + " ticks, " + frames + " fps");
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		hero.update();
		animateHero();

	}

	private void render(int frames) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

		// Display Sprite
		if (hero.getDirection()==0 || hero.getDirection()==3){
			g.drawImage(level.getSprite(hero.getSprite()), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(),
					null);
		}else if (hero.getDirection()==1){
			g.drawImage(level.getSprite(hero.getSprite()), hero.getX() + hero.getWidthHitBox() + hero.getXHitBox() * 2,
					hero.getY(), -hero.getWidth(), hero.getHeight(), null);
		}
		else if (hero.getDirection()==2){
			g.drawImage(level.getSprite(hero.getSprite()), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(),
					null);
		}

		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {

		Game game = new Game();
		game.frame.setResizable(false);
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

	private void animateHero() {
		if (hero.getMotion() && tickTimerSprite < 60) {
			tickTimerSprite++;
		} else {
			tickTimerSprite = 0;
			hero.setSprite(hero.getSpriteDisplay());
		}
		if (hero.getMotion() && tickTimerSprite % 3 == 0) {
			hero.setSprite(hero.getSprite() + 1);
		}
	}
}

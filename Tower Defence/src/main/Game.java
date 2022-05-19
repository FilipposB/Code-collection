package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import defenses.Bullet;
import defenses.Tower;
import people.Minion;
import world.Coordinates;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private int width = 1280;
	private int height = width / 16 * 9;

	private static String title = "Game FPS: 0";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	// colors
	private Color red = Color.RED;
	private Color green = Color.GREEN;
	private Color yellow = Color.YELLOW;
	private Color black = Color.BLACK;
	private Color purple = Color.MAGENTA;
	private Color blue = Color.BLUE;
	private Color cyan = Color.CYAN;

	// editor
	private boolean editorMode = false;
	private List<Coordinates> coords = new ArrayList<Coordinates>();
	private List<Coordinates> towerPosts = new ArrayList<Coordinates>();
	private List<Tower> towers = new ArrayList<Tower>();
	private List<Minion> minions = new ArrayList<Minion>();
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private int chosenCoord = -1;
	private int chosenPost = -1;

	private boolean startingPoint = true;
	private boolean endingPoint = false;
	private boolean delete = false;
	private boolean move = false;
	private boolean setPost = false;

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
				if (e.getKeyCode() == KeyEvent.VK_E) {
					editorMode = !editorMode;
				}
				if (e.getKeyCode() == KeyEvent.VK_1) {
					if (editorMode) {
						startingPoint = true;
						endingPoint = false;
						delete = false;
						move = false;
						setPost = false;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_2) {
					if (editorMode && coords.size() > 0) {
						endingPoint = true;
						startingPoint = false;
						delete = false;
						move = false;
						setPost = false;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_3) {
					if (editorMode) {
						setPost = true;
						endingPoint = false;
						startingPoint = false;
						delete = false;
						move = false;
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (editorMode) {
						delete = !delete;
						move = false;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_F) {
					if (editorMode) {
						move = !move;
						delete = false;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					if (coords.size() > 1) {
						minions.add(new Minion(coords.get(0), 2.5, 10));
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (editorMode) {
					if (delete) {
						for (int i = coords.size() - 1; i >= 0; i--) {
							if (intersect(20, coords.get(i).getX(), e.getX(), coords.get(i).getY(), e.getY())) {
								coords.remove(i);
								break;
							}
						}
						if (chosenCoord == -1)
							for (int i = towerPosts.size() - 1; i >= 0; i--) {
								if (intersect(20, towerPosts.get(i).getX(), e.getX(), towerPosts.get(i).getY(),
										e.getY())) {
									towerPosts.remove(i);
									break;
								}
							}

					} else if (move) {
						for (int i = coords.size() - 1; i >= 0; i--) {
							if (intersect(20, coords.get(i).getX(), e.getX(), coords.get(i).getY(), e.getY())) {
								chosenCoord = i;
								break;
							}
						}
						if (chosenCoord == -1)
							for (int i = towerPosts.size() - 1; i >= 0; i--) {
								if (intersect(20, towerPosts.get(i).getX(), e.getX(), towerPosts.get(i).getY(),
										e.getY())) {
									chosenPost = i;
									break;
								}
							}
					} else if (setPost) {
						towerPosts.add(new Coordinates(e.getX(), e.getY()));
					} else {
						if (startingPoint && !coords.isEmpty()) {
							coords.add(0, new Coordinates(e.getX(), e.getY()));
						} else if (startingPoint == endingPoint) {
							coords.add(coords.size() - 1, new Coordinates(e.getX(), e.getY()));
						} else
							coords.add(new Coordinates(e.getX(), e.getY()));

					}
				} else {
					int temp = -1;
					;
					for (int i = towerPosts.size() - 1; i >= 0; i--) {
						if (intersect(20, towerPosts.get(i).getX(), e.getX(), towerPosts.get(i).getY(), e.getY())) {
							temp = i;
							break;
						}
					}
					if (temp != -1) {
						boolean flag = true;
						for (int i = towers.size() - 1; i >= 0; i--) {
							if (towers.get(i).getX() == towerPosts.get(temp).getX()
									&& towers.get(i).getY() == towerPosts.get(temp).getY()) {
								flag = false;
								break;
							}
						}
						if (flag) {
							towers.add(new Tower(towerPosts.get(temp).getX(), towerPosts.get(temp).getY(), 200, 2, 20));
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				chosenCoord = -1;
				chosenPost = -1;
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {

			}

			public void mouseDragged(MouseEvent e) {
				if (move) {
					if (chosenCoord != -1) {
						coords.set(chosenCoord, new Coordinates(e.getX(), e.getY()));

					} else if (chosenPost != -1)
						towerPosts.set(chosenPost, new Coordinates(e.getX(), e.getY()));
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
				title = "Game FPS: " + lastFps;
				frame.setTitle(title);
				lastRender = 0;
				frames = 0;
			}

			tick(delta);

			render();

		}
		stop();
	}

	private void tick(double delta) {

		// Minions Update
		for (int i = minions.size() - 1; i >= 0; i--) {
			if (minions.get(i).isAlive()) {

				for (int j = 0; j < bullets.size(); j++) {

					if (bullets.get(j).getTarget() == i) {

						bullets.get(j).setTargetPosition(minions.get(i).getX(), minions.get(i).getY());
					}
				}
				if (coords.size() - 1 == minions.get(i).getCoordNumber() && minions.get(i).getArrived()) {
					minions.remove(i);
				} else if (minions.get(i).getArrived())
					minions.get(i).setCord(coords.get(minions.get(i).getCoordNumber() + 1));
				else {

					minions.get(i).update(delta);
				}
			}
			else{
				for (int j = bullets.size()-1; j >=0; j--) {
					if (bullets.get(j).getTarget() == i) {
						bullets.remove(j);
					}
				}
				minions.remove(i);
			}
		}
		// Towers Update
		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).update(delta);
			if (towers.get(i).getTarget() == -1 || towers.get(i).getTarget() >= minions.size()) {
				for (int j = 0; j < minions.size(); j++) {
					if (intersect((int) towers.get(i).getRange() / 2, 5, towers.get(i).getX(), minions.get(j).getX(),
							towers.get(i).getY(), minions.get(j).getY())) {
						towers.get(i).setTarget(j);
						break;
					}
				}
			} else if (intersect((int) towers.get(i).getRange() / 2, 5, towers.get(i).getX(),
					minions.get(towers.get(i).getTarget()).getX(), towers.get(i).getY(),
					minions.get(towers.get(i).getTarget()).getY())) {
				if (towers.get(i).canShoot()){
					bullets.add(new Bullet(towers.get(i).getX(), towers.get(i).getY(), towers.get(i).getTarget(), 6,towers.get(i).getDamage()));

				}
			} else {
				towers.get(i).setTarget(-1);
			}
		}
		// Bullet Update
		for (int i = bullets.size() - 1; i >= 0; i--) {
			bullets.get(i).update(delta);
			if (bullets.get(i).hasArrived()){
				if (bullets.get(i).getTarget()<minions.size())minions.get(bullets.get(i).getTarget()).doDamage(bullets.get(i).getDamage());
				bullets.remove(i);
			}
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		do {
			Graphics g = bs.getDrawGraphics();

			try {
				// Drawing Phase
				g.clearRect(0, 0, width, height);
				if (editorMode) {

					if (delete)
						g.setColor(purple);
					else if (move)
						g.setColor(blue);
					else if (endingPoint)
						g.setColor(red);
					else if (startingPoint)
						g.setColor(green);
					else if (setPost) {
						g.setColor(cyan);
					} else
						g.setColor(yellow);
					g.fillRect(0, 0, 20, 20);
					if(editorMode)
					for (int i = 1; i < coords.size(); i++) {
						g.setColor(black);
						g.drawLine(coords.get(i - 1).getX(), coords.get(i - 1).getY(), coords.get(i).getX(),
								coords.get(i).getY());
					}
					for (int i = 0; i < coords.size(); i++) {
						if (i == 0)
							g.setColor(green);
						else if (i == coords.size() - 1)
							g.setColor(red);
						else
							g.setColor(yellow);
						g.fillArc(coords.get(i).getX() - 10, coords.get(i).getY() - 10, 20, 20, 0, 360);

					}

				} else {
					for (int i = 1; i < coords.size(); i++) {
						g.setColor(black);
						g.drawLine(coords.get(i - 1).getX(), coords.get(i - 1).getY(), coords.get(i).getX(),
								coords.get(i).getY());


					}

				}
				g.setColor(red);
				for (int i = 0; i < minions.size(); i++) {
					int x = minions.get(i).getX();
					int y = minions.get(i).getY();
					g.fillArc(x - 5, y - 5, 10, 10, 0, 360);
				}
				g.setColor(cyan);
				for (int i = 0; i < bullets.size(); i++) {
					int x = bullets.get(i).getX();
					int y = bullets.get(i).getY();
					g.fillArc(x - 5, y - 5, 10, 10, 0, 360);
				}
				g.setColor(cyan);
				for (int i = 0; i < towerPosts.size(); i++) {
					g.fillArc(towerPosts.get(i).getX() - 20, towerPosts.get(i).getY() - 20, 40, 40, 0, 360);
				}
				for (int i = 0; i < towers.size(); i++) {
					g.setColor(purple);
					g.fillArc(towers.get(i).getX() - 20, towers.get(i).getY() - 20, 40, 40, 0, 360);
					g.setColor(black);
					g.drawArc(towers.get(i).getX() - (int) towers.get(i).getRange() / 2,
							towers.get(i).getY() - (int) towers.get(i).getRange() / 2, (int) towers.get(i).getRange(),
							(int) towers.get(i).getRange(), 0, 360);
				}

			} finally {
				g.dispose();
			}
			bs.show();
		} while (bs.contentsLost());

	}

	private boolean intersect(int r1, int x1, int x2, int y1, int y2) {
		if (Math.pow(r1 - 1, 2) >= Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) {
			return true;
		}
		return false;
	}

	private boolean intersect(int r1, int r2, int x1, int x2, int y1, int y2) {
		if (Math.pow(r1 - r2, 2) >= Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setUndecorated(false);
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

}

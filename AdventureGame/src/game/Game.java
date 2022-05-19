package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import map.HexMap;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";

	// JPanel Options
	private JPanel panel;
	private int width = 800;
	private int height = 800;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	// Mouse
	public int mouseX = 0;
	public int mouseY = 0;

	// map coord
	private int x = 0;
	private int y = 0;

	private HexMap map;

	private int cameraBoxMin = 100;
	private int cameraBoxMax = 700;
	private float cameraMoveVar = 6.9f;

	private int size = 20;
	private int maxSize = 80;
	private int minSize = 6;

	private boolean lockCamera = false;

	// Game Loop Constructor
	public Game() {
		map = new HexMap(100, 100, 20, "earth.png");
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
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
				}
				if (e.getKeyCode() == KeyEvent.VK_L) {
					lockCamera = !lockCamera;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});

		// Mouse Listener
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				// On left mouse click
				if (SwingUtilities.isLeftMouseButton(e)) {
					mouseX = e.getX();
					mouseY = e.getY();
					map.click(x, y, mouseX, mouseY);
				}
				// On right mouse click
				else {

				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
				// On right mouse release
				else {

				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				if (SwingUtilities.isLeftMouseButton(e)) {
				} else {

				}
			}
		});

		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				size -= e.getWheelRotation();
				if (size < minSize)
					size = minSize;
				else if (size > maxSize)
					size = maxSize;
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
				frame.setTitle("Frames " + frames + " | Ticks " + ticks);
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

		moveCamera();
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
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		map.draw(g, size, x, y, width, height);

		g.dispose();
		bs.show();
	}

	// Main
	public static void main(String[] args) {

		Game game = new Game();

		game.start();
	}

	private void moveCamera() {
		if (!lockCamera) {
			float cameraMoveVar = this.cameraMoveVar - (size/maxSize)*20;
			if (mouseX < cameraBoxMin) {
				x -= (cameraBoxMin - mouseX) / cameraMoveVar;

			} else if (mouseX > cameraBoxMax) {
				x += (mouseX - cameraBoxMax) / cameraMoveVar;

			}

			if (mouseY < cameraBoxMin) {
				y -= (cameraBoxMin - mouseY) / cameraMoveVar;

			} else if (mouseY > cameraBoxMax) {
				y += (mouseY - cameraBoxMax) / cameraMoveVar;

			}

		}

		if (x < -size * 2) {
			x = -size * 2;
		} else if (x > map.getWidth() - this.width) {
			x = map.getWidth() - this.width;
		}

		if (y < -size * 2) {
			y = -size * 2;
		} else if (y > map.getHeight() - this.height) {
			y = map.getHeight() - this.height;
		}
	}

}

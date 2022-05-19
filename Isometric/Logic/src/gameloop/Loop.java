package gameloop;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entity.LogicManager;

public class Loop extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";

	// JPanel Options
	private JPanel panel;
	private int width = 800;
	private int height = 600;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	// Mouse
	private int mouseX = 0;
	private int mouseY = 0;

	// World Coordinates
	private int worldX = width / 2;
	private int worldY = height / 2;

	// Menu
	private boolean select;
	private boolean addLogic;
	private boolean move;
	private boolean interact;

	private int selectLogic = 0;

	// Game Loop Constructor
	public Loop() {
		frame = new JFrame(title);
		panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
		setBounds(0, 0, width, height);
		panel.add(this);
		frame.setMinimumSize(new Dimension(800, 600));
		setIgnoreRepaint(true);
		frame.setUndecorated(false);
		frame.setResizable(true);
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
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (select)
						LogicManager.deselectAll();
					changeTool(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_1) {
					changeTool(1);
				}
				if (e.getKeyCode() == KeyEvent.VK_2) {
					changeTool(3);
				}
				if (e.getKeyCode() == KeyEvent.VK_3) {
					changeTool(3);
				}
				if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
					selectLogic--;
				}
				if (e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
					selectLogic++;
				}
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					LogicManager.deleteSelected();
				}
				if (e.getKeyCode() == KeyEvent.VK_M) {
					LogicManager.merge(mouseX - worldX, mouseY - worldY);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_N) {
					LogicManager.addLogicObject(mouseX - worldX, mouseY - worldY, selectLogic);
				}

				if (e.getKeyCode() == KeyEvent.VK_Q) {
					LogicManager.connectObjects(1, 0, 1, 0);
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
					if (addLogic)
						LogicManager.addLogicObject(mouseX - worldX, mouseY - worldY, selectLogic);
					else if (select)
						LogicManager.selectClick(mouseX - worldX, mouseY - worldY, e.isShiftDown());
					else if (move)
						LogicManager.establishFocus(mouseX - worldX, mouseY - worldY, true);
					else if (interact) {
						LogicManager.interact(mouseX - worldX, mouseY - worldY, false);
					}
				}
				// On right mouse click
				else {
					LogicManager.establishFocus(mouseX - worldX, mouseY - worldY, true);
				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (move)
						LogicManager.establishFocus(mouseX - worldX, mouseY - worldY, false);
					else if (interact) {
						LogicManager.interact(mouseX - worldX, mouseY - worldY, true);
						LogicManager.unFocusConnector();
					} else if (select) {
						LogicManager.boxSelect(mouseX - worldX, mouseY - worldY, e.isShiftDown());
					}
				}
				// On right mouse release

				else {
					LogicManager.establishFocus(mouseX - worldX, mouseY - worldY, false);

				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isMiddleMouseButton(e)) {
					worldX -= mouseX - e.getX();
					worldY -= mouseY - e.getY();
				}
				mouseX = e.getX();
				mouseY = e.getY();
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (move)
						LogicManager.moveLogic(mouseX - worldX, mouseY - worldY, e.isShiftDown());

				} else {
					LogicManager.moveLogic(mouseX - worldX, mouseY - worldY, e.isShiftDown());

				}
			}
		});

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				if (e.getWheelRotation() > 0) {
					selectLogic--;
				} else if (e.getWheelRotation() < 0) {
					selectLogic++;
				}
			}

		});

		changeTool(0);
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
		double amountOfTicks = 360.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int ticks = 0;
		while (running) {
			setBounds(0, 0, width, height);
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
				frame.setTitle("Frames " + frames + " | Ticks " + ticks + " | Logiced Objects : "
						+ LogicManager.getTotalLogicObjects());
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
		LogicManager.update();
	}

	// Game Render
	private void render() {
		// Sets Buffered Strategy
		if (frame.getWidth() != width || height != frame.getHeight()) {
			worldX += (frame.getWidth() - width) / 2;
			worldY += (frame.getHeight() - height) / 2;
			width = frame.getWidth();
			height = frame.getHeight();
		}
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		int worldX = this.worldX;
		int worldY = this.worldY;

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		// clears screen
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, width, height);

		g.translate(worldX, worldY);

		// Axis display
		g.setColor(Color.ORANGE);
		g.drawLine(-worldX, 0, width - worldX, 0);
		// g.setColor(Color.RED);
		g.drawLine(0, -worldY, 0, height - worldY);

		// Logic display
		LogicManager.draw(g, mouseX - worldX, mouseY - worldY);

		// GUI display
		g.translate(-worldX, -worldY);
		g.setColor(Color.GREEN);
		drawString(g, "Logic Selection : " + selectLogic + "\nMenu:", 5, 100);
		if (select) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		drawString(g, "\nEsc)Select Mode", 5, 130);
		if (addLogic) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		drawString(g, "\n1)Add Logic", 5, 150);
		if (interact) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		drawString(g, "\n2)Interact", 5, 170);

		g.setColor(Color.GREEN);
		drawString(g, "X: " + (mouseX - worldX) + "\nY: " + (-mouseY + worldY), 5, height - 80);

		g.dispose();
		bs.show();
	}

	private void drawString(Graphics2D g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	private void changeTool(int tool) {
		boolean tools[] = new boolean[4];
		if (tool >= tools.length)
			return;
		tools[tool] = true;
		select = tools[0];
		addLogic = tools[1];
		move = tools[2];
		interact = tools[3];
	}

	// Main
	public static void main(String[] args) {

		Loop game = new Loop();

		game.start();
	}

}
package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import camera.Camera;
import champion.Champion;
import champion.Zalzaman;
import map.Map;
import map.PracticeMap;
import udp.client.UDPClient;

public class ClientGame extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";

	// JPanel Options
	private JPanel panel;
	private int width = 1400;
	private int height = 800;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	// Map
	public Map map;

	// Camera
	public Camera camera;

	public Champion hero = new Zalzaman(0, 0);
	
	public ArrayList<Champion> heroes = new ArrayList<Champion>();

	// Mouse
	public int mouseX = 0;
	public int mouseY = 0;
	
	public String id = new Random().nextInt(8999) + 1000 + "";

	// Client
	public UDPClient client = new UDPClient(id);


	// Game Loop Constructor
	public ClientGame(Map gameMap) {
		map = gameMap;
		camera = new Camera(-20, -20, this);
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
					hero.left = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					hero.right = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					hero.up = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					hero.down = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					hero.left = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					hero.right = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					hero.up = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					hero.down = false;
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
					hero.auto = true;
				}
				// On right mouse click
				else {

				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					hero.auto = false;

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
				frame.setTitle("Frames "+frames+" | Ticks "+ticks+" | {Packets} Received: "+client.packetsReceived+" Send: "+client.packetsSend);
				timer += 1000;
				frames = 0;
				ticks = 0;
				client.packetsSend=0;
				client.packetsReceived=0;
				client.updateSend();
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
		map.tick();
		hero.tick(this);

		String moves = Boolean.toString(hero.left).charAt(0) + "" + Boolean.toString(hero.right).charAt(0)
				+ Boolean.toString(hero.up).charAt(0) + Boolean.toString(hero.down).charAt(0);
		client.send = id+moves;
		String answer=client.get;

		if (answer != null&& !answer.isEmpty()) {
			String num = "";
			for (int i = 0; i < answer.length(); i++) {
				if (answer.charAt(i) != '-') {
					num = num + answer.charAt(i);
				} else {
					int number = Integer.parseInt(num.trim());
					hero.x = number;
					num = "";
				}
			}
			
			int number = Integer.parseInt(num.trim());

			hero.y = number;
			num = "";
		}
		
		camera.moveCamera((int) (hero.x - 0.5), (int) (hero.y - 0.5));
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

		// Render Map
		map.paint(this, g);

		hero.paint(this, g);
		
		for (Champion c:heroes)c.paint(this, g);

		g.dispose();
		bs.show();
	}

	// Main
	public static void main(String[] args) {

		ClientGame game = new ClientGame(new PracticeMap());

		game.start();
	}

}

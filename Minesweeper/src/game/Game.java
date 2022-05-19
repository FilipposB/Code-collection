package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import map.Map;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	// our frame and panel
	private JFrame frame;
	private JPanel panel;

	// extra component size
	private final int compHeight = 2;

	// the frame and panel size
	private final int width = 802;
	private final int height = 800 + compHeight;


	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	private int worldSize=20;
	private int bombs=50;
	public int tileSize=width / worldSize;
;

	// map class
	private Map map;


	public Game() {
		init();
		frame = new JFrame("Mine sweeper v0");
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
				stop();
				System.exit(0);
			}
		});


		// Mouse Listener
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				// On left mouse click
				if (SwingUtilities.isLeftMouseButton(e)) {
					map.leftClickOnMap(e.getX()/tileSize, e.getY()/tileSize);
				}
				// On right mouse click
				else {
					map.rightClickOnMap(e.getX()/tileSize, e.getY()/tileSize);
				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					
				}
				// On right mouse release
				else {
				}
			}

		});
	}

	private void init() {
		
		map = new Map(worldSize,bombs);


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

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.requestFocus();
		while (running) {
			render();
			tick();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}
	}

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

		map.paint(g, tileSize);
		
		bs.show();
		g.dispose();
	}
	
	private void gameOver(){
		render();
		Scanner in = new Scanner(System.in);
		System.out.println("You loose :/");
		in.nextLine();
		map.newMap(worldSize, bombs);
		in.close();
	}
	
	private void gameWin(){
		render();
		Scanner in = new Scanner(System.in);
		System.out.println("You Won  !!!!");
		in.nextLine();
		map.newMap(worldSize, bombs);
		in.close();
	}

	// game update
	private void tick() {
		if (map.hitBomb)gameOver();
		if (map.win)gameWin();
	}

	public static void main(String args[]) {
		Game game = new Game();

		game.start();
	}

}

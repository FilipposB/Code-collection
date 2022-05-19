package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import camera.Camera;
import entities.Entity;
import player.PlayerEntity;
import tools.Isometric;




public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";
	
	// JPanel Options
	private JPanel panel;
	private int width=640;
	private int height=480;
	
	private Camera camera;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;
	
	//Test
	private Entity player = new PlayerEntity(100,100,10,10,"ghost.png");
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	// Game Loop Constructor
	public Game() {
		frame = new JFrame(title);
		panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(width,height));
		panel.setLayout(null);
		setBounds(0,0,width,height);
		panel.add(this);
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
		
		camera = new Camera(600,900,4);

		// Key Bindings
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					camera.changeDirection(0, true);
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					camera.changeDirection(1, true);

				}	

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					camera.changeDirection(2, true);

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					camera.changeDirection(3, true);

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					camera.changeDirection(0, false);
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					camera.changeDirection(1, false);

				}	

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					camera.changeDirection(2, false);

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					camera.changeDirection(3, false);

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
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
	      while(running) {
	       long now = System.nanoTime();
	       delta += (now - lastTime) / ns;
	       lastTime = now;
	       while(delta >= 1) {
	        tick();
	        ticks++;
	        delta--;
	       }
	       render();
	       frames++;

	       if(System.currentTimeMillis() - timer > 1000) {
		    frame.setTitle("FPS : "+frames+" | TICKS : "+ticks);
	        timer += 1000;
	        frames = 0;
	        ticks = 0;

	       }
	       
	       try { Thread.sleep(10); } catch (Exception e) {}

	      }
	      stop();
	}

	// Game Update
	private void tick() {
		camera.tick();
		
	}

	// Game Render
	private void render() {
		int size=50;
		Polygon poly = new Polygon();
		poly.addPoint(0, 0);
		poly.addPoint(0, size);
		poly.addPoint(size,size);
		poly.addPoint(size, 0);

		// Sets Buffered Strategy
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		//clears screen
		
		g.clearRect(0, 0, width, height);
		
		g.setColor(Color.green);
		int mapWid=100;
		int mapHei=100;
		int cameraX=camera.getX();
		int cameraY=camera.getY();
		g.drawRect(-size-cameraX, -cameraY, mapWid*size*2, mapHei*size);

		for (int i=0;i<mapWid;i++){
			for (int j=0;j<mapHei;j++){
				g.drawPolygon(Isometric.polygon2DToIso(poly,i*size*2-cameraX,j*size-cameraY));

			}
		}
		g.dispose();
		bs.show();
	}
	

	// Main
	public static void main(String[] args) {

		Game game = new Game();
		

		game.start();
	}

}

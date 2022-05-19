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

import entity.JointManager;

public class Loop extends Canvas implements Runnable {
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

	// Mouse
	private int mouseX = 0;
	private int mouseY = 0;

	// World Coordinates
	private int worldX = 700;
	private int worldY = 400;
	// Menu
	private boolean select;
	private boolean addJoint;
	private boolean move;

	// Game Loop Constructor
	public Loop() {
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
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (select)
						JointManager.deselectAll();
					changeTool(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_1) {
					changeTool(1);
				}
				if (e.getKeyCode() == KeyEvent.VK_2) {
					changeTool(2);
				}
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					JointManager.deleteSelected();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_N) {
					JointManager.addJointedObject(mouseX-worldX, mouseY-worldY);
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
					if (addJoint)
						JointManager.addJoint(mouseX-worldX, mouseY-worldY);
					else if (select)
						JointManager.selectClick(mouseX-worldX, mouseY-worldY, e.isShiftDown());
					else if (move)
						JointManager.establishFocus(mouseX-worldX, mouseY-worldY, true);
				}
				// On right mouse click

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (move)
						JointManager.establishFocus(mouseX-worldX, mouseY-worldY, false);
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
				if (SwingUtilities.isMiddleMouseButton(e)) {
					worldX-=mouseX-e.getX();
					worldY-=mouseY-e.getY();
				}
				mouseX = e.getX();
				mouseY = e.getY();
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (move)
						JointManager.moveJoint(mouseX-worldX, mouseY-worldY,e.isShiftDown());
				} else {
					
				}
			}
		});

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				
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
				frame.setTitle("Frames " + frames + " | Ticks " + ticks + " | Jointed Objects : "
						+ JointManager.getTotalJointedObjects());
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

	}

	// Game Render
	private void render() {

		// Sets Buffered Strategy
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		int worldX=this.worldX;
		int worldY=this.worldY;

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		// clears screen
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, width, height);
		

		g.translate(worldX, worldY);
		
		//Axis display
		g.setColor(Color.BLUE);
		g.drawLine(-worldX, 0, 1400-worldX, 0);
		g.setColor(Color.RED);
		g.drawLine(0,-worldY,0,800-worldY);
		
		//Joint display
		JointManager.draw(g);
		
		//GUI display
		g.translate(-worldX, -worldY);
		g.setColor(Color.GREEN);
		drawString(g,"Menu:\nEsc)Select Mode\n1)Add joint\n2)Move\n", 5, 100);
		
		g.dispose();
		bs.show();
	}
	
	private void drawString(Graphics2D g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	private void changeTool(int tool){
		boolean tools[] = new boolean[4];
		if (tool>=tools.length)return;
		tools[tool]=true;
		select=tools[0];
		addJoint=tools[1];
		move=tools[2];
	}

	// Main
	public static void main(String[] args) {

		Loop game = new Loop();

		game.start();
	}

}
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import enity.Bird;
import enity.Tube;
import neuralNetwork.NeuralNetwork;
import neuralNetwork.Tools;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// JFrame Options
	private JFrame frame;
	private static String title = "Game Loop";

	// JPanel Options
	private JPanel panel;
	private int width = 800;
	private int height = 700;

	// Game Loop Stuff
	private Thread thread;
	private boolean running = false;

	Bird bestBird;
	Bird maxBird;
	Bird smartBird;
	List<Bird> birds = new ArrayList<Bird>();

	int gen = 0;
	int score = 0;
	int maxScore = 0;
	int bestGen = -1;

	int saveScore = 400;

	public Tube tubes[] = new Tube[2];

	// Game Loop Constructor
	public Game() {
		try {
			smartBird = new Bird(100, height / 2 + 10, 20, 20, new NeuralNetwork(Tools.loadNetwork("birdBrain.dat")),true);
			birds.add(new Bird(100, height / 2 + 10, 20, 20, smartBird.brain,true));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (smartBird == null)
			for (int i = 0; i < 10000; i++) {
				birds.add(new Bird(100, height / 2 + 10, 20, 20, null,false));
			}
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
		spawnNewTubes();
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
				frame.setTitle("FPS : " + frames + " | TICKS : " + ticks +" Score "+score);
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
		for (Bird b : birds)
			b.update(this);
		for (Tube t : tubes) {
			t.update(this);
			if (t.x < -100) {
				score++;
				spawnNewTubes();
				break;
			}
		}
		
		if (score >= maxScore) {
			if ((score==saveScore))
			try {
				Tools.saveNetwork(birds.get(birds.size()-1).brain, "birdBrain.dat");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			maxScore = score;
			bestGen = gen;
			maxBird = bestBird;
			
		}

		for (int i = 0; i < birds.size(); i++) {
			if (birds.get(i).collidesWith(tubes[0]) || birds.get(i).collidesWith(tubes[1])) {
				bestBird = birds.get(i);
				birds.remove(i);
			}
		}

		
		if (birds.isEmpty()) {
			
			gen++;
			score = 0;
			if (smartBird == null)
				for (int i = 0; i < 1000; i++) {
					birds.add(new Bird(100, height / 2 + 10, 20, 20, maxBird.brain,false));
					birds.add(new Bird(100, height / 2 + 10, 20, 20, bestBird.brain,false));
					birds.add(new Bird(100, height / 2 + 10, 20, 20, null,false));

				}
			else{
				birds.add(new Bird(100, height / 2 + 10, 20, 20, smartBird.brain,true));

			}

			spawnNewTubes();
		}

	}

	private void spawnNewTubes() {
		tubes = new Tube[2];
		int gap = 150;
		Random rand = new Random();
		int y = rand.nextInt(500);
		tubes[0] = new Tube(800, 0, 100, y);
		tubes[1] = new Tube(800, y + gap, 100, 700);
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

		for (Bird b : birds){
			Color col = Color.DARK_GRAY;
			g.setColor(col);
			b.paint(g);
			
		}
		g.setColor(Color.black);
		tubes[0].paint(g);
		tubes[1].paint(g);

		g.setColor(Color.RED);
		g.drawString("Generation  :  " + gen, width - 100, 10);
		g.drawString("Score  :  " + score, width - 100, 40);
		g.drawString("Max Score  :  " + maxScore, width - 100, 70);
		g.drawString("Best Gen  :  " + bestGen, width - 100, 100);

		if (!birds.isEmpty()) {
			g.scale(0.3, 0.3);
			birds.get(0).brain.drawNetwork(g, 40, 40);

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

package game;

import java.awt.Canvas;

import champion.Champion;
import champion.Zalzaman;
import map.Map;
import map.PracticeMap;
import udp.server.UDPServer;

public class ServerGame extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// Game Loop Stuff
	private Thread thread;
	
	private boolean running = false;

	//

	UDPServer server = new UDPServer(this);

	// Map
	public Map map;

	public Champion hero = new Zalzaman(0, 0);

	// Game Loop Constructor
	public ServerGame() {
		map = new PracticeMap();
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

			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("Ticks "+ticks+" | "+server.clients.size()+" Clients are connected | {Packets} Received: "+server.packetsReceived+" Send: "+server.packetsSend);
				server.packetsReceived=0;
				server.packetsSend=0;
				timer += 1000;
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
		map.tick();
		hero.tick(this);
		server.send=(int)(hero.x-0.5)+"-"+(int)(hero.y-0.5);
		
	}

	// Main
	public static void main(String[] args) {

		ServerGame game = new ServerGame();

		game.start();
	}

}

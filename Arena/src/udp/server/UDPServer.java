package udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import game.ServerGame;

public class UDPServer implements Runnable {
	DatagramSocket serverSocket;
	byte[] receiveData = new byte[2048];
	byte[] sendData = new byte[2048];

	public int packetsSend;
	public int packetsReceived;

	public String send = "";

	private Thread server;

	public ArrayList<ClientProfile> clients;

	private boolean running = false;

	public ServerGame game;

	public UDPServer(ServerGame gam) {
		packetsSend = 0;
		packetsReceived = 0;
		game = gam;

		clients = new ArrayList<ClientProfile>();

		try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		start();
	}

	public void send(String tick) {
		sendData = new byte[2048];
		sendData = tick.getBytes();

		for (ClientProfile c : clients) {
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, c.ip, c.port);
			try {
				serverSocket.setSoTimeout(10);
				serverSocket.send(sendPacket);
				packetsSend++;
			} catch (IOException e) {
				e.getCause();
			}
			c.quitTimer++;
		}

	}

	public byte[] receive() {
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.setSoTimeout(10);
			serverSocket.receive(receivePacket);
			packetsReceived++;
			String name = new String(receivePacket.getData()).substring(0, 4);
			if (name != null) {
				if (checkClient(name) == -1) {
					clients.add(new ClientProfile(name, receivePacket.getAddress(), receivePacket.getPort(), 500));
				} else if (!clients.isEmpty()) {
					if (name.equals(clients.get(0).name)) {
						if (new String(receivePacket.getData()).charAt(4) == 't') {
							game.hero.left = true;
						} else {
							game.hero.left = false;

						}

						if (new String(receivePacket.getData()).charAt(5) == 't') {
							game.hero.right = true;

						} else {
							game.hero.right = false;

						}
						if (new String(receivePacket.getData()).charAt(6) == 't') {
							game.hero.up = true;

						} else {
							game.hero.up = false;

						}
						if (new String(receivePacket.getData()).charAt(7) == 't') {
							game.hero.down = true;

						} else {
							game.hero.down = false;

						}
					}
				}
			}

		} catch (IOException e) {

		}

		return receivePacket.getData();
	}

	public void close() {

		serverSocket.close();
		stop();
	}

	public void timeOutClients() {
		ArrayList<Integer> ids = new ArrayList<Integer>();

		for (ClientProfile c : clients) {

			c.quitTimer++;
			if (c.toRemove())
				ids.add(c.getID());

		}

		for (int i = clients.size() - 1; i >= 0; i--) {
			for (Integer num : ids)
				if (clients.get(i).getID() == num) {
					clients.remove(i);
					break;
				}

		}
	}

	public int checkClient(String nam) {
		for (ClientProfile c : clients) {

			if (c.name.equals(nam)) {
				c.quitTimer = 0;
				return c.getID();
			}

		}

		return -1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (running) {
			receive();
			send(send);
			timeOutClients();

		}

	}

	// Starts Game Loop
	private synchronized void start() {
		server = new Thread(this, "Server");
		running = true;
		server.start();
	}

	// Stops Game Loop
	private synchronized void stop() {
		running = false;

		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

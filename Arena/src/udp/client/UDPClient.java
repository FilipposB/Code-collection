package udp.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient implements Runnable {

	DatagramSocket clientSocket;
	InetAddress IPAddress;

	DatagramPacket receivePacket;

	byte[] sendData = new byte[2048];
	byte[] receiveData = new byte[2048];

	int port;

	Thread client;

	public String send;

	public String get;
	private String getOld;

	public int packetsSend;
	public int packetsReceived;

	boolean running = false;

	public UDPClient(String nam) {
		BufferedReader b;

		File textFile = new File("src/clientOptions");
		String ip = null;
		try {
			b = new BufferedReader(new FileReader(textFile));
			ip=b.readLine();
			port=Integer.parseInt(b.readLine());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			IPAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

		send = nam;
		client = new Thread(this, "Client");
		try {
			clientSocket = new DatagramSocket();
			port = clientSocket.getLocalPort();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		start();

	}

	public void send(String tick) {
		if (!new String(sendData).trim().equals(tick.trim()) && sendData != null) {
			sendData = tick.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			try {
				clientSocket.setSoTimeout(10);

				clientSocket.send(sendPacket);

				packetsSend++;

			} catch (IOException e) {
				sendData = new byte[1024];
			}
		}
		

	}

	public void updateSend() {
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		try {
			clientSocket.setSoTimeout(10);

			clientSocket.send(sendPacket);
			packetsSend++;

		} catch (IOException e) {

		}
	}

	public byte[] receive() {
		receiveData = new byte[1024];
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.setSoTimeout(10);

			clientSocket.receive(receivePacket);
			packetsReceived++;
			return receivePacket.getData();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	public void close() {
		clientSocket.close();
		stop();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (running) {

			send(send);
			byte reciv[] = receive();
			if (reciv != null) {
				get = new String(reciv);
			}
		}
	}

	// Starts Game Loop
	private synchronized void start() {
		client = new Thread(this, "Client");
		running = true;
		client.start();
	}

	// Stops Game Loop
	private synchronized void stop() {
		running = false;
		try {
			client.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

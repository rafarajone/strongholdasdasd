package game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import game.World;
import game.entity.Unit;

public class GameSocket {

	World world;
	
	DatagramSocket socket;
	
	SocketBuffer buffer = new SocketBuffer();
	SocketBuffer receiveBuffer = new SocketBuffer(32768);
	
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	
	int port;
	InetAddress address;
	
	ArrayList<OnEvent> onEvents = new ArrayList<OnEvent>();
	
	public GameSocket(World world) {
		this.world = world;
		
		receivePacket = new DatagramPacket(receiveBuffer.data, receiveBuffer.length());
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void init(String address, int port) {
		this.port = port;
		
		try {
			this.address = InetAddress.getByName(address);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
	
	public void listen() {
		while(true) {
			/*
			System.out.println();
			System.out.println("GameSocket: listening...");
			*/
			receivePacket = new DatagramPacket(receiveBuffer.data, receiveBuffer.length());
			
			//System.out.println("listening for packet...");
			try {
				socket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println("received");
			/*
			System.out.println("Received packet from:");
			System.out.println("Address: "+ receivePacket.getAddress().getHostAddress());
			System.out.println("Port: " + receivePacket.getPort());
			*/
			int socketNameLength = receiveBuffer.readInt();
			String socketName = receiveBuffer.readString(socketNameLength);
			
			//System.out.println("socketName: " + socketName);
			
			for(int i = 0; i < onEvents.size(); i++) {
				OnEvent oe = onEvents.get(i);
				if(oe.socketName.equals(socketName))
					oe.callback.apply(receiveBuffer);
			}
			
			
			receiveBuffer.setPointer(0);
		}
	}
	
	/*
	public void readMap() {
		int xpos = receiveBuffer.readInt();
		int ypos = receiveBuffer.readInt();
		int w = receiveBuffer.readInt();
		int h = receiveBuffer.readInt();
		
		
		System.out.println("xpos: " + xpos);
		System.out.println("ypos: " + ypos);
		System.out.println("w: " + w);
		System.out.println("h: " + h);
		
		
		byte[] map = receiveBuffer.readBytes(w * h);
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				world.map[(x + xpos) + (y + ypos) * world.MAP_WIDTH] = map[x + y * w];
			}
		}
		
	}
	*/
	
	/*
	public synchronized void readUnits() {
		//world.unitList.clear();
		int size = receiveBuffer.readInt();
		
		//System.out.println("size: " + size);
		for(int i = 0; i < size; i++) {
			int x = receiveBuffer.readInt();
			int y = receiveBuffer.readInt();
			
			Unit unit = new Unit(world, x, y);
			
			world.unitList.add(unit);
			
		}
		
	}
	*/
	
	public void on(String socketName, OnCallback callback) {
		onEvents.add(new OnEvent(socketName, callback));
	}
	
	public void send(String socketName, byte[] data) {
		
		buffer.writeInt(socketName.length());
		buffer.writeBytes(socketName.getBytes());
		buffer.writeBytes(data);
		
		sendPacket = new DatagramPacket(buffer.data, buffer.length(), address, 3859);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		buffer.clear();
	}
	
	public void send(String socketName, String data) {
		send(socketName, data.getBytes());
	}
}

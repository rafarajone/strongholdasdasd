
package game;

import game.engine.Camera;
import game.engine.Engine;
import game.engine.Keyboard;
import game.engine.MeshBuffer;
import game.engine.Mouse;
import game.engine.ObjLoader;
import game.engine.Scene;
import game.engine.Sprite;
import game.engine.Texture2D;
import game.engine.Window;
import game.net.GameSocket;
import game.state.Game;

import javax.swing.JOptionPane;

import static org.lwjgl.glfw.GLFW.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	public static final long SECOND = 1000000000;
	public static final long UPDATE_TIME = SECOND / 60;

	World world = new World();
	public GameSocket socket;
	
	public static final int CONNECT = 1;
	public static final int GAME = 2;

	public int state = GAME;

	Window window;
	Game game;
	
	public Main() {
		
		
		socket = new GameSocket(world);
		connect("127.0.0.1", 3859);

		// new Frame(canvas);

		Engine.init();

		window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "untitled game");
		
		
		Keyboard keyboard = new Keyboard();
		window.setKeyCallback(keyboard);
		
		Mouse mouse = new Mouse();
		window.setMouseCallback(mouse);
		
		
		game = new Game(socket, world, window, keyboard);
		
		long time = System.nanoTime();
		long time2 = System.nanoTime();
		int fps = 0;
		while (!window.shouldClose()) {

			if (System.nanoTime() - time > UPDATE_TIME) {

				update();
				time += UPDATE_TIME;
			}
			
			if (System.nanoTime() - time2 > SECOND) {

				System.out.println("FPS: " + fps);
				
				fps = 0;
				time2 += SECOND;
			}

			switch(state) {
			case CONNECT:
				break;
			case GAME:
				game.render();
				break;
			}
			fps++;
			//window.render(scene, camera);
			window.update();
		}

		System.exit(0);
		// loop();
	}

	public void update() {
		switch(state) {
		case CONNECT:
			break;
		case GAME:
			game.update();
			break;
		}
	}

	public static String readFile(String path) {
		InputStream is = Main.class.getResourceAsStream(path);
		
		String text = null;
		try {
			byte[] data = new byte[is.available()];
			is.read(data);
			text = new String(data);
			
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public static void printArray(byte[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print((array[i] & 0xFF) + " ");
		}
		System.out.println();
	}
	
	public static void printArray(int[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	public static void printArray(float[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	public void connect(String address, int port) {
		System.out.println(address);
		System.out.println(port);
		
		new Thread(() -> socket.listen()).start();
		
		socket.init(address, port);
		
		//game.start();
	}
	
	/*
	public void setState(int state) {
		this.state = state;
	}
	
	public void loop() {
		long time = System.nanoTime();
		while(true) {
			
			if(System.nanoTime() - time > UPDATE_TIME) {
				update();
				
				time += UPDATE_TIME;
			}
		}
	}
	
	public void update() {
		
		switch(state) {
		case CONNECT:
			connectState.update();
			break;
		case Main.GAME:
			game.update();
			break;
		}
		
	}
	*/

	public static void main(String[] args) {
		new Main();
	}
}

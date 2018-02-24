package game.engine;

import static org.lwjgl.glfw.GLFW.glfwInit;

import javax.swing.JOptionPane;

public class Engine {

	public static void init() {
		if (!glfwInit()) {
			JOptionPane.showMessageDialog(null, "Error initializing glfw");
		}
	}
	
	
}

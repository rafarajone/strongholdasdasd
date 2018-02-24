package game.engine;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Keyboard implements GLFWKeyCallbackI{

	boolean[] keys = new boolean[512];
	
	public static final int RIGHT_ARROW = 262;
	public static final int LEFT_ARROW = 263;	
	public static final int DOWN_ARROW = 264;
	public static final int UP_ARROW = 265;
	
	public static final int SHIFT = 340;
	public static final int CONTROL = 341;
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action > 0;
		//System.out.println(window + " " + key + " " + scancode + " " + action + " " + mods);
	}
	
	public boolean isPressed(int key) {
		return keys[key];
	}

}

package game.engine;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.system.NativeType;

public class Mouse{

	int mouseX, mouseY;
	public int[] buttons = new int[8];
	
	public int buttonAction(int button) {
		return buttons[button];
	}
}

package game.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class Window {

	public int width, height;
	String title;

	public long id;
	
	public Keyboard keyboard;
	public Mouse mouse;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;

		id = glfwCreateWindow(width, height, title, 0, 0);

		glfwMakeContextCurrent(id);

		/*GLCapabilities cap = */GL.createCapabilities();
	}
	
	public void setKeyCallback(Keyboard keyboard) {
		this.keyboard = keyboard;
		glfwSetKeyCallback(id, keyboard);
	}
	
	public void setMouseCallback(Mouse mouse) {
		this.mouse = mouse;
		glfwSetMouseButtonCallback(id, new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				mouse.buttons[button] = action;
			}
		});
		glfwSetCursorPosCallback(id, new GLFWCursorPosCallback() {
			public void invoke(long window, double xpos, double ypos) {
				//System.out.println(window + " " + xpos + " " + ypos);
				mouse.mouseX = (int) xpos;
				mouse.mouseY = (int) ypos;
			}
		});
	}
	
	public void render(Scene scene, Camera camera) {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(1.0f, 1.0f, 0.0f, 0.0f);

		for (int i = 0; i < scene.meshList.size(); i++) {
			Mesh mesh = scene.meshList.get(i);
			mesh.render(camera);
			// System.out.println("saa");
		}

	}

	public void update() {
		glfwSwapBuffers(id);

		glfwPollEvents();
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(id);
	}

}

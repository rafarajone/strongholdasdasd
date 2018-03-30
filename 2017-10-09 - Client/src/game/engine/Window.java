package game.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

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

		glfwWindowHint(GLFW_SAMPLES, 4);
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
		//glEnable(GL_DEPTH_TEST);

		
		for(int i = 0; i < Material.usedMaterials.size(); i++) {
			Material material = Material.usedMaterials.get(i);
			material.program.use();
			
			int projectionMatrixLocation = glGetUniformLocation(material.program.id, "projectionMatrix");
			int viewMatrixLocation = glGetUniformLocation(material.program.id, "viewMatrix");

			float[] projectionElements = new float[16];
			camera.projectionMatrix.get(projectionElements);

			float[] viewElements = new float[16];
			camera.viewMatrix.get(viewElements);
			
			glUniformMatrix4fv(projectionMatrixLocation, false, projectionElements);
			glUniformMatrix4fv(viewMatrixLocation, false, viewElements);
			
		}
		
		
		/*
		for (int i = 0; i < scene.meshList.size(); i++) {
			Mesh mesh = scene.meshList.get(i);
			mesh.render();
			// System.out.println("saa");
		}
		*/

		
		for (int i = 0; i < scene.batchList.size(); i++) {
			Batch b = scene.batchList.get(i);
			b.render();
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

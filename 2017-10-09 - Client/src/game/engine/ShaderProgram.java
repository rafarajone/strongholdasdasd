package game.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

	public Shader vertex, fragment;
	int id;
	
	public static int currentProgramId = -1234;

	public ShaderProgram(Shader vertex, Shader fragment) {
		this.vertex = vertex;
		this.fragment = fragment;
		
		id = glCreateProgram();
		glAttachShader(id, vertex.id);
		glAttachShader(id, fragment.id);
		glLinkProgram(id);
		int linkStatus = glGetProgrami(id, GL_LINK_STATUS);
		if (linkStatus == GL_FALSE) {
			System.out.println("Error linking program: " + glGetProgramInfoLog(id));
		}
		int validateStatus = glGetProgrami(id, GL_VALIDATE_STATUS);
		if (validateStatus == GL_FALSE) {
			System.out.println("Error validating program: " + glGetProgramInfoLog(id));
		}

	}

	public void use() {
		currentProgramId = id;
		glUseProgram(id);
	}
	
	public boolean isUsedNow() {
		return id == currentProgramId;
	}
}

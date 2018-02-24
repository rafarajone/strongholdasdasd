package game.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	int id;
	
	public static final int VERTEX_SHADER = GL_VERTEX_SHADER;
	public static final int FRAGMENT_SHADER = GL_FRAGMENT_SHADER;
	
	public String source;
	public int type;
	
	public Shader(String source, int type) {
		this.source = source;
		this.type = type;
		
		id = glCreateShader(type);
		glShaderSource(id, source);
		glCompileShader(id);
		int compileStatus = glGetShaderi(id, GL_COMPILE_STATUS);
		if(compileStatus == GL_FALSE) {
			System.out.println("Error compiling shader: " + glGetShaderInfoLog(id));
		}
		
		
		
	}
	
	
}

package game.engine;

import java.util.ArrayList;

import game.Main;

public class Material {
	
	static ArrayList<Material> usedMaterials = new ArrayList<Material>(); //should work
	
	protected static ShaderProgram spriteProgram;
	protected static ShaderProgram defaultProgram;
	
	public ShaderProgram program = defaultProgram;
	
	Texture2D texture;

	static {
		String spriteVertexSource = Main.readFile("/shaders/spriteVertex.glsl");
		Shader spriteVertex = new Shader(spriteVertexSource, Shader.VERTEX_SHADER);

		String spriteFragmentSource = Main.readFile("/shaders/spriteFragment.glsl");
		Shader spriteFragment = new Shader(spriteFragmentSource, Shader.FRAGMENT_SHADER);

		spriteProgram = new ShaderProgram(spriteVertex, spriteFragment);
		
		String defaultVertexSource = Main.readFile("/shaders/defaultVertex.glsl");
		Shader defaultVertex = new Shader(defaultVertexSource, Shader.VERTEX_SHADER);

		String defaultFragmentSource = Main.readFile("/shaders/defaultFragment.glsl");
		Shader defaultFragment = new Shader(defaultFragmentSource, Shader.FRAGMENT_SHADER);

		defaultProgram = new ShaderProgram(defaultVertex, defaultFragment);
	}
	
	public Material(Texture2D texture) {
		this.texture = texture;
		usedMaterials.add(this);
	}
	
}

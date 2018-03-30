package game.engine;

public class SpriteMaterial extends Material{

	public SpriteMaterial(Texture2D texture) {
		super(texture);
		
		program = spriteProgram;
	}
	
	public SpriteMaterial() {
		this(null);
	}

}

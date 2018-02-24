package game.engine;

public class CubeGeometry extends Geometry{

	public CubeGeometry(float w, float h, float d) {
		
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		float hw = w / 2.0f;
		float hh = h / 2.0f;
		float hd = d / 2.0f;
		
		vertices = new float[]{
			x - hw, y + hh, z - hd,
			x + hw, y + hh, z - hd,
			x + hw, y - hh, z - hd,
			x - hw, y - hh, z - hd,
			
			x - hw, y + hh, z + hd,
			x + hw, y + hh, z + hd,
			x + hw, y - hh, z + hd,
			x - hw, y - hh, z + hd,
		};
		
		faces = new int[] {
			0, 1, 2,	//BACK WALL
			2, 3, 0,
			
			4, 5, 6,	//FRONT WALL
			6, 7, 4,
			
			0, 4, 7,	//LEFT WALL
			7, 3, 0,
			
			5, 1, 2,	//RIGHT WALL
			2, 6, 5,
			
			0, 1, 5,	//TOP WALL
			5, 4, 0,
			
			7, 6, 2,	//BOTTOM WALL
			2, 3, 7
		};
		
		texCoords = new float[] {
			1.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
		};
		
	}
	
	
}

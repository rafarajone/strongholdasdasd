
package game.entity;

import game.World;
import game.engine.Geometry;
import game.engine.Material;
import game.engine.Mesh;
import game.engine.MeshBuffer;
import game.engine.ObjLoader;
import game.engine.Scene;

public class Unit {

	World world;
	Scene scene;
	float x, y;
	
	public Mesh mesh;
	public static Geometry geometry;
	public static Material material;
	
	public Unit(World world, Scene scene, float x, float y) {
		this.world = world;
		this.scene = scene;
		this.x = x;
		this.y = y;
		
	}
	
	public static void loadMesh() {
		MeshBuffer meshBuffer = ObjLoader.load("/models/block.obj").get(0);
		geometry = meshBuffer.getGeometry();
		material = meshBuffer.getMaterial();
	}
	
	public void load() {
		if(mesh != null) return;
		
		mesh = new Mesh(geometry, material);
		//System.out.println("abc " + mesh);
		mesh.position.x = x;
		mesh.position.z = y;
		
		scene.add(mesh);
	}
}

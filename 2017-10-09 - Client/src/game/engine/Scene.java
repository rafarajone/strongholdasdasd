package game.engine;

import java.util.ArrayList;

public class Scene {

	public ArrayList<Mesh> meshList = new ArrayList<Mesh>();
	
	public Scene() {
		
	}
	
	public void add(Mesh mesh) {
		meshList.add(mesh);
	}
	
	public boolean remove(Mesh mesh) {
		return meshList.remove(mesh);
	}
	
	public void clear() {
		meshList.clear();
	}
	
}

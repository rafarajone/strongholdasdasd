package game.engine;

import java.util.ArrayList;

public class Scene {

	public ArrayList<Mesh> meshList = new ArrayList<Mesh>();
	public ArrayList<Batch> batchList = new ArrayList<Batch>();
	
	public Scene() {
		
	}
	
	public void add(Mesh mesh) {
		meshList.add(mesh);
		
		//buildBatches(); //temporary naive way
	}
	
	public void buildBatches() {
		batchList.clear();
		System.out.println("building batches from " + meshList.size() + " meshes...");
		ArrayList<Mesh> meshesToBatch = new ArrayList<Mesh>();
		int curHash = 0;
		for(int i = 0; i < meshList.size(); i++) {
			Mesh m = meshList.get(i);
			
			if(m.hash != curHash) {
				System.out.println(i + " new hash: " + m.hash);
				curHash = m.hash;
				if(meshesToBatch.size() != 0) {
					makeBatch(meshesToBatch);
					meshesToBatch = new ArrayList<Mesh>();
				}
			}
			meshesToBatch.add(m);
		}
		if(meshesToBatch.size() > 0)
			makeBatch(meshesToBatch);
	}
	
	public void makeBatch(ArrayList<Mesh> meshesToBatch) {
		System.out.println("making batch from " + meshesToBatch.size() + " meshes...");
		Batch batch = new Batch(meshesToBatch);
		batchList.add(batch);
		batch.hash = meshesToBatch.get(0).hash;
	}
	
	public boolean remove(Mesh mesh) {
		return meshList.remove(mesh);
	}
	
	public void clear() {
		meshList.clear();
	}
	
}

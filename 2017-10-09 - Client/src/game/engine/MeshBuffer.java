package game.engine;

import java.util.ArrayList;

public class MeshBuffer {

	public ArrayList<Float> verticesList = new ArrayList<Float>();
	public ArrayList<Float> texCoordList = new ArrayList<Float>();
	
	public ArrayList<Integer> facesList = new ArrayList<Integer>();
	public ArrayList<Integer> texCoordIndicesList = new ArrayList<Integer>();
	
	public Texture2D texture;
	
	public MeshBuffer() {
		
	}
	
	public Geometry getGeometry() {
		Geometry geometry = new Geometry();
		
		
		geometry.vertices = new float[verticesList.size()];
		for(int i = 0; i < verticesList.size(); i++) {
			geometry.vertices[i] = verticesList.get(i);
		}
		
		
		/*
		geometry.texCoords = new float[texCoordList.size()];
		for(int i = 0; i < texCoordList.size(); i++) {
			geometry.texCoords[i] = texCoordList.get(i);
		}
		*/
		
		
		geometry.texCoords = new float[texCoordIndicesList.size() * 2];
		for(int i = 0; i < texCoordIndicesList.size(); i++) {
			int index = texCoordIndicesList.get(i);
			geometry.texCoords[i * 2] = texCoordList.get(index * 2);
			geometry.texCoords[i * 2 + 1] = texCoordList.get(index * 2 + 1);
		}
		
		/*
		geometry.texCoords = new float[verticesList.size() / 3 * 2];
		for(int i = 0; i < facesList.size(); i++) {
			int n = facesList.get(i);
			if(geometry.texCoords[n * 2] == 0) continue;
			geometry.texCoords[n * 2] = texCoords[i * 2];
			geometry.texCoords[n * 2 + 1] = texCoords[i * 2 + 1];
		}
		*/
		
		
		geometry.faces = new int[facesList.size()];
		for(int i = 0; i < facesList.size(); i++) {
			geometry.faces[i] = facesList.get(i);
		}
		
		
		return geometry;
	}
	
	public Material getMaterial() {
		Material material = new Material(texture);
		return material;
	}
}

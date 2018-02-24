
package game.engine;

import java.io.InputStream;
import java.util.ArrayList;

import game.Main;

public class ObjLoader {

	private static String readFile(String path) {
		InputStream is = Main.class.getResourceAsStream(path);
		
		String text = null;
		try {
			byte[] data = new byte[is.available()];
			is.read(data);
			text = new String(data);
			
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public static ArrayList<MeshBuffer> load(String path) {
		String text = readFile(path);
		String[] lines = text.split("\n");
		//System.out.println(lines[0]);
		
		ArrayList<MeshBuffer> meshList = new ArrayList<MeshBuffer>();
		ArrayList<MaterialBuffer> materialList = null;
		
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			MeshBuffer currentMesh = null;
			if(meshList.size() > 0) {
				currentMesh = meshList.get(meshList.size() - 1);
			}
			
			if(line.startsWith("#")) {
				
			}else if(line.startsWith("mtllib")){
				line = removeFromString(line, 0, "mtllib ".length());
				String mtlpath = path;
				for(int j = mtlpath.length() - 1; j > 0; j--) {
					if(mtlpath.charAt(j) == '/') {
						mtlpath = removeFromString(mtlpath, j + 1, mtlpath.length() - 1);
						break;
					}
				}
				mtlpath += line;
				materialList = parseMtl(mtlpath);
			}else if(line.startsWith("usemtl")){
				line = removeFromString(line, 0, "usemtl ".length());
				
				for(int j = 0; j < materialList.size(); j++) {
					MaterialBuffer mb = materialList.get(j);
					if(mb.name.equals(line)) {
						currentMesh.texture = mb.texture;
						break;
					}
				}
			}else if(line.startsWith("o")){
				meshList.add(new MeshBuffer());
			}else if(line.startsWith("vn")) {
			
			}else if(line.startsWith("vt")) {
				line = removeFromString(line, 0, "vt ".length());
				String[] values = line.split(" ");
				for(int j = 0; j < 2; j++) {
					Float texCoord = Float.valueOf(values[j]);
					currentMesh.texCoordList.add(texCoord);
				}
			}else if(line.startsWith("v")) {
				line = removeFromString(line, 0, "v ".length());
				String[] values = line.split(" ");
				for(int j = 0; j < 3; j++) {
					Float f = Float.valueOf(values[j]);
					currentMesh.verticesList.add(f);
				}
			}else if(line.startsWith("f")) {
				line = removeFromString(line, 0, "f ".length());
				String[] faces = line.split(" ");
				for(int j = 0; j < 3; j++) {
					String[] values = faces[j].split("/");
					for(int k = 0; k < values.length; k++) {
						if(k == 0) {
							Integer indice = Integer.valueOf(values[k]) - 1;
							currentMesh.facesList.add(indice);
						}else if(k == 1) {
							Integer indice = Integer.valueOf(values[k]) - 1;
							currentMesh.texCoordIndicesList.add(indice);
						}
					}
				}
			}
		}
		
		/*
		MeshBuffer mb1 = meshList.get(0);
	
		System.out.println("mb1.facesList.size(): " + mb1.facesList.size());
		
		System.out.println("verticesList: ");
		for(int i = 0; i< mb1.verticesList.size(); i++) {
			System.out.print(mb1.verticesList.get(i) + " ");
			if(i % 3 == 2)
				System.out.println();
		}
		
		System.out.println("texCoordList: ");
		for(int i = 0; i< mb1.texCoordList.size(); i++) {
			System.out.print(mb1.texCoordList.get(i) + " ");
			if(i % 2 == 1)
				System.out.println();
		}
		
		System.out.println("facesList: ");
		for(int i = 0; i< mb1.facesList.size(); i++) {
			System.out.print(mb1.facesList.get(i) + " ");
			if(i % 3 == 2)
				System.out.println();
		}
		
		System.out.println("texCoordIndiciesList: ");
		for(int i = 0; i< mb1.texCoordIndicesList.size(); i++) {
			System.out.print(mb1.texCoordIndicesList.get(i) + " ");
			if(i % 3 == 2)
				System.out.println();
		}
		*/
		
		return meshList;
	}
	
	private static ArrayList<MaterialBuffer> parseMtl(String path){
		ArrayList<MaterialBuffer> materialList = new ArrayList<MaterialBuffer>();
		
		String mtltext = readFile(path);
		
		String[] lines = mtltext.split("\n");
		
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			MaterialBuffer currentMaterial = null;
			if(materialList.size() > 0) {
				currentMaterial = materialList.get(materialList.size() - 1);
			}
			
			if(line.startsWith("#")) {
				
			}else if(line.startsWith("newmtl")) {
				line = removeFromString(line, 0, "newmtl ".length());
				materialList.add(new MaterialBuffer(line));
			}else if(line.startsWith("map_Kd")) {
				line = removeFromString(line, 0, "map_Kd ".length());
				
				String texturepath = path;
				for(int j = texturepath.length() - 1; j > 0; j--) {
					if(texturepath.charAt(j) == '/') {
						texturepath = removeFromString(texturepath, j + 1, texturepath.length() - 1);
						break;
					}
				}
				texturepath += line;
				//System.out.println("texturepath: " + texturepath);
				Texture2D texture = new Texture2D(texturepath);
				currentMaterial.texture = texture;
			}
		}
		
		return materialList;
	}
	
	private static String removeFromString(String text, int begin, int end) {
		String out = "";
		if(begin > 0) {
			out += text.substring(0, begin);
		}
		if(end < text.length() - 1) {
			out += text.substring(end, text.length());
		}
		return out;
	}
	
}

package game.state;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import game.Main;
import game.World;
import game.engine.Camera;
import game.engine.CubeGeometry;
import game.engine.Geometry;
import game.engine.Keyboard;
import game.engine.Material;
import game.engine.Mesh;
import game.engine.MeshBuffer;
import game.engine.ObjLoader;
import game.engine.Scene;
import game.engine.Sprite;
import game.engine.SpriteMaterial;
import game.engine.Texture2D;
import game.engine.Window;
import game.entity.Unit;
import game.net.GameSocket;

public class Game extends State{

	GameSocket socket;
	World world;
	Window window;
	Keyboard keyboard;
	
	Camera camera;

	Scene scene = new Scene();
	
	
	public Game(GameSocket socket, World world, Window window, Keyboard keyboard) {
		this.socket = socket;
		this.world = world;
		this.window = window;
		this.keyboard = keyboard;
		
		camera = new Camera((float) (Math.PI / 4.0f), (float)window.width / (float)window.height, 0.1f, 1000.0f);
		camera.rotationOrder = "XYZ";
		camera.position.y = -10;
		camera.rotation.x = (float) (Math.PI / 4);
		
		Unit.loadMesh();
		
		

		{
			Texture2D texture = new Texture2D("/textures/texture.png");
			Material mat1 = new SpriteMaterial(texture);
			//System.out.println(mat1.program.fragment.source);
			Sprite sprite = new Sprite(mat1);
			scene.add(sprite);
			sprite.position.set(0, 0, -10);
		}
		
		{
			Geometry geometry = new CubeGeometry(1.0f, 1.0f, 1.0f);
			
			Texture2D texture = new Texture2D("/textures/gradient.png");
			Material material = new Material(texture);
			//System.out.println(material.program.fragment.source);
			Mesh mesh = new Mesh(geometry, material);
			scene.add(mesh);
		}
		
		{
			Geometry geometry = new CubeGeometry(1000.0f, 1000.0f, 1000.0f);
			
			Texture2D texture = new Texture2D("/textures/skybox.jpg");
			Material material = new Material(texture);
			//System.out.println(material.program.fragment.source);
			Mesh mesh = new Mesh(geometry, material);
			scene.add(mesh);
		}
		
		{
			ArrayList<MeshBuffer> meshList = ObjLoader.load("/models/block.obj");
			Mesh mesh2 = Mesh.load(meshList.get(0));
			mesh2.position.x = 5;
			//Main.printArray(mesh2.geometry.vertices);
			//Main.printArray(mesh2.geometry.faces);
			
			scene.add(mesh2);
		}
		
		
		
		socket.send("login", "asdasd");
		
		socket.on("units", buffer -> {
			System.out.println("units");
			
			System.out.println("world.unitList.size(): " + world.unitList.size());
			System.out.println("scene.meshList.size(): " + scene.meshList.size());
			
			for(int i = 0; i < world.unitList.size(); i++) {
				Unit u = world.unitList.get(i);
				scene.remove(u.mesh);
			}
			
			
			for(int i = 0; i < world.unitList.size(); i++) {
				Unit u = world.unitList.get(i);
				System.out.println(u.mesh);
			}
			
			
			/*
			if(!world.unitList.isEmpty() && world.unitList.get(0).mesh != null) {
				Mesh m0 = world.unitList.get(0).mesh;
				for(int i = 0; i < scene.meshList.size(); i++) {
					Mesh m = scene.meshList.get(i);
					//System.out.println(m0);
					if(m.toString().equals(m0.toString())) {
						System.out.println("sdsdf");
					}
				}
			}
			*/
			
			world.unitList.clear();
			
			System.out.println("world.unitList.size(): " + world.unitList.size());
			System.out.println("scene.meshList.size(): " + scene.meshList.size());
			
			int size = buffer.readInt();
			for(int i = 0; i < size; i++) {
				float x = buffer.readFloat();
				float y = buffer.readFloat();
				//System.out.println(x + "  " + y);
				Unit unit = new Unit(world, scene, x, y);
				
				world.unitList.add(unit);
			}
			
			System.out.println("world.unitList.size(): " + world.unitList.size());
			System.out.println("scene.meshList.size(): " + scene.meshList.size());
		});
	}
	
	public void render() {
		window.render(scene, camera);
	}
	
	public synchronized void loadUnits() {
		for(int i = 0; i < world.unitList.size(); i++) {
			Unit u = world.unitList.get(i);
			u.load();
			System.out.println("loadUnits: " + u.mesh);
		}
		
	}
	
	public void update() {
		
		loadUnits();
		
		double speed = 0.1;
		
		double sin = Math.sin(camera.rotation.y + Math.PI / 2);
		double cos = Math.cos(camera.rotation.y + Math.PI / 2);
		
		
		if(keyboard.isPressed(KeyEvent.VK_W)) {
			camera.position.z += speed * sin;
			camera.position.x += speed * cos;
		}
		if(keyboard.isPressed(KeyEvent.VK_S)) {
			camera.position.z += speed * -sin;
			camera.position.x += speed * -cos;
		}
		if(keyboard.isPressed(KeyEvent.VK_A)) {
			camera.position.z += speed * -cos;
			camera.position.x += speed * sin;
		}
		if(keyboard.isPressed(KeyEvent.VK_D)) {
			camera.position.z += speed * cos;
			camera.position.x += speed * -sin;
		}
		
		if(keyboard.isPressed(Keyboard.SHIFT)) {
			camera.position.y += speed;
		}
		if(keyboard.isPressed(Keyboard.CONTROL)) {
			camera.position.y -= speed;
		}
		
		if(keyboard.isPressed(Keyboard.LEFT_ARROW)) {
			camera.rotation.y -= 0.04f;
		}
		if(keyboard.isPressed(Keyboard.RIGHT_ARROW)) {
			camera.rotation.y += 0.04f;
		}
		if(keyboard.isPressed(Keyboard.UP_ARROW)) {
			camera.rotation.x -= 0.04f;
		}
		if(keyboard.isPressed(Keyboard.DOWN_ARROW)) {
			camera.rotation.x += 0.04f;
		}
		
		camera.recalculate();
	}
	
}

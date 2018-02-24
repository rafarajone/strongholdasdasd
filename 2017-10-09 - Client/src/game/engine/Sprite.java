package game.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL45.*;

import org.joml.Vector3f;

import game.Main;

public class Sprite extends Mesh {

	private static Geometry spriteGeometry = new Geometry();

	static {
		
		
		spriteGeometry.vertices = new float[] {
			-0.5f, 0.5f, 0.0f,
			0.5f, 0.5f, 0.0f,
			0.5f, -0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f
		};
		
		spriteGeometry.faces = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		spriteGeometry.texCoords = new float[] {
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
		};
	}

	public Sprite(Material material) {
		super(spriteGeometry, material);
	}

	/*
	public void render(Camera camera) {
		material.program.use();

		glEnable(GL_DEPTH_TEST);
		// glEnable(GL_CULL_FACE);
		// glFrontFace(GL_CCW);
		// glCullFace(GL_BACK);

		material.texture.bind();
		glActiveTexture(GL_TEXTURE0);

		worldMatrix.rotationX(rotation.x);
		worldMatrix.rotationY(rotation.y);
		worldMatrix.rotationZ(rotation.z);
		worldMatrix.translate(position);

		float[] worldElements = new float[16];
		worldMatrix.get(worldElements);

		float[] projectionElements = new float[16];
		camera.projectionMatrix.get(projectionElements);

		float[] viewElements = new float[16];
		camera.viewMatrix.get(viewElements);

		
		System.out.println("sdf");
		for(int i = 0; i < worldElements.length; i++) {
			System.out.print(worldElements[i] + " ");
		}
		System.out.println();
		

		glUniformMatrix4fv(projectionMatrixLocation, false, projectionElements);
		glUniformMatrix4fv(viewMatrixLocation, false, viewElements);
		glUniformMatrix4fv(worldMatrixLocation, false, worldElements);
		
		glDrawElements(GL_TRIANGLES, geometry.faces.length, GL_UNSIGNED_INT, 0);
	}
	*/
}

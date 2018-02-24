package game.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Mesh {
	
	Matrix4f worldMatrix = new Matrix4f();
	public Vector3f rotation = new Vector3f();
	public Vector3f position = new Vector3f();
	
	public Geometry geometry;
	public Material material;
	
	int projectionMatrixLocation;
	int viewMatrixLocation;
	int worldMatrixLocation;

	int vao;
	
	public Mesh(Geometry geometry, Material material) {
		this.geometry = geometry;
		this.material = material;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		material.program.use();
		//System.out.println(material.program.fragment.source);
		
		int vb = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vb);
		glBufferData(GL_ARRAY_BUFFER, geometry.vertices, GL_STATIC_DRAW);

		int positionLocation = glGetAttribLocation(material.program.id, "aPosition");
		glVertexAttribPointer(positionLocation, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
		glEnableVertexAttribArray(positionLocation);

		if(geometry.texCoords != null) {
			int tb = glCreateBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, tb);
			glBufferData(GL_ARRAY_BUFFER, geometry.texCoords, GL_STATIC_DRAW);
	
			int texCoordsLocation = glGetAttribLocation(material.program.id, "aTexCoord");
			glVertexAttribPointer(texCoordsLocation, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
			glEnableVertexAttribArray(texCoordsLocation);
		}
		
		int ib = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ib);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, geometry.faces, GL_STATIC_DRAW);

		if(geometry.texCoords == null) {
			//System.out.println("geometry.texCoords == null");
			glUniform1i(glGetUniformLocation(material.program.id, "useTexture"), 0);
		}else {
			//System.out.println("geometry.texCoords != null");
			glUniform1i(glGetUniformLocation(material.program.id, "useTexture"), 1);
		}
		
		projectionMatrixLocation = glGetUniformLocation(material.program.id, "projectionMatrix");
		viewMatrixLocation = glGetUniformLocation(material.program.id, "viewMatrix");
		worldMatrixLocation = glGetUniformLocation(material.program.id, "worldMatrix");
	}
	
	public static Mesh load(MeshBuffer buffer) {
		return new Mesh(buffer.getGeometry(), buffer.getMaterial());
	}
	
	public void render(Camera camera) {	
		glBindVertexArray(vao);
		material.program.use();

		glEnable(GL_DEPTH_TEST);
		
		if(material.texture != null) {
			material.texture.bind();
			glActiveTexture(GL_TEXTURE0);
		}

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

		glUniformMatrix4fv(projectionMatrixLocation, false, projectionElements);
		glUniformMatrix4fv(viewMatrixLocation, false, viewElements);
		glUniformMatrix4fv(worldMatrixLocation, false, worldElements);

		glDrawElements(GL_TRIANGLES, geometry.faces.length, GL_UNSIGNED_INT, 0);
	}
}

package game.engine;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL45.*;

public class Batch {

	public ArrayList<Mesh> meshList;
	
	Mesh mesh0;
	
	int vao;
	
	public int hash = 0;
	
	public Batch(ArrayList<Mesh> meshList) {
		this.meshList = meshList;
		mesh0 = meshList.get(0);
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		if(!mesh0.material.program.isUsedNow()) {
			mesh0.material.program.use();
		}
		//System.out.println(material.program.fragment.source);
		
		//////////////////////
		int vb = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vb);
		glBufferData(GL_ARRAY_BUFFER, mesh0.geometry.vertices, GL_STATIC_DRAW);

		int positionLocation = glGetAttribLocation(mesh0.material.program.id, "aPosition");
		System.out.println("positionLocation " + positionLocation);
		glVertexAttribPointer(positionLocation, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
		glEnableVertexAttribArray(positionLocation);
		///////////////////////
		if(mesh0.geometry.texCoords != null) {
			int tb = glCreateBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, tb);
			glBufferData(GL_ARRAY_BUFFER, mesh0.geometry.texCoords, GL_STATIC_DRAW);
	
			int texCoordsLocation = glGetAttribLocation(mesh0.material.program.id, "aTexCoord");
			System.out.println("texCoordsLocation " + texCoordsLocation);
			glVertexAttribPointer(texCoordsLocation, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
			glEnableVertexAttribArray(texCoordsLocation);
		}
		////////////////////////
		int ib = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ib);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh0.geometry.faces, GL_STATIC_DRAW);

		if(mesh0.geometry.texCoords == null) {
			//System.out.println("geometry.texCoords == null");
			glUniform1i(glGetUniformLocation(mesh0.material.program.id, "useTexture"), 0);
		}else {
			//System.out.println("geometry.texCoords != null");
			glUniform1i(glGetUniformLocation(mesh0.material.program.id, "useTexture"), 1);
		}
		//////////////////////////
		
		float[] objectsPositions = new float[meshList.size() * 3];
		for(int i = 0; i < meshList.size(); i++) {
			Mesh mesh = meshList.get(i);
			objectsPositions[i * 3 + 0] = mesh.position.x;
			objectsPositions[i * 3 + 1] = mesh.position.y;
			objectsPositions[i * 3 + 2] = mesh.position.z;
		}
		
		int opb = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, opb);
		glBufferData(GL_ARRAY_BUFFER, objectsPositions, GL_STATIC_DRAW);
		
		int objectPositionLocation = glGetAttribLocation(mesh0.material.program.id, "aObjectPosition");
		System.out.println("objectPositionLocation " + objectPositionLocation);
		glEnableVertexAttribArray(objectPositionLocation);
		glVertexAttribPointer(objectPositionLocation, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glVertexAttribDivisor(objectPositionLocation, 1);
	}
	
	public void render() {
		glBindVertexArray(vao);
		glEnable(GL_MULTISAMPLE);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
		if(!mesh0.material.program.isUsedNow()) {
			mesh0.material.program.use();
			glEnable(GL_DEPTH_TEST);
		}

		if(mesh0.material.texture != null) {
			if(!mesh0.material.texture.isBound()) {
				mesh0.material.texture.bind();
			}
			glActiveTexture(GL_TEXTURE0);
		}
		
		mesh0.material.texture.bind();
		glActiveTexture(GL_TEXTURE0);

		float[] worldElements = new float[16];
		mesh0.worldMatrix.get(worldElements);
		
		glUniformMatrix4fv(mesh0.worldMatrixLocation, false, worldElements);

		//glDrawArraysInstanced(GL_TRIANGLES, 0, 6, 100);
		//System.out.println(meshList.size());
		glDrawElementsInstanced(GL_TRIANGLES, mesh0.geometry.faces.length, GL_UNSIGNED_INT, 0, meshList.size());
		//glDrawElements(GL_TRIANGLES, mesh0.geometry.faces.length, GL_UNSIGNED_INT, 0);
		
		glBindVertexArray(0);
	}
}

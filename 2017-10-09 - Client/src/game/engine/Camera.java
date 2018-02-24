package game.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	public Matrix4f projectionMatrix;
	public Matrix4f viewMatrix;
	
	public Vector3f position = new Vector3f(0);
	public Vector3f rotation = new Vector3f(0);
	
	public String rotationOrder = "XYZ";
	
	public Camera() {
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective((float) Math.toRadians(45), 1.0f, 0.1f, 1000.0f);
		
		viewMatrix = new Matrix4f();
	}
	
	public Camera(float fovy, float aspect, float zNear, float zFar) {
		projectionMatrix = new Matrix4f();
		projectionMatrix.setPerspective(fovy, aspect, zNear, zFar);
		
		viewMatrix = new Matrix4f();
	}
	
	public void recalculate() {
		viewMatrix = new Matrix4f();

		for(int i = 0; i < 3; i++) {
			switch(rotationOrder.charAt(i)) {
			case 'X':
				viewMatrix.rotateX(rotation.x);
				break;
			case 'Y':
				viewMatrix.rotateY(rotation.y);
				break;
			case 'Z':
				viewMatrix.rotateZ(rotation.z);
				break;
			default:
				System.out.println("Unrecognized symbols in rotation order");
				break;
			}
		}
		
		viewMatrix.translate(position);
	}
}

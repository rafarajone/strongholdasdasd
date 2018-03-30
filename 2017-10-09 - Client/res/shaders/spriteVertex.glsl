
attribute vec3 aObjectPosition;
attribute vec3 aPosition;
attribute vec2 aTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 worldMatrix;

varying vec2 vTexCoord;

void main(){

	vTexCoord = aTexCoord;
	//gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPosition, 1.0);
	//gl_InstanceID

	//aPosition.x += (gl_InstanceID % 800) * 1;
	//aPosition.y += (gl_InstanceID / 800) * 1;



	vec4 pos = projectionMatrix * viewMatrix * worldMatrix * vec4(aPosition + aObjectPosition, 1.0);

	gl_Position = pos;
}


attribute vec3 aPosition;
attribute vec2 aTexCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 worldMatrix;

varying vec2 vTexCoord;
varying float dist;

void main(){

	vTexCoord = aTexCoord;
	gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPosition, 1.0);

	dist = sqrt(gl_Position.x * gl_Position.x + gl_Position.y * gl_Position.y + gl_Position.z * gl_Position.z);
}

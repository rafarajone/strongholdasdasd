
varying vec2 vTexCoord;

uniform sampler2D sampler;

void main(){

	gl_FragColor = texture2D(sampler, vTexCoord);
}

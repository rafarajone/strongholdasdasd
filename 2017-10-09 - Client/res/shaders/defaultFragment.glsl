
varying vec2 vTexCoord;
varying float dist;

uniform sampler2D sampler;
uniform int useTexture;

void main(){

	if(useTexture == 1){
		gl_FragColor = texture2D(sampler, vTexCoord);
	}else{
		gl_FragColor = vec4(dist, clamp(mod(dist / 10.0f, 1.0f), 0.0f, 1.0f), 1.0, 1.0);
	}
	//gl_FragColor = vec4(1.0, useTexture, 1.0, 1.0);
	//gl_FragColor = texture2D(sampler, vTexCoord);
}

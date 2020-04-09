#version 460

layout (location = 0) out vec4 outColour;

in vec3 position_FS;

uniform samplerCube environmentMap;
uniform float gamma;

void main()
{
	vec3 envColour = textureLod(environmentMap, normalize(position_FS), 0.2).rgb;
	
	//envColour = envColour / (envColour + vec3(1.0));
	//envColour = pow(envColour, vec3(1.0 / gamma));
	
	outColour = vec4(envColour, 1);
}
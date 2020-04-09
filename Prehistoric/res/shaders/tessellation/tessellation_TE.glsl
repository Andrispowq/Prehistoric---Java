#version 430

layout (triangles, fractional_odd_spacing, ccw) in;

struct Material
{
	sampler2D albedoMap;
	sampler2D normalMap;
	sampler2D displacementMap;
	sampler2D metallicMap;
	sampler2D roughnessMap;
	sampler2D occlusionMap;
	sampler2D emissionMap;
	
	vec3 colour;
	int usesNormalMap;
	float heightScale;
	float metallic;
	float roughness;
	float ambientOcclusion;
	vec3 emission;
};

in vec2 texture_TE[];
in vec3 normal_TE[];

out vec2 texture_GS;
out vec3 normal_GS;

vec4 getPosition()
{
	return gl_TessCoord.x * gl_in[0].gl_Position
			+ gl_TessCoord.y * gl_in[1].gl_Position
			+ gl_TessCoord.z * gl_in[2].gl_Position;
}

vec2 getTexture()
{
	return gl_TessCoord.x * texture_TE[0]
		+ gl_TessCoord.y * texture_TE[1]
		+ gl_TessCoord.z * texture_TE[2];
}

vec3 getNormal()
{
	return gl_TessCoord.x * normal_TE[0]
		+ gl_TessCoord.y * normal_TE[1]
		+ gl_TessCoord.z * normal_TE[2];
}

void main()
{
	vec4 position = getPosition();
	vec2 texCoord = getTexture();
	vec3 normal = getNormal();
	
	gl_Position = position;
	texture_GS = texCoord;
	normal_GS = normal;
}
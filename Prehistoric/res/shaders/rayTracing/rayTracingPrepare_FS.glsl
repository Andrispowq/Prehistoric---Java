#version 460

layout (location = 0) out vec4 positionMetallic;
layout (location = 1) out vec4 albedoRoughness;
layout (location = 2) out vec4 normalLit;
layout (location = 3) out vec4 emission;

struct Material
{
	sampler2D albedoMap;
	sampler2D normalMap;
	sampler2D displacementMap;
	sampler2D metallicMap;
	sampler2D roughnessMap;
	sampler2D emissionMap;
	
	vec3 colour;
	int usesNormalMap;
	float heightScale;
	float metallic;
	float roughness;
	vec3 emission;
};

in vec3 position_FS;
in vec2 texture_FS;
in vec3 normal_FS;
in vec3 tangent_FS;

uniform Material material;
uniform vec3 cameraPosition;
uniform int highDetailRange;

vec3 getColor(sampler2D map, vec3 alternateValue, vec2 texCoords)
{
	if(alternateValue.r == -1)
		return texture(map, texCoords).rgb;
	else
		return alternateValue;
}

vec3 getColor(sampler2D map, float alternateValue, vec2 texCoords)
{
	if(alternateValue == -1)
		return texture(map, texCoords).rgb;
	else
		return vec3(alternateValue);
}

void main()
{
	vec3 albedo = getColor(material.albedoMap, material.colour, texture_FS);
	
	float metallic = getColor(material.metallicMap, material.metallic, texture_FS).r;
	float roughness = getColor(material.roughnessMap, material.roughness, texture_FS).r;
	
	vec3 emissionValue = getColor(material.emissionMap, material.emission, texture_FS);
	
	float dist = length(cameraPosition - position_FS);
	
	vec3 normal = normalize(normal_FS);
	vec3 bumpNormal = vec3(0);
	
	if(dist > highDetailRange - 50 && material.usesNormalMap != 0)
	{
		float attenuation = clamp(-dist / (highDetailRange - 50) + 1, 0, 1);
		
		vec3 bitangent = normalize(cross(tangent_FS, normal));
		mat3 tbn = mat3(tangent_FS, normal, bitangent);
		
		bumpNormal = 2 * texture(material.normalMap, texture_FS).rgb - 1;
		bumpNormal = normalize(bumpNormal);
		bumpNormal.xz *= attenuation;
		
		normal = normalize(tbn * bumpNormal);
	}
	
	positionMetallic = vec4(position_FS, metallic);
	albedoRoughness = vec4(albedo, roughness);
	normalLit = vec4(normal, 1);
	emission = vec4(emissionValue, 1);
}
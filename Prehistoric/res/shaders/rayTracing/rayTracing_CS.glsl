#version 460

layout(local_size_x = 16, local_size_y = 16) in;

layout(binding = 0) uniform writeonly image2D scene;

uniform sampler2D positionMetallic;
uniform sampler2D albedoRoughness;
uniform sampler2D normalLit;
uniform sampler2D emission;

void main()
{
	
}
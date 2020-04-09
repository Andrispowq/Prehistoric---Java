#version 460

layout (location = 0) in vec3 position_VS;
layout (location = 1) in vec2 texture_VS;
layout (location = 2) in vec3 normal_VS;
layout (location = 3) in mat4 transform_VS;

out vec2 texture_GS;
out vec3 normal_GS;

void main()
{
	gl_Position = transform_VS * vec4(position_VS, 1);
	
	texture_GS = texture_VS;
	normal_GS = normalize((transform_VS * vec4(normal_VS, 0)).xyz);
}
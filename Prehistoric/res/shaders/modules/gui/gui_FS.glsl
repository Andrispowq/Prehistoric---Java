#version 460

layout (location = 0) out vec4 outColour;

in vec2 texture_FS;

uniform sampler2D guiTexture;

void main()
{
	outColour = texture(guiTexture, texture_FS);
}
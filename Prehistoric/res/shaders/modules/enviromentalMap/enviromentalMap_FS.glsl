#version 460

layout (location = 0) out vec4 out_colour;

in vec3 position_FS;

uniform sampler2D equirectangularMap;

const vec2 invAtan = vec2(0.1591, 0.3183);

vec2 SampleSphericalMap(vec3 v)
{
    vec2 uv = vec2(atan(v.z, v.x), asin(-v.y));
	
    uv *= invAtan;
    uv += 0.5;
	
    return uv;
}

void main()
{		
    vec2 uv = SampleSphericalMap(normalize(position_FS));
    vec3 color = texture(equirectangularMap, uv).rgb;
    
    out_colour = vec4(color, 1.0);
}
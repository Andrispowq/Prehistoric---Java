#version 430

layout (vertices = 3) out;

in vec2 texture_TC[];
in vec3 normal_TC[];

out vec2 texture_TE[];
out vec3 normal_TE[];

uniform int tessellationFactor;
uniform float tessellationSlope;
uniform float tessellationShift;
uniform vec3 cameraPosition;

const int AB = 2;
const int BC = 1;
const int CA = 0;

float lodFactor(float dist)
{
	return max(0.0, tessellationFactor / pow(dist, tessellationSlope) + tessellationShift);
}

void main()
{
	if(gl_InvocationID == 0)
	{
		vec3 abMid = vec3(gl_in[0].gl_Position + gl_in[1].gl_Position) / 2;
		vec3 bcMid = vec3(gl_in[1].gl_Position + gl_in[2].gl_Position) / 2;
		vec3 caMid = vec3(gl_in[2].gl_Position + gl_in[0].gl_Position) / 2;
		
		float distAB = distance(abMid, cameraPosition);
		float distBC = distance(bcMid, cameraPosition);
		float distCA = distance(caMid, cameraPosition);
		
		gl_TessLevelOuter[AB] = mix(1, gl_MaxTessGenLevel, lodFactor(distAB));
		gl_TessLevelOuter[BC] = mix(1, gl_MaxTessGenLevel, lodFactor(distBC));
		gl_TessLevelOuter[CA] = mix(1, gl_MaxTessGenLevel, lodFactor(distCA));
		
		gl_TessLevelInner[0] = (gl_TessLevelOuter[AB] + gl_TessLevelOuter[BC] + gl_TessLevelOuter[CA]) / 3;
	}
	
	gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
	
	texture_TE[gl_InvocationID] = texture_TC[gl_InvocationID];
	normal_TE[gl_InvocationID] = normal_TC[gl_InvocationID];
}
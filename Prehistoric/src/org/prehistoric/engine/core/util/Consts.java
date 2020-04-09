package org.prehistoric.engine.core.util;

import org.math.vector.Vector2f;

public class Consts
{
    //Texture map constants
    public static final String ALBEDO_MAP_POSTFIX = "_DIF";
    public static final String NORMAL_MAP_POSTFIX = "_NRM";
    public static final String DISPLACEMENT_MAP_POSTFIX = "_DISP";
    public static final String SPECULAR_MAP_POSTFIX = "_SPEC";
    public static final String METALLIC_MAP_POSTFIX = "_MET";
    public static final String ROUGHNESS_MAP_POSTFIX = "_RGH";
    public static final String OCCLUSION_MAP_POSTFIX = "_OCC";
    public static final String EMISSION_MAP_POSTFIX = "_EMIS";
    
    public static final String ALBEDO_MAP = "albedoMap";
    public static final String NORMAL_MAP = "normalMap";
    public static final String DISPLACEMENT_MAP = "displacementMap";
    public static final String SPECULAR_MAP = "specularMap";
    public static final String METALLIC_MAP = "metallicMap";
    public static final String ROUGHNESS_MAP = "roughnessMap";
    public static final String OCCLUSION_MAP = "occlusionMap";
    public static final String EMISSION_MAP = "emissionMap";
    public static final String ALPHA_MAP = "alphaMap";
    
    //Terrain config constants
    public static final String HEIGHT_SCALE_POSTFIX = "_heightScale";
    public static final String HORIZONTAL_SCALE_POSTFIX = "_horizontalScale";
    public static final String METALLIC_POSTFIX = "_metallic";
    public static final String ROUGHNESS_POSTFIX = "_roughness";
    public static final String OCCLUSION_POSTFIX = "_occlusion";
    
    public static final String HEIGHT_SCALE = "heightScale";
    public static final String HORIZONTAL_SCALE = "horizontalScale";
   
    //Uniform constants - Material
    public static final String COLOUR = "colour"; 
    public static final String METALLIC = "metallic";
    public static final String ROUGHNESS = "roughness"; 
    public static final String OCCLUSION = "ambientOcclusion"; 
    public static final String EMISSION = "emission"; 
    public static final String ALPHA = "alpha"; 

    //Material ID constants - uniform constants
    public static final String SPECULAR_INTENSITY = "specularIntensity"; 
    public static final String SPECULAR_POWER = "specularPower"; 
    
    //Component constants
    public static final String RENDERER_COMPONENT = "renderer";
    public static final String RENDERER_2D_COMPONENT = "renderer2D";
    public static final String WIREFRAME_RENDERER_COMPONENT = "wireframeRenderer";
    public static final String LIGHT_COMPONENT = "light";
    public static final String PHYSICS_OBJECT_COMPONENT = "physicsObject";
    public static final String ENVIROMENTAL_PROBE_COMPONENT = "envMap";
    
    public static float clampf(float value, float min, float max)
    {
	if(value < min)
	    return value = min;
	else if(value > max)
	    return value = max;
	else
	    return value;
    }
    
    public static double clampd(double value, double min, double max)
    {
	if(value < min)
	    return value = min;
	else if(value > max)
	    return value = max;
	else
	    return value;
    }
    
    public static int clampi(int value, int min, int max)
    {
	if(value < min)
	    return value = min;
	else if(value > max)
	    return value = max;
	else
	    return value;
    }
    
    public static boolean inside(Vector2f point, Vector2f start, Vector2f end)
    {
	if(point.x > start.x && point.x < end.x)
	{
	    if(point.y > start.y && point.y < end.y)
	    {
		return true;
	    }
	}
	
	return false;
    }
}

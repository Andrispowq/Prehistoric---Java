package org.prehistoric.engine.engines.rendering.shader.rayTracing;

import java.util.ArrayList;

import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class ScreenSpaceRayTracingShader extends Shader
{
    private static ScreenSpaceRayTracingShader instance = null;
    
    public static ScreenSpaceRayTracingShader getInstance()
    {
	if(instance == null)
	    instance = new ScreenSpaceRayTracingShader();
	
	return instance;
    }
    
    protected ScreenSpaceRayTracingShader()
    {
	super();
	
	AddComputeShader(ResourceLoader.LoadShader("shaders/rayTracing/rayTracing_CS.glsl"));
	
	CompileShader();
    }
    
    public void UpdateUniforms(ArrayList<Light> lights)
    {
	
    }
}

package org.prehistoric.engine.engines.rendering.shader.enviromentalMap;

import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class BRDFIntegrateShader extends Shader
{
    private static BRDFIntegrateShader instance = null;
    
    public static BRDFIntegrateShader getInstance()
    {
	if(instance == null)
	    instance = new BRDFIntegrateShader();
	
	return instance;
    }
    
    protected BRDFIntegrateShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/brdfIntegrate_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/brdfIntegrate_FS.glsl"));
	
	CompileShader();
    }
}

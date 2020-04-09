package org.prehistoric.engine.components.renderer;

import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class RenderInfo
{
    private RenderConfig wireframeConfig;
    
    private Shader instanceShader;    
    private Shader wireframeShader;
    
    public RenderInfo(RenderConfig wireframeConfig, Shader instanceShader, Shader wireframeShader)
    {
	this.wireframeConfig = wireframeConfig;
	
	this.instanceShader = instanceShader;
	this.wireframeShader = wireframeShader;
    }
    
    public RenderInfo()
    {
	wireframeConfig = null;
	
	instanceShader = null;
	wireframeShader = null;
    }

    public Shader getInstanceShader()
    {
        return instanceShader;
    }

    public RenderConfig getWireframeConfig()
    {
        return wireframeConfig;
    }

    public Shader getWireframeShader()
    {
        return wireframeShader;
    }
}

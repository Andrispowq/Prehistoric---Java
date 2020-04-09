package org.prehistoric.engine.engines.rendering.shader.gpgpu;

import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class SplatMapShader extends Shader
{
    private static SplatMapShader instance = null;
    
    public static SplatMapShader GetInstance()
    {
	if(instance == null)
	    instance = new SplatMapShader();
	
	return instance;
    }
    
    protected SplatMapShader()
    {
	super();
	
	AddComputeShader(ResourceLoader.LoadShader("shaders/modules/gpgpu/splatMap_CS.glsl"));
	
	CompileShader();
	
	AddUniform("normalmap");
	AddUniform("N");
    }
    
    public void UpdateUniforms(Texture normalmap, int N)
    {
        normalmap.Bind(0);
        
        SetUniformi("normalmap", 0);
        SetUniformi("N", N);
    }
}

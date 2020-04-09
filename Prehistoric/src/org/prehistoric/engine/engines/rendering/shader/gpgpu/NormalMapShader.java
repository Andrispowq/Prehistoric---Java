package org.prehistoric.engine.engines.rendering.shader.gpgpu;

import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class NormalMapShader extends Shader
{
    private static NormalMapShader instance = null;
    
    public static NormalMapShader GetInstance()
    {
	if(instance == null)
	    instance = new NormalMapShader();
	
	return instance;
    }
    
    protected NormalMapShader()
    {
	super();
	
	AddComputeShader(ResourceLoader.LoadShader("shaders/modules/gpgpu/normalMap_CS.glsl"));
	
	CompileShader();
	
	AddUniform("heightmap");
	AddUniform("N");
	AddUniform("strength");
    }
    
    public void UpdateUniforms(Texture heightmap, int N, float strength)
    {
	heightmap.Bind(0);
	
	SetUniformi("heightmap", 0);
	SetUniformi("N", N);
	SetUniformf("strength", strength);
    }
}

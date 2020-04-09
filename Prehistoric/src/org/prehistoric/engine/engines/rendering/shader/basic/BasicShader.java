package org.prehistoric.engine.engines.rendering.shader.basic;

import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class BasicShader extends Shader
{
    private static BasicShader instance = null;
    
    public static BasicShader GetInstance()
    {
	if(instance == null)
	    instance = new BasicShader();
	
	return instance;
    }
    
    protected BasicShader()
    {
	AddVertexShader(ResourceLoader.LoadShader("shaders/basic/basic_VS.glsl"));
	AddVertexShader(ResourceLoader.LoadShader("shaders/basic/basic_FS.glsl"));
	CompileShader();
	
	AddUniform("scale");
    }
    
    public void UpdateUniforms(GameObject object)
    {
        SetUniformf("scale", .5f);
    }
}

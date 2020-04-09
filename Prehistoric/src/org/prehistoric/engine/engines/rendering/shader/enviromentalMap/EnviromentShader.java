package org.prehistoric.engine.engines.rendering.shader.enviromentalMap;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class EnviromentShader extends Shader
{
    private static EnviromentShader instance = null;
    
    public static EnviromentShader getInstance()
    {
	if(instance == null)
	    instance = new EnviromentShader();
	
	return instance;
    }
    
    protected EnviromentShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/enviroment_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/enviroment_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("environmentMap");
	AddUniform("gamma");
    }
    
    public void UpdateUniforms(Matrix4f projection, Matrix4f view, Texture texture)
    {
	SetUniform("m_view", view);
	SetUniform("m_projection", projection);
	
        texture.Bind(0);
        SetUniformi("environmentMap", 0);

	SetUniformf("gamma", EngineConfig.getGamma());
    }
}

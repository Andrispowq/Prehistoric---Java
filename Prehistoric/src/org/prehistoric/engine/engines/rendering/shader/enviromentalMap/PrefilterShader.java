package org.prehistoric.engine.engines.rendering.shader.enviromentalMap;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class PrefilterShader extends Shader
{
    private static PrefilterShader instance = null;
    
    public static PrefilterShader getInstance()
    {
	if(instance == null)
	    instance = new PrefilterShader();
	
	return instance;
    }
    
    protected PrefilterShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/prefilter_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/prefilter_FS.glsl"));
	
	CompileShader();

	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("environmentMap");
	AddUniform("roughness");
	AddUniform("resolution");
    }
    
    public void UpdateUniforms(Matrix4f projection, Matrix4f view, Texture texture, float roughness)
    {
	SetUniform("m_view", view);
	SetUniform("m_projection", projection);
	
        texture.Bind(0);
        SetUniformi("environmentMap", 0);
        
        SetUniformf("roughness", roughness);
        SetUniformi("resolution", EngineConfig.getPrefilterMapResolution());
    }
}

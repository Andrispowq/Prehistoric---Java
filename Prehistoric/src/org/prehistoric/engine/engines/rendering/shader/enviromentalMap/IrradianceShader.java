package org.prehistoric.engine.engines.rendering.shader.enviromentalMap;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class IrradianceShader extends Shader
{
    private static IrradianceShader instance = null;
    
    public static IrradianceShader getInstance()
    {
	if(instance == null)
	    instance = new IrradianceShader();
	
	return instance;
    }
    
    protected IrradianceShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/irradiance_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/irradiance_FS.glsl"));
	
	CompileShader();

	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("environmentMap");
    }
    
    public void UpdateUniforms(Matrix4f projection, Matrix4f view, Texture texture)
    {
	SetUniform("m_view", view);
	SetUniform("m_projection", projection);
	
        texture.Bind(0);
        SetUniformi("environmentMap", 0);
    }
}

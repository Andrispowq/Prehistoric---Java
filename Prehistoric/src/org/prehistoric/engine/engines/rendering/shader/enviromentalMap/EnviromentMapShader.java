package org.prehistoric.engine.engines.rendering.shader.enviromentalMap;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class EnviromentMapShader extends Shader
{
    private static EnviromentMapShader instance = null;
    
    public static EnviromentMapShader getInstance()
    {
	if(instance == null)
	    instance = new EnviromentMapShader();
	
	return instance;
    }
    
    protected EnviromentMapShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/enviromentalMap_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/enviromentalMap/enviromentalMap_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("equirectangularMap");
    }
    
    public void UpdateUniforms(Matrix4f projection, Matrix4f view, Texture texture)
    {
	SetUniform("m_view", view);
	SetUniform("m_projection", projection);
	
        texture.Bind(0);
        SetUniformi("equirectangularMap", 0);
    }
}

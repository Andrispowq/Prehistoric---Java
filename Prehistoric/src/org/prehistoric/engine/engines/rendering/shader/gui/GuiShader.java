package org.prehistoric.engine.engines.rendering.shader.gui;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.components.renderer.Renderer2D;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class GuiShader extends Shader
{
    private static GuiShader instance = null;
    
    public static GuiShader GetInstance()
    {
	if(instance == null)
	    instance = new GuiShader();
	
	return instance;
    }
    
    protected GuiShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/gui/gui_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/gui/gui_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_transform");
	
	AddUniform("guiTexture");
    }
    
    public void UpdateUniforms(GameObject object)
    {
	Texture texture = ((Renderer2D) object.GetComponent(Consts.RENDERER_2D_COMPONENT)).getTexture();
	
	SetUniform("m_transform", object.getWorldTransform().getTransformationMatrix());
	 
	texture.Bind(0);
	SetUniformi("guiTexture", 0);
    }
    
    public void UpdateUniforms(Texture texture)
    {
	SetUniform("m_transform", new Matrix4f().Identity());
	
	texture.Bind(0);
	SetUniformi("guiTexture", 0);
    }
}

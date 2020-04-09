package org.prehistoric.engine.components.renderer;

import java.util.ArrayList;

import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class Renderer2D extends Renderable
{    
    private Texture texture;    
    
    public Renderer2D(Texture texture, VBO vbo, RenderConfig config, Shader shader)
    {
	if(vbos.contains(vbo))
	{
	    vboIndex = vbos.indexOf(vbo);
	} else
	{
	    vbos.add(vbo);
	    vboIndex = vbos.size() - 1;
	}
	
	this.texture = texture;
	this.config = config;
	this.shader = shader;
    }
    
    public Renderer2D(VBO vbo, RenderConfig config, Shader shader)
    {
	if(vbos == null)
	    vbos = new ArrayList<VBO>();
	
	if(vbos.contains(vbo))
	{
	    vboIndex = vbos.indexOf(vbo);
	} else
	{
	    vbos.add(vbo);
	    vboIndex = vbos.size() - 1;
	}

	this.config = config;
	this.shader = shader;
    }
    
    public void PreRender(Engine engine)
    {	
	engine.getRenderingEngine().AddModel(this);
    }
    
    public void Render(RenderingEngine renderingEngine)
    {	
	VBO vbo = vbos.get(vboIndex);
	
	shader.Bind();
	shader.UpdateUniforms(getParent());
	
	vbo.Bind();
	vbo.Draw();
	vbo.UnBind();
	
	shader.UnBind();
    }
    
    public void CleanUp()
    {
	vbos.get(vboIndex).Delete();
	
	shader.CleanUp();
    }
    
    public Texture getTexture()
    {
	return texture;
    }
    
    public RenderConfig getConfig()
    {
        return super.getConfig();
    }
    
    public Shader getShader()
    {
        return super.getShader();
    }
}

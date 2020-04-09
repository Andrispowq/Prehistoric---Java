package org.prehistoric.engine.components.renderer;

import java.util.ArrayList;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class Renderer extends Renderable
{
    private Material material;
    private RenderInfo renderInfo;
    
    public Renderer(Material material, VBO vbo, RenderConfig config, Shader shader, RenderInfo renderInfo)
    {	
	if(vbos.contains(vbo))
	{
	    vboIndex = vbos.indexOf(vbo);
	} else
	{
	    vbos.add(vbo);
	    vboIndex = vbos.size() - 1;
	}
	
	this.material = material;
	this.renderInfo = renderInfo;
	this.config = config;
	this.shader = shader;
    }
    
    public Renderer(Material material, VBO vbo, RenderConfig config, Shader shader)
    {	
	if(vbos.contains(vbo))
	{
	    vboIndex = vbos.indexOf(vbo);
	} else
	{
	    vbos.add(vbo);
	    vboIndex = vbos.size() - 1;
	}
	
	this.material = material;
	
	this.config = config;
	this.shader = shader;
	
	this.renderInfo = new RenderInfo(config, shader, shader);
    }
    
    public Renderer(VBO vbo, RenderConfig config, Shader shader, RenderInfo info)
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
	
	this.renderInfo = info;
	
	this.config = config;
	this.shader = shader;
	
	this.renderInfo = new RenderInfo(config, shader, shader);
    }
    
    public Renderer(VBO vbo, RenderConfig config, Shader shader)
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
	
	this.renderInfo = new RenderInfo(config, shader, shader);
    }
    
    public void PreRender(Engine engine)
    {
	engine.getRenderingEngine().AddModel(this);
    }
    
    public void Render(RenderingEngine renderingEngine)
    {	
	VBO vbo = vbos.get(vboIndex);
	
	if(renderingEngine.isWireframe())
	{
	    renderInfo.getWireframeConfig().Enable();
	    renderInfo.getWireframeShader().Bind();
	    renderInfo.getWireframeShader().UpdateUniforms(getParent(), renderingEngine.getLights(), renderingEngine.getCamera());
	} else
	{
	    config.Enable();
	    shader.Bind();
	    shader.UpdateUniforms(getParent(), renderingEngine.getLights(), renderingEngine.getCamera());
	}
        
	vbo.Bind();
	vbo.Draw();
	vbo.UnBind();
	
	if(renderingEngine.isWireframe())
	{
	    renderInfo.getWireframeConfig().Disable();
	    renderInfo.getWireframeShader().UnBind();
	} else
	{
	    config.Enable();
	    shader.UnBind();
	}
    }
    
    public void Render(RenderingEngine renderingEngine, Matrix4f view, Matrix4f projection)
    {	
	VBO vbo = vbos.get(vboIndex);
	
	if(renderingEngine.isWireframe())
	{
	    renderInfo.getWireframeConfig().Enable();
	    renderInfo.getWireframeShader().Bind();
	    renderInfo.getWireframeShader().UpdateUniforms(view, projection);
	} else
	{
	    config.Enable();
	    shader.Bind();
	    shader.UpdateUniforms(view, projection);
	}
	
	vbo.Bind();
	vbo.Draw();
	vbo.UnBind();
	
	if(renderingEngine.isWireframe())
	{
	    renderInfo.getWireframeConfig().Disable();
	    renderInfo.getWireframeShader().UnBind();
	} else
	{
	    config.Enable();
	    shader.UnBind();
	}
    }
    
    public void RenderRTX(RenderingEngine renderingEngine, Shader rtxShader)
    {
	VBO vbo = vbos.get(vboIndex);
	
	config.Enable();
	rtxShader.Bind();
	rtxShader.UpdateUniforms(getParent(), renderingEngine.getCamera());
	
	vbo.Bind();
	vbo.Draw();
	vbo.UnBind();
	
	rtxShader.UnBind();
	config.Disable();
    }
    
    public void CleanUp()
    {
	vbos.get(vboIndex).Delete();
	
	shader.CleanUp();
	renderInfo.getInstanceShader().CleanUp();
	renderInfo.getWireframeShader().CleanUp();
    }
    
    public Material getMaterial()
    {
	return material;
    }
    
    public void setMaterial(Material material)
    {
	this.material = material;
    }
    
    public RenderInfo getRenderInfo()
    {
	return renderInfo;
    }
    
    public void setRenderInfo(RenderInfo renderInfo)
    {
	this.renderInfo = renderInfo;
    }
}

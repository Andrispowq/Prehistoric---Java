package org.prehistoric.engine.engines.rendering.rendererModules;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT3;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.prehistoric.engine.components.renderer.Renderable;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.core.buffers.FBO;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.ImageFormat;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.core.model.texture.TextureUtil.TextureWrapMode;
import org.prehistoric.engine.core.util.creators.Fabricator;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.engines.rendering.shader.Shader;
import org.prehistoric.engine.engines.rendering.shader.rayTracing.RayTracePrepareShader;
import org.prehistoric.engine.engines.rendering.shader.rayTracing.ScreenSpaceRayTracingShader;

public class RayTracingRenderer
{
    //Framebuffer object and relevant textures
    private FBO scene;
    
    private Texture positionMetallic; //Position (3 floats) + metallicness (float)
    private Texture albedoRoughness; //Albedo (3 floats) + roughness (float)
    private Texture normalLit; //Normal (3 floats) + lit (boolean, 1 float)
    private Texture emission; //Emission (3 floats), HDR
    
    //Screen-size quad and shaders
    private MeshVBO screenQuad;
    private RayTracePrepareShader sceneShader;
    private ScreenSpaceRayTracingShader rayTracer;
    
    //Utils	
    private IntBuffer drawBuffers;
    
    public RayTracingRenderer()
    {
	//Setup of FBO and the textures
	scene = new FBO();
	
	positionMetallic = TextureUtil.Storage2D(FrameworkConfig.getWidth(), FrameworkConfig.getHeight(), ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
	albedoRoughness = TextureUtil.Storage2D(FrameworkConfig.getWidth(), FrameworkConfig.getHeight(), ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
	normalLit = TextureUtil.Storage2D(FrameworkConfig.getWidth(), FrameworkConfig.getHeight(), ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
	emission = TextureUtil.Storage2D(FrameworkConfig.getWidth(), FrameworkConfig.getHeight(), ImageFormat.RGBA32FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);
	
	scene.Bind();
	scene.CreateDepthAttachment(FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
	
	scene.AddColourTextureAttachment(positionMetallic.getID(), 0);
	scene.AddColourTextureAttachment(albedoRoughness.getID(), 1);
	scene.AddColourTextureAttachment(normalLit.getID(), 2);
	scene.AddColourTextureAttachment(emission.getID(), 3);
	
	scene.CheckStatus();
	scene.Unbind();
	
	//Setup of quad and shaders
	screenQuad = Fabricator.CreateMesh("res/models", "quad", null, 0);
	
	sceneShader = RayTracePrepareShader.getInstance();
	rayTracer = ScreenSpaceRayTracingShader.getInstance();
	
	drawBuffers = IntBuffer.allocate(4);
	
	drawBuffers.put(GL_COLOR_ATTACHMENT0);
	drawBuffers.put(GL_COLOR_ATTACHMENT1);
	drawBuffers.put(GL_COLOR_ATTACHMENT2);
	drawBuffers.put(GL_COLOR_ATTACHMENT3);
    }
    
    public void Render(RenderingEngine renderingEngine)
    {
	scene.SetViewport(FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
	scene.Bind();
	scene.SetDrawBuffer(GL_COLOR_ATTACHMENT0);
	
	for(VBO vbo : renderingEngine.getModels().keySet())
	{
	    vbo.Bind();
	    
	    HashMap<Shader, ArrayList<Renderable>> sr = renderingEngine.getModels().get(vbo);
	    
	    for(Shader shader : sr.keySet())
	    {
		ArrayList<Renderable> renderables = sr.get(shader);
		
		for(Renderable renderable : renderables)
		{
		    if(renderable instanceof Renderer)
		    {
			Renderer rend = (Renderer) renderable;
			rend.RenderRTX(renderingEngine, sceneShader);
		    } else
		    {
			continue;
		    }
		}
	    }
	    
	    vbo.UnBind();
	}
	
	scene.Unbind();
	scene.SetViewport(FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
	
	rayTracer.Bind();
	rayTracer.UpdateUniforms(renderingEngine.getLights());
	
	screenQuad.Bind();
	screenQuad.Draw();
	screenQuad.UnBind();
	
	rayTracer.UnBind();
    }
}

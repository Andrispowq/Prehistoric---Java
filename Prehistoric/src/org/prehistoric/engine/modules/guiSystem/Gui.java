package org.prehistoric.engine.modules.guiSystem;

import org.math.vector.Vector2f;
import org.prehistoric.engine.components.renderer.Renderer2D;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.configs.Default;
import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.creators.Fabricator;
import org.prehistoric.engine.engines.rendering.shader.gui.GuiShader;

public class Gui extends GameObject
{
    private static RenderConfig configuration;
    private static MeshVBO vbo;
    
    public Gui(Vector2f position, Vector2f scale, String texturePath)
    {
	if(configuration == null)
	    configuration = new Default();
	
	if(vbo == null)
	{
	    vbo = Fabricator.CreateMesh("res/models", "quad", null, 0);
	}
	
	Move(position.x, position.y, 0);
	Scale(scale.x, scale.y, 0);
	
	Renderer2D renderer = new Renderer2D(new Texture(texturePath), vbo, configuration, GuiShader.GetInstance());
	
	AddComponent(Consts.RENDERER_2D_COMPONENT, renderer);
    }
    
    public Gui(Vector2f position, Vector2f scale, Texture texture)
    {
	if(configuration == null)
	    configuration = new Default();
	
	if(vbo == null)
	{
	    vbo = Fabricator.CreateMesh("res/models", "quad", null, 0);
	}
	
	Move(position.x, position.y, 0);
	Scale(scale.x, scale.y, 0);
	
	Renderer2D renderer = new Renderer2D(texture, vbo, configuration, GuiShader.GetInstance());
	
	AddComponent(Consts.RENDERER_2D_COMPONENT, renderer);
    }
    
    public Gui(Vector2f position, Vector2f scale, int textureID)
    {
	if(configuration == null)
	    configuration = new Default();
	
	if(vbo == null)
	{
	    vbo = Fabricator.CreateMesh("res/models", "quad", null, 0);
	}
	
	Move(position.x, position.y, 0);
	Scale(scale.x, scale.y, 0);
	
	Renderer2D renderer = new Renderer2D(new Texture(textureID), vbo, configuration, GuiShader.GetInstance());
	
	AddComponent(Consts.RENDERER_2D_COMPONENT, renderer);
    }
}

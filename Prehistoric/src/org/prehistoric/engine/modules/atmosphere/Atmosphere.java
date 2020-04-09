package org.prehistoric.engine.modules.atmosphere;

import org.math.vector.Vector3f;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.config.AtmosphereConfig;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.configs.CCW;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.creators.Fabricator;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.rendering.shader.atmosphere.AtmosphericScatteringShader;

public class Atmosphere extends GameObject
{
    private Vector3f sun;
    
    public Atmosphere()
    {
	super();
	
	Scale(Vector3f.Get(EngineConfig.getFar_plane() / 2));
	
	MeshVBO vbo = Fabricator.CreateMesh("res/models", "sphere", null, 0);
	
	Renderer renderer = new Renderer(vbo, new CCW(), AtmosphericScatteringShader.GetInstance());
	
	AddComponent(Consts.RENDERER_COMPONENT, renderer);
	
	sun = AtmosphereConfig.getSunPosition();
    }
    
    public void PreUpdate(Engine engine)
    {
        sun = sun.Add(Vector3f.Get(0, engine.getFrameTime() / 30, engine.getFrameTime() / 60));
	AtmosphereConfig.setSunPosition(sun);
        
        super.PreUpdate(engine);
    }
    
    public AtmosphericScatteringShader getScatteringShader()
    {
	return (AtmosphericScatteringShader) ((Renderer) GetComponent(Consts.RENDERER_COMPONENT)).getShader();
    }
}

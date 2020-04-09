package org.prehistoric.engine.engines;

import org.prehistoric.engine.config.AtmosphereConfig;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.config.TerrainConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.framework.Input;
import org.prehistoric.engine.engines.physics.PhysicsEngine;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.scene.SceneCreator;

public class Engine
{
    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    
    private GameObject rootObject;
    
    private float frameTime;
    
    public Engine()
    {
	frameTime = 0;
	
	FrameworkConfig.InitFramework("res/config/framework.cfg");
	EngineConfig.InitEngine("res/config/engine.cfg");
	AtmosphereConfig.LoadAtmosphereConfig("res/config/atmosphere.cfg");
	
	renderingEngine = new RenderingEngine();
	
	TerrainConfig.LoadTerrainConfig("res/config/terrain.cfg");

	renderingEngine.Init();
	
	physicsEngine = new PhysicsEngine();
	
	Input.Init(renderingEngine.getWindow());
	
	rootObject = new GameObject();
	SceneCreator.CreateScene(rootObject, renderingEngine.getCamera());
    }
    
    public void Input(float frameTime)
    {
	this.frameTime = frameTime;
	
	Input.Update();
	
	renderingEngine.Input(this);
	rootObject.PreInput(this); 
    }
    
    public void Update()
    {
	renderingEngine.Update(this);
	physicsEngine.Update(this, frameTime); 
    }
    
    public void Render()
    {
	renderingEngine.Render(this);
    }
    
    public void CleanUp()
    {
	renderingEngine.CleanUp();
    }
    
    public RenderingEngine getRenderingEngine()
    {
	return renderingEngine;
    }
    
    public PhysicsEngine getPhysicsEngine()
    {
	return physicsEngine;
    }
    
    public GameObject getRootObject()
    {
	return rootObject;
    }
    
    public float getFrameTime()
    {
	return frameTime;
    }
}

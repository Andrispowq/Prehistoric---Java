package org.prehistoric.engine.modules.terrain;

import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.engines.Engine;

public class Terrain extends GameObject
{
    private TerrainConfig configuration;
    
    public Terrain(Camera camera)
    {
	configuration = new TerrainConfig();
	configuration.CreateMaps();
	
	AddChild(new TerrainQuadtree(configuration, camera));
    }
    
    public void UpdateQuadtree(Camera camera)
    {
	if(camera.isCameraMoved() || camera.isCameraMoved())
	{
	    ((TerrainQuadtree) getChildren().get(0)).UpdateQuadtree(camera);
	}
    }
    
    public void PreRender(Engine engine)
    {
	UpdateQuadtree(engine.getRenderingEngine().getCamera());
	
        super.PreRender(engine);
    }
}

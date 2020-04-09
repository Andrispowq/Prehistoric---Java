package org.prehistoric.engine.modules.terrain;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.Node;
import org.prehistoric.engine.core.buffers.PatchVBO;
import org.prehistoric.engine.core.movement.Camera;

public class TerrainQuadtree extends Node
{
    public static int rootNodes = 8;
    
    public TerrainQuadtree(TerrainConfig config, Camera camera)
    {
	PatchVBO buffer = new PatchVBO();
	buffer.Allocate(GeneratePatch());
	
	for(int x = 0; x < rootNodes; x++)
	{
	    for(int y = 0; y < rootNodes; y++)
	    {
		AddChild(new TerrainNode(buffer, config, new Vector2f(x / (float) rootNodes, y / (float) rootNodes), 0, new Vector2f(x, y), camera));
	    }
	}
	
	worldTransform.setScaling(new Vector3f(org.prehistoric.engine.config.TerrainConfig.getScaleXZ(), org.prehistoric.engine.config.TerrainConfig.getScaleY(), org.prehistoric.engine.config.TerrainConfig.getScaleXZ()));
	worldTransform.setPosition(new Vector3f(org.prehistoric.engine.config.TerrainConfig.getScaleXZ() / 2, 0, org.prehistoric.engine.config.TerrainConfig.getScaleXZ() / 2));
    }
    
    public void UpdateQuadtree(Camera camera)
    {
	for(Node child : children)
	{
	    ((TerrainNode) child).UpdateQuadtree(camera);
	}
    }
    
    public Vector2f[] GeneratePatch()
    {
	Vector2f[] vertices = new Vector2f[16];
	
	int index = 0;
	
	vertices[index++] = new Vector2f(0, 0);
	vertices[index++] = new Vector2f(0.333f, 0);
	vertices[index++] = new Vector2f(0.666f, 0);
	vertices[index++] = new Vector2f(1, 0);
	
	vertices[index++] = new Vector2f(0, 0.333f);
	vertices[index++] = new Vector2f(0.333f, 0.333f);
	vertices[index++] = new Vector2f(0.666f, 0.333f);
	vertices[index++] = new Vector2f(1, 0.333f);
	
	vertices[index++] = new Vector2f(0, 0.666f);
	vertices[index++] = new Vector2f(0.333f, 0.666f);
	vertices[index++] = new Vector2f(0.666f, 0.666f);
	vertices[index++] = new Vector2f(1, 0.666f);
	
	vertices[index++] = new Vector2f(0, 1);
	vertices[index++] = new Vector2f(0.333f, 1);
	vertices[index++] = new Vector2f(0.666f, 1);
	vertices[index++] = new Vector2f(1, 1);
	
	return vertices;
    }
}

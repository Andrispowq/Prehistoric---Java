package org.prehistoric.engine.modules.terrain;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.components.renderer.RenderInfo;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.Node;
import org.prehistoric.engine.core.buffers.PatchVBO;
import org.prehistoric.engine.core.configs.CCW;
import org.prehistoric.engine.core.configs.Wireframe;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.rendering.shader.terrain.TerrainShader;
import org.prehistoric.engine.engines.rendering.shader.terrain.TerrainWireframeShader;

public class TerrainNode extends GameObject
{
    private boolean isLeaf;
    private TerrainConfig config;
    private int lod;
    private Vector2f location;
    private Vector3f worldPos;
    private Vector2f index;
    private float gap;
    private PatchVBO buffer;

    public TerrainNode(PatchVBO buffer, TerrainConfig config, Vector2f location, int lod, Vector2f index, Camera camera)
    {
	this.buffer = buffer;
	this.config = config;
	this.index = index;
	this.lod = lod;
	this.location = location;
	this.gap = 1f / (TerrainQuadtree.rootNodes * (float) (Math.pow(2, lod)));

	Vector3f localScaling = new Vector3f(gap, 0, gap);
	Vector3f localPosition = new Vector3f(location.x, 0, location.y);

	localTransform.setScaling(localScaling);
	localTransform.setPosition(localPosition);

	worldTransform.setScaling(new Vector3f(org.prehistoric.engine.config.TerrainConfig.getScaleXZ(), org.prehistoric.engine.config.TerrainConfig.getScaleY(), org.prehistoric.engine.config.TerrainConfig.getScaleXZ()));
	worldTransform.setPosition(new Vector3f(-org.prehistoric.engine.config.TerrainConfig.getScaleXZ() / 2, 0, -org.prehistoric.engine.config.TerrainConfig.getScaleXZ() / 2));
	
	Renderer renderer = new Renderer(buffer, new CCW(), TerrainShader.GetInstance(), new RenderInfo(new Wireframe(), TerrainShader.GetInstance(), TerrainWireframeShader.GetInstance()));
	
	AddComponent(Consts.RENDERER_COMPONENT, renderer);
	
	ComputeWorldPosition();
	UpdateQuadtree(camera);
    }

    public void PreRender(Engine engine)
    {
	if (isLeaf)
	{
	    GetComponent(Consts.RENDERER_COMPONENT).PreRender(engine);
	}
	
	for (Node child : children)
	    child.PreRender(engine);
    }

    public void UpdateQuadtree(Camera camera)
    {
	if (camera.getPosition().y > org.prehistoric.engine.config.TerrainConfig.getScaleY())
	    worldPos.y = org.prehistoric.engine.config.TerrainConfig.getScaleY();
	else
	    worldPos.y = camera.getPosition().y;
	
	UpdateChildNodes(camera);

	for (Node child : children)
	    ((TerrainNode) child).UpdateQuadtree(camera);
    }

    private void AddChildNodes(int lod, Camera camera)
    {
	if (isLeaf)
	    isLeaf = false;

	if (children.size() == 0)
	{
	    for (int i = 0; i < 2; i++)
	    {
		for (int j = 0; j < 2; j++)
		{
		    AddChild(new TerrainNode(buffer, config, location.Add(new Vector2f(i * gap / 2f, j * gap / 2f)), lod, new Vector2f(i, j), camera));
		}
	    }
	}
    }
    
    private void RemoveChildNodes()
    {
	if(!isLeaf)
	    isLeaf = true;
	
	if(children.size() != 0)
	    children.clear();
    }
    
    private void UpdateChildNodes(Camera camera)
    {
	float distance = camera.getPosition().Sub(worldPos).Length();
	
	if(distance < org.prehistoric.engine.config.TerrainConfig.getLodRange()[lod])
	    AddChildNodes(lod + 1, camera);
	else if(distance >= org.prehistoric.engine.config.TerrainConfig.getLodRange()[lod])
	    RemoveChildNodes();
    }
    
    public void ComputeWorldPosition()
    {
	Vector2f loc = location.Add(gap / 2).Mul(org.prehistoric.engine.config.TerrainConfig.getScaleXZ()).Sub(org.prehistoric.engine.config.TerrainConfig.getScaleXZ() / 2f);
	
	worldPos = new Vector3f(loc.x, 0, loc.y);
    }

    public boolean isLeaf()
    {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf)
    {
        this.isLeaf = isLeaf;
    }

    public TerrainConfig getConfig()
    {
        return config;
    }

    public void setConfig(TerrainConfig config)
    {
        this.config = config;
    }

    public int getLod()
    {
        return lod;
    }

    public void setLod(int lod)
    {
        this.lod = lod;
    }

    public Vector2f getLocation()
    {
        return location;
    }

    public void setLocation(Vector2f location)
    {
        this.location = location;
    }

    public Vector3f getWorldPos()
    {
        return worldPos;
    }

    public void setWorldPos(Vector3f worldPos)
    {
        this.worldPos = worldPos;
    }

    public Vector2f getIndex()
    {
        return index;
    }

    public void setIndex(Vector2f index)
    {
        this.index = index;
    }

    public float getGap()
    {
        return gap;
    }

    public void setGap(float gap)
    {
        this.gap = gap;
    }

    public PatchVBO getBuffer()
    {
        return buffer;
    }

    public void setBuffer(PatchVBO buffer)
    {
        this.buffer = buffer;
    }
}

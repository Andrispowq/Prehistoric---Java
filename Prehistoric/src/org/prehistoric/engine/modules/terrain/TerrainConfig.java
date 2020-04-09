package org.prehistoric.engine.modules.terrain;

import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.modules.gpgpu.NormalMapRenderer;
import org.prehistoric.engine.modules.gpgpu.SplatMapRenderer;

public class TerrainConfig
{
    private Texture heightmap;
    private Texture normalmap;
    private Texture splatmap;

    public void CreateMaps()
    {
	heightmap = TextureUtil.Texture(org.prehistoric.engine.config.TerrainConfig.getHeightmap_location(), SamplerFilter.Trilinear);
	
	NormalMapRenderer normalMapRenderer = new NormalMapRenderer(heightmap.getWidth());
	normalMapRenderer.setStrength(12);
	normalMapRenderer.Render(heightmap);
	normalmap = normalMapRenderer.getNormalmap();
	normalMapRenderer.CleanUp();

	SplatMapRenderer splatMapRenderer = new SplatMapRenderer(normalmap.getWidth());
	splatMapRenderer.Render(normalmap);
	splatmap = splatMapRenderer.getSplatmap();
    }
    
    public Texture getHeightmap()
    {
	return heightmap;
    }
    
    public void setHeightmap(Texture heightmap)
    {
	this.heightmap = heightmap;
    }
    
    public Texture getNormalmap()
    {
	return normalmap;
    }
    
    public void setNormalmap(Texture normalmap)
    {
	this.normalmap = normalmap;
    }
    
    public Texture getSplatmap()
    {
	return splatmap;
    }
    
    public void setSplatmap(Texture splatmap)
    {
	this.splatmap = splatmap;
    }
}

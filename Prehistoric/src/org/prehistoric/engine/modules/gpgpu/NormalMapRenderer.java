package org.prehistoric.engine.modules.gpgpu;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.ImageFormat;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.engines.rendering.shader.gpgpu.NormalMapShader;

public class NormalMapRenderer
{
    private NormalMapShader shader;
    private Texture normalmap;
    private float strength;
    private int N;
    
    public NormalMapRenderer(int N)
    {
	this.N = N;
	shader = NormalMapShader.GetInstance();
	normalmap = TextureUtil.Storage2D(N, N, ImageFormat.RGBA32FLOAT, SamplerFilter.Trilinear);
    }
    
    public void Render(Texture heightmap)
    {
	shader.Bind();
	shader.UpdateUniforms(heightmap, N, strength);
	
	glBindImageTexture(0, normalmap.getID(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA32F);
	glDispatchCompute(N / 16, N / 16, 1);
	glFinish();
	
	normalmap.Bind(0);
	normalmap.Filter(SamplerFilter.Trilinear);
	
	shader.UnBind();
    }
    
    public void CleanUp()
    {
	shader.CleanUp();
    }
    
    public NormalMapShader getShader()
    {
	return shader;
    }
    
    public int getN()
    {
	return N;
    }
    
    public Texture getNormalmap()
    {
	return normalmap;
    }
    
    public float getStrength()
    {
	return strength;
    }
    
    public void setN(int n)
    {
	N = n;
    }
    
    public void setNormalmap(Texture normalmap)
    {
	this.normalmap = normalmap;
    }
    
    public void setShader(NormalMapShader shader)
    {
	this.shader = shader;
    }
    
    public void setStrength(float strength)
    {
	this.strength = strength;
    }
}

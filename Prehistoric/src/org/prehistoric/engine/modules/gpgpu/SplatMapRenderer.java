package org.prehistoric.engine.modules.gpgpu;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.ImageFormat;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.engines.rendering.shader.gpgpu.SplatMapShader;

public class SplatMapRenderer
{
    private SplatMapShader shader;
    private Texture splatmap;
    private int N;
    
    public SplatMapRenderer(int N)
    {
	this.N = N;
	shader = SplatMapShader.GetInstance();
	
	splatmap = TextureUtil.Storage2D(N, N, ImageFormat.RGBA16FLOAT, SamplerFilter.Trilinear);
    }
    
    public void Render(Texture normalmap)
    {
	shader.Bind();
	shader.UpdateUniforms(normalmap, N);
	
	glBindImageTexture(0, splatmap.getID(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
	glDispatchCompute(N / 16, N / 16, 1);
	glFinish();
	
	splatmap.Bind(0);
	splatmap.Filter(SamplerFilter.Trilinear);
	
	shader.UnBind();
    }
    
    public void CleanUp()
    {
	shader.CleanUp();
    }
    
    public int getN()
    {
	return N;
    }
    
    public SplatMapShader getShader()
    {
	return shader;
    }
    
    public Texture getSplatmap()
    {
	return splatmap;
    }
    
    public void setN(int n)
    {
	N = n;
    }
    
    public void setShader(SplatMapShader shader)
    {
	this.shader = shader;
    }
    
    public void setSplatmap(Texture splatmap)
    {
	this.splatmap = splatmap;
    }
}

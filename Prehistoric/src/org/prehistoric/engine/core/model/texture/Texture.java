package org.prehistoric.engine.core.model.texture;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import org.lwjgl.opengl.GL;
import org.prehistoric.engine.core.loaders.ImageLoader;
import org.prehistoric.engine.core.model.texture.TextureUtil.ImageType;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.core.model.texture.TextureUtil.TextureWrapMode;

public class Texture
{
    private int id;
    private int type;
    
    private int width;
    private int height;
    
    public Texture(int[] info)
    {
	this.id = info[0];
	this.width = info[1];
	this.height = info[2];
	type = GL_TEXTURE_2D;
	
	Filter(SamplerFilter.Nearest);
    }
    
    public Texture(int[] info, ImageType imageType)
    {
	this.id = info[0];
	this.width = info[1];
	this.height = info[2];
	type = getType(imageType);
	
	Filter(SamplerFilter.Nearest);
    }
    
    public Texture(int textureID)
    {
	this.id = textureID;
	this.width = -1;
	this.height = -1;
	type = GL_TEXTURE_2D;
	
	Filter(SamplerFilter.Nearest);
    }
    
    public Texture(String location)
    {
	this(ImageLoader.LoadTexture(location));
    }
    
    public Texture() {}
    
    public void Bind(int slot)
    {
	glActiveTexture(GL_TEXTURE0 + slot);
	glBindTexture(type, id);
    }
    
    public void Generate()
    {
	id = glGenTextures();
    }

    public void Delete()
    {
	glDeleteTextures(id);
    }

    public void Unbind()
    {
	glBindTexture(type, 0);
    }
    
    public void Filter(SamplerFilter filter)
    {
	if(filter == SamplerFilter.Nearest)
	{
	    glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	    glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	} else if(filter == SamplerFilter.Bilinear)
	{
	    glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	} else if(filter == SamplerFilter.Trilinear)
	{
	    glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glGenerateMipmap(type);
	    glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	} else if(filter == SamplerFilter.Anisotropic)
	{
	    glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glGenerateMipmap(type);
	    glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	    
	    if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic)
	    {
		float maxfilterLevel = glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
		glTexParameterf(type, GL_TEXTURE_MAX_ANISOTROPY_EXT, maxfilterLevel);
	    } else
	    {
		System.err.println("Anisotropic filtering not supported!");
	    }
	}
    }
    
    public void WrapMode(TextureWrapMode wrapMode)
    {
	if(wrapMode == TextureWrapMode.Repeat)
	{
	    glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_REPEAT);
	    glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_REPEAT);
	    if(type == GL_TEXTURE_CUBE_MAP) 
		glTexParameteri(type, GL_TEXTURE_WRAP_R, GL_REPEAT);
	} else if(wrapMode == TextureWrapMode.ClampToEdge)
	{
	    glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	    glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	    if(type == GL_TEXTURE_CUBE_MAP) 
		glTexParameteri(type, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
	} else if(wrapMode == TextureWrapMode.ClampToBorder)
	{
	    glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	    glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	    if(type == GL_TEXTURE_CUBE_MAP) 
		glTexParameteri(type, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_BORDER);
	} else if(wrapMode == TextureWrapMode.MirrorRepeat)
	{
	    glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	    glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	    if(type == GL_TEXTURE_CUBE_MAP) 
		glTexParameteri(type, GL_TEXTURE_WRAP_R, GL_MIRRORED_REPEAT);
	}
    }
    
    private int getType(ImageType imageType)
    {
	if(imageType == ImageType.GL_TEXTURE_2D)
	    return GL_TEXTURE_2D;
	else if(imageType == ImageType.GL_TEXTURE_CUBE_MAP)
	    return GL_TEXTURE_CUBE_MAP;
	else
	    return -1;
    }
    
    public int getType()
    {
	return type;
    }
    
    public void setType(int type)
    {
	this.type = type;
    }
    
    public int getHeight()
    {
	return height;
    }
    
    public int getID()
    {
	return id;
    }
    
    public int getWidth()
    {
	return width;
    }
    
    public void setHeight(int height)
    {
	this.height = height;
    }
    
    public void setID(int id)
    {
	this.id = id;
    }
    
    public void setWidth(int width)
    {
	this.width = width;
    }
}

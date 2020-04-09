package org.prehistoric.engine.core.model.texture;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_R;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL30.GL_R16;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RG;
import static org.lwjgl.opengl.GL30.GL_RG16;
import static org.lwjgl.opengl.GL30.GL_RG32F;
import static org.lwjgl.opengl.GL30.GL_RGB32F;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glTexStorage2D;

import java.nio.ByteBuffer;

public class TextureUtil
{
    public enum ImageFormat
    {
	RGBA32FLOAT, RGB32FLOAT, RGBA16FLOAT, DEPTH32FLOAT, R16FLOAT, R32FLOAT, R8FLOAT, RG16FLOAT, RG32FLOAT
    }

    public enum ImageType
    {
	GL_TEXTURE_2D, GL_TEXTURE_CUBE_MAP
    }

    public enum SamplerFilter
    {
	Nearest, Bilinear, Trilinear, Anisotropic
    }

    public enum TextureWrapMode
    {
	ClampToEdge, ClampToBorder, Repeat, MirrorRepeat
    }
    
    public static Texture Texture(String file, SamplerFilter filter)
    {
	Texture texture = new Texture(file);
	texture.Bind(0);
	texture.Filter(filter);
	return texture;
    }
    
    public static Texture Storage3D(int width, int height, ImageFormat format, SamplerFilter filter, TextureWrapMode wrapMode)
    {
	Texture texture = new Texture();
	texture.Generate();
	texture.setType(GL_TEXTURE_CUBE_MAP);
	texture.Bind(0);
	texture.setWidth(width);
	texture.setHeight(height);

	for(int i = 0; i < 6; ++i)
	{
	    glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GetImageFormat(format), width, height, 0, GetFormat(format), GL_FLOAT, (ByteBuffer) null);
	}
	
	texture.Filter(filter);
	texture.WrapMode(wrapMode);
	texture.Unbind();
	return texture;
    }
    
    public static Texture Storage3D(int width, int height, ImageFormat format, SamplerFilter filter)
    {
	Texture texture = new Texture();
	texture.Generate();
	texture.setType(GL_TEXTURE_CUBE_MAP);
	texture.Bind(0);
	texture.setWidth(width);
	texture.setHeight(height);

	for(int i = 0; i < 6; ++i)
	{
	    glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GetImageFormat(format), width, height, 0, GetFormat(format), GL_FLOAT, (ByteBuffer) null);
	}

	texture.Filter(filter);
	texture.Unbind();
	return texture;
    }
    
    public static Texture Storage2D(int width, int height, ImageFormat format, SamplerFilter filter, TextureWrapMode wrapMode)
    {
	Texture texture = new Texture();
	texture.Generate();
	texture.setType(GL_TEXTURE_2D);
	texture.Bind(0);
	texture.setWidth(width);
	texture.setHeight(height);
	
	glTexStorage2D(GL_TEXTURE_2D, 1, GetImageFormat(format), width, height);

	texture.Filter(filter);
	texture.WrapMode(wrapMode);
	texture.Unbind();
	return texture;
    }
    
    public static Texture Storage2D(int width, int height, ImageFormat format, SamplerFilter filter)
    {
	Texture texture = new Texture();
	texture.Generate();
	texture.setType(GL_TEXTURE_2D);
	texture.Bind(0);
	texture.setWidth(width);
	texture.setHeight(height);
	
	glTexStorage2D(GL_TEXTURE_2D, 1, GetImageFormat(format), width, height);

	texture.Filter(filter);
	texture.Unbind();
	return texture;
    }
    
    private static int GetImageFormat(ImageFormat format)
    {
	if(format == ImageFormat.R8FLOAT)
	    return GL_R8;
	if(format == ImageFormat.R16FLOAT)
	    return GL_R16;
	if(format == ImageFormat.R32FLOAT)
	    return GL_R32F;
	if(format == ImageFormat.RG16FLOAT)
	    return GL_RG16;
	if(format == ImageFormat.RG32FLOAT)
	    return GL_RG32F;
	if(format == ImageFormat.RGB32FLOAT)
	    return GL_RGB32F;
	if(format == ImageFormat.RGBA16FLOAT)
	    return GL_RGBA16F;
	if(format == ImageFormat.RGBA32FLOAT)
	    return GL_RGBA32F;

	return -1;
    }
    
    private static int GetFormat(ImageFormat format)
    {
	if(format == ImageFormat.R8FLOAT)
	    return GL_R;
	if(format == ImageFormat.R16FLOAT)
	    return GL_R;
	if(format == ImageFormat.R32FLOAT)
	    return GL_R;
	if(format == ImageFormat.RG16FLOAT)
	    return GL_RG;
	if(format == ImageFormat.RG32FLOAT)
	    return GL_RG;
	if(format == ImageFormat.RGB32FLOAT)
	    return GL_RGB;
	if(format == ImageFormat.RGBA16FLOAT)
	    return GL_RGBA;
	if(format == ImageFormat.RGBA32FLOAT)
	    return GL_RGBA;

	return -1;
    }
}

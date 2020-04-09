package org.prehistoric.engine.core.loaders;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.GL_RGB16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ImageLoader
{
    public int LoadFontTexture(String fileName) 
    {
	Texture texture = null;
	
   	try 
   	{
   	    texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + fileName + ".png"));
   	    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
   	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
   	    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
   	} catch (Exception e)
   	{
   	    e.printStackTrace();
   	    System.err.println("Tried to load texture " + fileName + ".png , didn't work");
   	    System.exit(-1);
   	}
   	
   	return texture.getTextureID();
    }
    
    public static int[] LoadTextureHDR(String file)
    {
	ByteBuffer imageBuffer;
	
	try
	{
	    imageBuffer = IoResourceToByteBuffer(file, 128 * 128);
	} catch (IOException e)
	{
	    throw new RuntimeException(e);
	}

	IntBuffer w = BufferUtils.createIntBuffer(1);
	IntBuffer h = BufferUtils.createIntBuffer(1);
	IntBuffer c = BufferUtils.createIntBuffer(1);

	// Use info to read image metadata without decoding the entire image.
	if (!stbi_info_from_memory(imageBuffer, w, h, c))
	{
	    throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
	}

	// Decode the image
	ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, c, 0);
	
	if (image == null)
	{
	    throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
	}

	int width = w.get(0);
	int height = h.get(0);
	int comp = c.get(0);

	int texId = glGenTextures();

	glBindTexture(GL_TEXTURE_2D, texId);

	if (comp == 3)
	{
	    if ((width & 3) != 0)
	    {
		glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
	    }
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
	} else
	{
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	}

	stbi_image_free(image);

	int[] data =
	{ texId, w.get(), h.get() };
	
	GL11.glFinish();

	return data;
    }
    
    public static int[] LoadTexture(String file)
    {
	ByteBuffer imageBuffer;
	
	try
	{
	    imageBuffer = IoResourceToByteBuffer(file, 128 * 128);
	} catch (IOException e)
	{
	    System.err.println("Could not load texture " + file + ", the program will crash!");
	    throw new RuntimeException(e);
	}

	IntBuffer w = BufferUtils.createIntBuffer(1);
	IntBuffer h = BufferUtils.createIntBuffer(1);
	IntBuffer c = BufferUtils.createIntBuffer(1);

	// Use info to read image metadata without decoding the entire image.
	if (!stbi_info_from_memory(imageBuffer, w, h, c))
	{
	    throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
	}

	// Decode the image
	ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, c, 0);
	
	if (image == null)
	{
	    throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
	}

	int width = w.get(0);
	int height = h.get(0);
	int comp = c.get(0);

	int texId = glGenTextures();

	glBindTexture(GL_TEXTURE_2D, texId);

	if (comp == 3)
	{
	    if ((width & 3) != 0)
	    {
		glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
	    }
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
	} else
	{
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	}

	stbi_image_free(image);

	int[] data =
	{ texId, w.get(), h.get() };
	
	GL11.glFinish();

	return data;
    }

    public static ByteBuffer LoadImageToByteBuffer(String file)
    {
	ByteBuffer imageBuffer;
	try
	{
	    imageBuffer = IoResourceToByteBuffer(file, 128 * 128);
	} catch (IOException e)
	{
	    throw new RuntimeException(e);
	}

	IntBuffer w = BufferUtils.createIntBuffer(1);
	IntBuffer h = BufferUtils.createIntBuffer(1);
	IntBuffer c = BufferUtils.createIntBuffer(1);

	// Use info to read image metadata without decoding the entire image.
	if (!stbi_info_from_memory(imageBuffer, w, h, c))
	{
	    throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
	}

	//System.out.println("Image width: " + w.get(0));
	//System.out.println("Image height: " + h.get(0));
	//System.out.println("Image components: " + c.get(0));
	//System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));

	// Decode the image
	ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, c, 0);
	if (image == null)
	{
	    throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
	}

	return image;
    }

    public static ByteBuffer IoResourceToByteBuffer(String resource, int bufferSize) throws IOException
    {
	ByteBuffer buffer;

	Path path = Paths.get(resource);
	if (Files.isReadable(path))
	{
	    try (SeekableByteChannel fc = Files.newByteChannel(path))
	    {
		buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
		while (fc.read(buffer) != -1)
		{
		    ;
		}
	    }
	} else
	{
	    System.err.println("Could not load texture " + resource + ", the program will crash!");
	    try (InputStream source = Thread.class.getClassLoader().getResourceAsStream(resource);
		    ReadableByteChannel rbc = Channels.newChannel(source))
	    {
		buffer = BufferUtils.createByteBuffer(bufferSize);

		while (true)
		{
		    int bytes = rbc.read(buffer);
		    if (bytes == -1)
		    {
			break;
		    }
		    if (buffer.remaining() == 0)
		    {
			buffer = ResizeBuffer(buffer, buffer.capacity() * 2);
		    }
		}
	    }
	}

	buffer.flip();
	return buffer;
    }

    private static ByteBuffer ResizeBuffer(ByteBuffer buffer, int newCapacity)
    {
	ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
	buffer.flip();
	newBuffer.put(buffer);
	return newBuffer;
    }
}

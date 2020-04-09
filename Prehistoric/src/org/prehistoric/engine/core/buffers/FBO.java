package org.prehistoric.engine.core.buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import java.nio.IntBuffer;

public class FBO
{
    private int id;
    
    //private int colourTexture;
    private int depthBuffer;

    public FBO()
    {
	id = glGenFramebuffers();
    }

    public void Bind()
    {
	glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void BindForWriting()
    {
	glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);
    }

    public void BindForReading()
    {
	glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
    }

    public void Unbind()
    {
	glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void UnbindForWriting()
    {
	glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public void UnbindForReading()
    {
	glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    public void SetDrawBuffer(int i)
    {
	glDrawBuffer(GL_COLOR_ATTACHMENT0 + i);
    }

    public void SetDrawBuffers(IntBuffer buffer)
    {
	glDrawBuffers(buffer);
    }
    
    public void SetViewport(int width, int height)
    {
	glViewport(0, 0, width, height);
    }
    
    public void ClearBuffer(boolean colour, boolean depth)
    {	
	if(colour && depth)
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	else if(colour)
	    glClear(GL_COLOR_BUFFER_BIT);
	else if(depth)
	    glClear(GL_DEPTH_BUFFER_BIT);
    }
    
    public void CreateDepthAttachment(int width, int height)
    {
	depthBuffer = glGenRenderbuffers();
	
	glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
	glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, width, height);
	glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
    }
    
    public void AddColourTextureAttachment(int textureID)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);
    }
    
    public void AddColourTextureAttachment(int textureID, int attachment)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachment, GL_TEXTURE_2D, textureID, 0);
    }
    
    public void AddColourTextureAttachment3D(int face, int textureID)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, textureID, 0);
    }
    
    public void AddColourTextureAttachment3D(int face, int textureID, int attachment)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachment, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, textureID, 0);
    }
    
    public void AddColourTextureAttachmentMipmap(int textureID, int level)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, level);
    }
    
    public void AddColourTextureAttachmentMipmap(int textureID, int level, int attachment)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachment, GL_TEXTURE_2D, textureID, level);
    }
    
    public void AddColourTextureAttachmentMipmap3D(int face, int textureID, int level)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, textureID, level);
    }
    
    public void AddColourTextureAttachmentMipmap3D(int face, int textureID, int level, int attachment)
    {
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachment, GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, textureID, level);
    }

    public void BlitFrameBuffer(int sourceAttachment, int destinationAttachment, int writeFBO, int width, int height)
    {
	glBindFramebuffer(GL_DRAW_FRAMEBUFFER, writeFBO);
	glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
	glReadBuffer(GL_COLOR_ATTACHMENT0 + sourceAttachment);
	glDrawBuffer(GL_COLOR_ATTACHMENT0 + destinationAttachment);
	glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT,
		GL_NEAREST);
	glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
	glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    public void CheckStatus()
    {
	if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE)
	{
	    return;
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNDEFINED)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNDEFINED error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNSUPPORTED)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNSUPPORTED error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE error");
	    System.exit(1);
	} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS)
	{
	    System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS error");
	    System.exit(1);
	}
    }
    
    public int getID()
    {
	return id;
    }
}

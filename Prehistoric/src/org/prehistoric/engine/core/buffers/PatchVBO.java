package org.prehistoric.engine.core.buffers;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL40.GL_PATCHES;
import static org.lwjgl.opengl.GL40.GL_PATCH_VERTICES;
import static org.lwjgl.opengl.GL40.glPatchParameteri;

import org.math.vector.Vector2f;
import org.prehistoric.engine.core.util.BufferUtil;

public class PatchVBO extends VBO
{
    public PatchVBO()
    {
	vboID = glGenBuffers();
	vaoID = glGenVertexArrays();
	size = 0;
    }

    public void Allocate(Vector2f[] vertices)
    {
	size = vertices.length;

	glBindVertexArray(vaoID);

	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glBufferData(GL_ARRAY_BUFFER, BufferUtil.CreateFlippedBuffer(vertices), GL_DYNAMIC_DRAW);

	glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);
	glPatchParameteri(GL_PATCH_VERTICES, size);

	glBindVertexArray(0);
    }
    
    public void Bind()
    {
	glBindVertexArray(vaoID);
	glEnableVertexAttribArray(0);
    }

    public void Draw()
    {
	glDrawArrays(GL_PATCHES, 0, size);
    }

    public void UnBind()
    {
	glDisableVertexAttribArray(0);
	glBindVertexArray(0);
    }
    
    public void Delete()
    {
	glBindVertexArray(vaoID);
	glDeleteBuffers(vboID);
	glDeleteVertexArrays(vaoID);
	glBindVertexArray(0);
    }
}

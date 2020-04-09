package org.prehistoric.engine.core.buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
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

import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.util.BufferUtil;

public class MeshVBO extends VBO
{
    private int iboID;
    
    private boolean tessellation;

    public MeshVBO()
    {
	vboID = glGenBuffers();
	iboID = glGenBuffers();
	vaoID = glGenVertexArrays();
	size = 0;
    }

    public void Allocate(Mesh mesh, boolean tessellation, int patchSize)
    {	
	size = mesh.getIndices().length;
	this.tessellation = tessellation;
	this.mesh = mesh;

	glBindVertexArray(vaoID);

	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glBufferData(GL_ARRAY_BUFFER, BufferUtil.CreateFlippedBufferAOS(mesh.getVertices()), GL_STATIC_DRAW);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.CreateFlippedBuffer(mesh.getIndices()), GL_STATIC_DRAW);
	
	if(tessellation)
	    glPatchParameteri(GL_PATCH_VERTICES, patchSize);
	
	glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 8, 0);
	glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
	glVertexAttribPointer(2, 3, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 5);

	glBindVertexArray(0);
    }
    
    public void Bind()
    {
	glBindVertexArray(vaoID);

	glEnableVertexAttribArray(0);
	glEnableVertexAttribArray(1);
	glEnableVertexAttribArray(2);
    }

    public void Draw()
    {
	glDrawElements(tessellation ? GL_PATCHES : GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
    }
    
    public void UnBind()
    {
	glDisableVertexAttribArray(0);
	glDisableVertexAttribArray(1);
	glDisableVertexAttribArray(2);

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

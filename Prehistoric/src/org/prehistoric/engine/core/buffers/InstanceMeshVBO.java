package org.prehistoric.engine.core.buffers;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
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
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.util.BufferUtil;

public class InstanceMeshVBO extends VBO
{
    private int iboID;
    private int numberOfInstances;

    public InstanceMeshVBO()
    {
	vboID = glGenBuffers();
	iboID = glGenBuffers();
	vaoID = glGenVertexArrays();
	size = 0;
    }

    public void Allocate(Mesh mesh)
    {
	size = mesh.getIndices().length;
	this.mesh = mesh;

	glBindVertexArray(vaoID);

	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glBufferData(GL_ARRAY_BUFFER, BufferUtil.CreateFlippedBufferAOS(mesh.getVertices()), GL_DYNAMIC_DRAW);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.CreateFlippedBuffer(mesh.getIndices()), GL_DYNAMIC_DRAW);

	glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 0);
	glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
	glVertexAttribPointer(2, 3, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 5);

	glBindVertexArray(0);
    }

    public void AddInstancedAttribute(int vboID, int attribute, int dataSize, int instancedDataLength, int offset)
    {
	glBindVertexArray(vaoID);
	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glVertexAttribPointer(attribute, dataSize, GL_FLOAT, false, instancedDataLength * Float.BYTES, offset * Float.BYTES);
	glVertexAttribDivisor(attribute, 1);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
    }
    
    public void Bind()
    {
	glBindVertexArray(vaoID);

	glEnableVertexAttribArray(0);
	glEnableVertexAttribArray(1);
	glEnableVertexAttribArray(2);
	glEnableVertexAttribArray(3);
	glEnableVertexAttribArray(4);
	glEnableVertexAttribArray(5);
	glEnableVertexAttribArray(6);
    }

    public void Draw()
    {
	glDrawElementsInstanced(GL_TRIANGLES, size, GL_UNSIGNED_INT, iboID, numberOfInstances);
    }
    
    public void UnBind()
    {
	glDisableVertexAttribArray(6);
	glDisableVertexAttribArray(5);
	glDisableVertexAttribArray(4);
	glDisableVertexAttribArray(3);
	glDisableVertexAttribArray(2);
	glDisableVertexAttribArray(1);
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
    
    public int getNumberOfInstances()
    {
	return numberOfInstances;
    }
    
    public void setNumberOfInstances(int numberOfInstances)
    {
	this.numberOfInstances = numberOfInstances;
    }
}

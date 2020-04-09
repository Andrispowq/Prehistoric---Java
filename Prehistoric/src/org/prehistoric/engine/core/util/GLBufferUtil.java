package org.prehistoric.engine.core.util;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class GLBufferUtil
{
    public static int CreateEmptyVBO(int floatCount)
    {
	int vboID = glGenBuffers();
	
	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glBufferData(GL_ARRAY_BUFFER, floatCount * 4, GL_DYNAMIC_DRAW);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	
	return vboID;
    }

    public static void UpdateVBO(int vboID, float[] data) 
    {
	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	glBufferData(GL_ARRAY_BUFFER, data.length * 4, GL_DYNAMIC_DRAW);
	glBufferSubData(GL_ARRAY_BUFFER, 0, data);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public static void DeleteVBO(int vboID)
    {
	glDeleteBuffers(vboID);
    }
}

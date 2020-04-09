package org.prehistoric.engine.core.util;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.math.matrix.Matrix4f;
import org.math.vector.Quaternion;
import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.model.Vertex;

public class BufferUtil
{
    public static FloatBuffer CreateFloatBuffer(int size)
    {
	return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer CreateIntBuffer(int size)
    {
	return BufferUtils.createIntBuffer(size);
    }

    public static DoubleBuffer CreateDoubleBuffer(int size)
    {
	return BufferUtils.createDoubleBuffer(size);
    }
    
    public static FloatBuffer CreateFlippedBufferAOS(List<Vertex> vertices)
    {
	FloatBuffer buffer = CreateFloatBuffer(vertices.size() * Vertex.FLOATS);

	for (int i = 0; i < vertices.size(); i++)
	{
	    buffer.put(vertices.get(i).getPosition().x);
	    buffer.put(vertices.get(i).getPosition().y);
	    buffer.put(vertices.get(i).getPosition().z);
	    buffer.put(vertices.get(i).getTextureCoord().x);
	    buffer.put(vertices.get(i).getTextureCoord().y);
	    buffer.put(vertices.get(i).getNormal().x);
	    buffer.put(vertices.get(i).getNormal().y);
	    buffer.put(vertices.get(i).getNormal().z);

	    if (vertices.get(i).getTangent() != null && vertices.get(i).getBitangent() != null)
	    {
		buffer.put(vertices.get(i).getTangent().x);
		buffer.put(vertices.get(i).getTangent().y);
		buffer.put(vertices.get(i).getTangent().z);
		buffer.put(vertices.get(i).getBitangent().x);
		buffer.put(vertices.get(i).getBitangent().y);
		buffer.put(vertices.get(i).getBitangent().z);
	    }
	}

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBufferSOA(List<Vertex> vertices)
    {
	FloatBuffer buffer = CreateFloatBuffer(vertices.size() * Vertex.FLOATS);

	for (int i = 0; i < vertices.size(); i++)
	{
	    buffer.put(vertices.get(i).getPosition().x);
	    buffer.put(vertices.get(i).getPosition().y);
	    buffer.put(vertices.get(i).getPosition().z);
	}

	for (int i = 0; i < vertices.size(); i++)
	{
	    buffer.put(vertices.get(i).getTextureCoord().x);
	    buffer.put(vertices.get(i).getTextureCoord().y);
	}

	for (int i = 0; i < vertices.size(); i++)
	{
	    buffer.put(vertices.get(i).getNormal().x);
	    buffer.put(vertices.get(i).getNormal().y);
	    buffer.put(vertices.get(i).getNormal().z);
	}

	buffer.flip();

	return buffer;
    }

    public static IntBuffer CreateFlippedBuffer(int... values)
    {
	IntBuffer buffer = CreateIntBuffer(values.length);
	buffer.put(values);
	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(float... values)
    {
	FloatBuffer buffer = CreateFloatBuffer(values.length);
	buffer.put(values);
	buffer.flip();

	return buffer;
    }

    public static DoubleBuffer CreateFlippedBuffer(double... values)
    {
	DoubleBuffer buffer = CreateDoubleBuffer(values.length);
	buffer.put(values);
	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Vector3f[] vector)
    {
	FloatBuffer buffer = CreateFloatBuffer(vector.length * Float.BYTES * 3);

	for (int i = 0; i < vector.length; i++)
	{
	    buffer.put(vector[i].x);
	    buffer.put(vector[i].y);
	    buffer.put(vector[i].z);
	}

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Quaternion[] vector)
    {
	FloatBuffer buffer = CreateFloatBuffer(vector.length * Float.BYTES * 4);

	for (int i = 0; i < vector.length; i++)
	{
	    buffer.put(vector[i].x);
	    buffer.put(vector[i].y);
	    buffer.put(vector[i].z);
	    buffer.put(vector[i].w);
	}

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Vector3f vector)
    {
	FloatBuffer buffer = CreateFloatBuffer(Float.BYTES * 3);

	buffer.put(vector.x);
	buffer.put(vector.y);
	buffer.put(vector.z);

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Quaternion vector)
    {
	FloatBuffer buffer = CreateFloatBuffer(Float.BYTES * 4);

	buffer.put(vector.x);
	buffer.put(vector.y);
	buffer.put(vector.z);
	buffer.put(vector.w);

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Vector2f[] vector)
    {
	FloatBuffer buffer = CreateFloatBuffer(vector.length * Float.BYTES * 2);

	for (int i = 0; i < vector.length; i++)
	{
	    buffer.put(vector[i].x);
	    buffer.put(vector[i].y);
	}

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Matrix4f matrix)
    {
	FloatBuffer buffer = CreateFloatBuffer(4 * 4);

	for (int i = 0; i < 4; i++)
	    for (int j = 0; j < 4; j++)
		buffer.put(matrix.Get(i, j));

	buffer.flip();

	return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Matrix4f[] matrices)
    {
	FloatBuffer buffer = CreateFloatBuffer(4 * 4 * matrices.length);

	for (Matrix4f matrix : matrices)
	{
	    for (int i = 0; i < 4; i++)
		for (int j = 0; j < 4; j++)
		    buffer.put(matrix.Get(i, j));
	}

	buffer.flip();

	return buffer;
    }
}

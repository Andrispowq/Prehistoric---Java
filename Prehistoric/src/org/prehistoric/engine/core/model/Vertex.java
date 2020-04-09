package org.prehistoric.engine.core.model;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;

public class Vertex
{
    public static final int BYTES = 14 * Float.BYTES;
    public static final int FLOATS = 14;

    private Vector3f position;
    private Vector2f textureCoord;
    private Vector3f normal;
    private Vector3f tangent;
    private Vector3f bitangent;

    public Vertex() {}
    
    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal) 
    {
	this.position = position;
	this.textureCoord = textureCoord;
	this.normal = normal;
    }
    
    public Vertex(Vector3f position, Vector2f textureCoord)
    {
	this.position = position;
	this.textureCoord = textureCoord;
	this.normal = Vector3f.Get();
    }
    
    public Vertex(Vector3f position)
    {
	this.position = position;
	this.textureCoord = Vector2f.Get();
	this.normal = Vector3f.Get();
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public Vector3f getNormal()
    {
        return normal;
    }

    public void setNormal(Vector3f normal)
    {
        this.normal = normal;
    }

    public Vector2f getTextureCoord()
    {
        return textureCoord;
    }

    public void setTextureCoord(Vector2f textureCoord)
    {
        this.textureCoord = textureCoord;
    }

    public Vector3f getTangent()
    {
        return tangent;
    }

    public void setTangent(Vector3f tangent)
    {
        this.tangent = tangent;
    }

    public Vector3f getBitangent()
    {
        return bitangent;
    }

    public void setBitangent(Vector3f bitangent)
    {
        this.bitangent = bitangent;
    }
}

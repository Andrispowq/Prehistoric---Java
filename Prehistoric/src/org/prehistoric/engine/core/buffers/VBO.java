package org.prehistoric.engine.core.buffers;

import org.prehistoric.engine.core.model.Mesh;

public class VBO
{
    protected int vaoID;
    protected int vboID;
    protected int size;
    
    protected Mesh mesh;
    
    public void Bind() {}    
    public void Draw() {}
    public void UnBind() {}
    public void Delete() {}
    
    public int getVaoID()
    {
	return vaoID;
    }
    
    public void setVaoID(int vaoID)
    {
	this.vaoID = vaoID;
    }
    
    public Mesh getMesh()
    {
	return mesh;
    }
    
    public void setMesh(Mesh mesh)
    {
	this.mesh = mesh;
    }
}
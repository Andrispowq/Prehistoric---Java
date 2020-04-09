package org.prehistoric.engine.core.model;

import java.util.ArrayList;

public class Mesh
{
    private ArrayList<Vertex> vertices;
    private int[] indices;
    private float furthestPoint;
    
    public Mesh(ArrayList<Vertex> vertices, int[] indices)
    {
	this.vertices = vertices;
	this.indices = indices;
    }
    
    public Mesh()
    {
	this.vertices = new ArrayList<Vertex>();
    }
    
    public boolean HasTangents()
    {
	for(Vertex v : vertices)
	    if(v.getTangent() == null)
		return false;
	
	return true;
    }
    
    public boolean HasBitangents()
    {
	for(Vertex v : vertices)
	    if(v.getBitangent() == null)
		return false;
	
	return true;
    }
    
    public int[] getIndices()
    {
	return indices;
    }
    
    public void setIndices(int[] indices)
    {
	this.indices = indices;
    }
    
    public ArrayList<Vertex> getVertices()
    {
	return vertices;
    }
    
    public void setVertices(ArrayList<Vertex> vertices)
    {
	this.vertices = vertices;
    }
    
    public float getFurthestPoint()
    {
	return furthestPoint;
    }
    
    public void setFurthestPoint(float furthestPoint)
    {
	this.furthestPoint = furthestPoint;
    }
}

package org.prehistoric.engine.core.loaders.obj;

import java.util.ArrayList;

import org.prehistoric.engine.core.model.Vertex;

public class Polygon
{
    private ArrayList<Vertex> vertices;
    private ArrayList<Integer> indices;
    private String material = null;

    public Polygon()
    {
	vertices = new ArrayList<Vertex>();
	indices = new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> getIndices()
    {
	return indices;
    }
    
    public void setIndices(ArrayList<Integer> indices)
    {
	this.indices = indices;
    }
    
    public String getMaterial()
    {
	return material;
    }
    
    public void setMaterial(String material)
    {
	this.material = material;
    }
    
    public ArrayList<Vertex> getVertices()
    {
	return vertices;
    }
    
    public void setVertices(ArrayList<Vertex> vertices)
    {
	this.vertices = vertices;
    }
}

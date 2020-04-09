package org.prehistoric.engine.core.model;

import org.prehistoric.engine.core.model.material.Material;

public class Model
{
    private Mesh mesh;
    private Material material;
    
    public Model(Mesh mesh, Material material)
    {
	this.mesh = mesh;
	this.material = material;
    }
    
    public Material getMaterial()
    {
	return material;
    }
    
    public void setMaterial(Material material)
    {
	this.material = material;
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

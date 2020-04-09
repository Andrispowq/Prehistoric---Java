package org.prehistoric.engine.components.light;

import org.math.vector.Vector3f;

public class Sun
{
    private Vector3f position;
    private Vector3f colour;
    private float radius;
    
    public Sun(Vector3f position, Vector3f colour, float radius)
    {
	this.position = position;
	this.colour = colour;
	this.radius = radius;
    }
    
    public Vector3f getColour()
    {
	return colour;
    }
    
    public void setColour(Vector3f colour)
    {
	this.colour = colour;
    }
    
    public Vector3f getPosition()
    {
	return position;
    }
    
    public void setPosition(Vector3f position)
    {
	this.position = position;
    }
    
    public float getRadius()
    {
	return radius;
    }
    
    public void setRadius(float radius)
    {
	this.radius = radius;
    }
}

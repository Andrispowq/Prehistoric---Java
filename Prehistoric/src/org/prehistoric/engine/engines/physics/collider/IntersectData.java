package org.prehistoric.engine.engines.physics.collider;

import org.math.vector.Vector3f;

public class IntersectData
{
    private boolean intersects;
    private Vector3f direction;
    
    public IntersectData(boolean intersects, Vector3f direction)
    {
	this.intersects = intersects;
	this.direction = direction;
    }
    
    public Vector3f getDirection()
    {
	return direction;
    }
    
    public boolean isIntersects()
    {
	return intersects;
    }
    
    public void setDirection(Vector3f direction)
    {
	this.direction = direction;
    }
    
    public void setIntersects(boolean intersects)
    {
	this.intersects = intersects;
    }
}

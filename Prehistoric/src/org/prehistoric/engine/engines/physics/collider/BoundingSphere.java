package org.prehistoric.engine.engines.physics.collider;

import org.math.vector.Vector3f;

public class BoundingSphere
{
    private Vector3f center;
    private float radius;
    
    public BoundingSphere(Vector3f center, float radius)
    {
	this.center = center;
	this.radius = radius;
    }
    
    public IntersectData Collide(BoundingSphere other)
    {
	Vector3f distanceOfCenters = other.getCenter().Sub(center);
	float dist = distanceOfCenters.Length();
	
	return new IntersectData(dist < (radius + other.getRadius()), distanceOfCenters);
    }
    
    public Vector3f getCenter()
    {
	return center;
    }
    
    public void setCenter(Vector3f center)
    {
	this.center = center;
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

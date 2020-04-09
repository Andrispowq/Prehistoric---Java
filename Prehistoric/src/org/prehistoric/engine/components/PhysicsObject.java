package org.prehistoric.engine.components;

import org.math.vector.Vector3f;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.physics.collider.BoundingSphere;

public class PhysicsObject extends Component
{
    private static final Vector3f GRAVITY = Vector3f.Get(0, -9.80665f, 0).Mul(10);
    
    private Vector3f velocity;
    private Vector3f acceleration;
    private boolean affectedByGravity;
    private float mass;
    
    private BoundingSphere collider;
    
    public PhysicsObject(boolean affectedByGravity, float mass)
    {
	this.velocity = Vector3f.Get();
	this.acceleration = Vector3f.Get();
	this.affectedByGravity = affectedByGravity;
	this.mass = mass;
	
	collider = new BoundingSphere(Vector3f.Get(), 0);
    }
    
    public void PreUpdate(Engine engine)
    {
	engine.getPhysicsEngine().AddPhysicsObject(this);
    }
    
    public void Update(float delta)
    {
	if(isAffectedByGravity())
	{
	    acceleration = acceleration.Add(GRAVITY);
	}
        
        velocity = velocity.Add(acceleration.Mul(delta));
	
        Vector3f position = getParent().getWorldTransform().getPosition();
        
        position = position.Add(velocity.Mul(delta));
        
        collider.setCenter(position);
        collider.setRadius(getParent().getWorldTransform().getScaling().x);
        
        getParent().getWorldTransform().setPosition(position);
        
        acceleration = Vector3f.Get();
    }
    
    public Vector3f getForce()
    {
	return getAcceleration().Mul(mass);
    }
    
    public Vector3f getAcceleration()
    {	
	return acceleration;
    }
    
    public void setAcceleration(Vector3f acceleration)
    {
	this.acceleration = acceleration;
    }
    
    public boolean isAffectedByGravity()
    {
	return affectedByGravity;
    }
    
    public void setAffectedByGravity(boolean affectedByGravity)
    {
	this.affectedByGravity = affectedByGravity;
    }
    
    public Vector3f getVelocity()
    {
	return velocity;
    }
    
    public void setVelocity(Vector3f velocity)
    {
	this.velocity = velocity;
    }
    
    public float getMass()
    {
	return mass;
    }
    
    public void setMass(float mass)
    {
	this.mass = mass;
    }
    
    public BoundingSphere getCollider()
    {
	return collider;
    }
    
   public void setCollider(BoundingSphere collider)
{
    this.collider = collider;
}
}

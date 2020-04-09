package org.prehistoric.engine.engines.physics;

import java.util.ArrayList;

import org.math.vector.Vector3f;
import org.prehistoric.engine.components.PhysicsObject;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.engines.physics.collider.IntersectData;

public class PhysicsEngine
{
    private static final double G = 6.67408 * Math.pow(10, -11) * 1; 
    
    private ArrayList<PhysicsObject> physicsObjects;
    
    public PhysicsEngine()
    {
	physicsObjects = new ArrayList<PhysicsObject>();
    }
    
    public void Update(Engine engine, float delta)
    {
	engine.getRootObject().PreUpdate(engine);
	
	for(int i = 0; i < physicsObjects.size(); i++)
	{
	    //Getting PhysicsObject 0
	    PhysicsObject o0 = physicsObjects.get(i);
	    
	    for(int j = i + 1; j < physicsObjects.size(); j++)
	    {
		//Getting PhysicsObject 1
		PhysicsObject o1 = physicsObjects.get(j);
		
		//getting masses
		float mass0 = o0.getMass();
		float mass1 = o1.getMass();
		
		Vector3f pos0 = o0.getParent().getWorldTransform().getPosition();
		Vector3f pos1 = o1.getParent().getWorldTransform().getPosition();
		
		Vector3f iToj = pos0.Sub(pos1);
		
		float F = 0;
		
		//applying gravitational pull by Newton (F = ((m0 * m1) / d2) * G)
		if(iToj.Length() != 0)
		    F = (float) (((mass0 * mass1) * G) / Math.pow(iToj.Length(), 2));
		
		o0.setVelocity(o0.getVelocity().Sub(iToj.Mul(F)));
		o1.setVelocity(o1.getVelocity().Sub(iToj.Invert().Mul(F)));
		
		IntersectData data = o0.getCollider().Collide(o1.getCollider());
		
		//Collision response
		if(data.isIntersects())
		{
		    Vector3f force0 = o0.getForce();
		    Vector3f force1 = o1.getForce();
		    
		    float force0s = force0.Length();
		    float force1s = force1.Length();
		    
		    Vector3f direction0 = data.getDirection().Normalize();
		    Vector3f direction1 = direction0.Reflect(o0.getAcceleration().Normalize());
		    
		    System.out.println(o0.getAcceleration());
		    
		    o0.setAcceleration(o0.getAcceleration().Reflect(direction1).Mul(force1s - force0s));
		    o1.setAcceleration(o1.getAcceleration().Reflect(direction0).Mul(force0s - force1s));
		}
		
		o0.getParent().getWorldTransform().setPosition(pos0);
		o1.getParent().getWorldTransform().setPosition(pos1);
	    }
	    
	    o0.Update(delta);
	}
	
	physicsObjects.clear();
    }
    
    public void AddPhysicsObject(PhysicsObject object)
    {
	physicsObjects.add(object);
    }
}

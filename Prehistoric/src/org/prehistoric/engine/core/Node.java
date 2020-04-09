package org.prehistoric.engine.core;

import java.util.ArrayList;

import org.math.vector.Vector3f;
import org.prehistoric.engine.core.model.Transform;
import org.prehistoric.engine.engines.Engine;

public class Node
{
    protected ArrayList<Node> children;
    protected Node parent;
    
    protected Transform worldTransform;
    protected Transform localTransform;
    
    public Node()
    {
	children = new ArrayList<Node>();
	parent = null;
	
	worldTransform = new Transform();
	localTransform = new Transform();
    }
    
    public void PreInput(Engine engine)
    {
	for(Node object : children)
	    object.PreInput(engine);
    }
    
    public void PreUpdate(Engine engine)
    {
	for(Node object : children)
	    object.PreUpdate(engine);
    }
    
    public void PreRender(Engine engine)
    {
	for(Node object : children)
	    object.PreRender(engine);
    }
    
    public void CleanUp()
    {
	for(Node object : children)
	    object.CleanUp();
    }
    
    public Node AddChild(Node object)
    {
	children.add(object);
	object.setParent(this);
	return this;
    }
    
    public Node Move(float x, float y, float z)
    {
	getWorldTransform().getPosition().x = x;
	getWorldTransform().getPosition().y = y;
	getWorldTransform().getPosition().z = z;
	
	return this;
    }
    
    public Node Move(Vector3f delta)
    {
	getWorldTransform().setPosition(delta);
	return this;
    }
    
    public Node Rotate(float x, float y, float z)
    {
	getWorldTransform().getRotation().x = x;
	getWorldTransform().getRotation().y = y;
	getWorldTransform().getRotation().z = z;
	
	return this;
    }
    
    public Node Rotate(Vector3f delta)
    {
	getWorldTransform().setRotation(delta);
	return this;
    }
    
    public Node Scale(float x, float y, float z)
    {
	getWorldTransform().getScaling().x = x;
	getWorldTransform().getScaling().y = y;
	getWorldTransform().getScaling().z = z;
	
	return this;
    }
    
    public Node Scale(Vector3f delta)
    {
	getWorldTransform().setScaling(delta);
	return this;
    }
    
    public ArrayList<Node> getChildren()
    {
	return children;
    }
    
    public Node getParent()
    {
	return parent;
    }
    
    public void setParent(Node parent)
    {
	this.parent = parent;
    }
    
    public Transform getWorldTransform()
    {
	return worldTransform;
    }
    
    public void setWorldTransform(Transform transform)
    {
	this.worldTransform = transform;
    }
    
    public Transform getLocalTransform()
    {
	return localTransform;
    }
    
    public void setLocalTransform(Transform localTransform)
    {
	this.localTransform = localTransform;
    }
}

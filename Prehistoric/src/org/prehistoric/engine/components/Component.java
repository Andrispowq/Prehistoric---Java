package org.prehistoric.engine.components;

import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.engines.Engine;

public class Component
{
    private GameObject parent;
    
    protected Component() 
    {
	if(parent == null)
	    parent = new GameObject();
    }
    
    public void PreInput(Engine engine) {}
    public void PreUpdate(Engine engine) {}
    public void PreRender(Engine engine) {}
    public void CleanUp() {}
    
    public GameObject getParent()
    {
	return parent;
    }
    
    public void setParent(GameObject parent)
    {
	this.parent = parent;
    }
}

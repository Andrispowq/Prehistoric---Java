package org.prehistoric.engine.core;

import java.util.HashMap;

import org.prehistoric.engine.components.Component;
import org.prehistoric.engine.engines.Engine;

public class GameObject extends Node
{
    private HashMap<String, Component> components;
    
    public GameObject()
    {
	components = new HashMap<String, Component>();
    }
    
    public void PreInput(Engine engine)
    {	
	for(Component component : components.values())
	    component.PreInput(engine);
	
	super.PreInput(engine);
    }
    
    public void PreUpdate(Engine engine)
    {
	for(Component component : components.values())
	    component.PreUpdate(engine);
	
	super.PreUpdate(engine);
    }
    
    public void PreRender(Engine engine)
    {
	for(Component component : components.values())
	    component.PreRender(engine);
	
	super.PreRender(engine);
    }
    
    public void CleanUp()
    {
	for(Component component : components.values())
	    component.CleanUp();
	
	super.CleanUp();
    }
    
    public GameObject AddComponent(String name, Component component)
    {
	components.put(name, component);
	component.setParent(this);
	return this;
    }
    
    public Component GetComponent(String name)
    {
	return components.get(name);
    }
}

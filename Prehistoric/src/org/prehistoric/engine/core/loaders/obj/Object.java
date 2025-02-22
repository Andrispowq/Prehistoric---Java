package org.prehistoric.engine.core.loaders.obj;

import java.util.ArrayDeque;
import java.util.Deque;

public class Object
{
    private Deque<PolygonGroup> polygonGroups;
    private String name = "";

    public Object()
    {
	polygonGroups = new ArrayDeque<PolygonGroup>();
    }
    
    public String getName()
    {
	return name;
    }
    
    public void setName(String name)
    {
	this.name = name;
    }
    
    public Deque<PolygonGroup> getPolygonGroups()
    {
	return polygonGroups;
    }
    
    public void setPolygonGroups(Deque<PolygonGroup> polygonGroups)
    {
	this.polygonGroups = polygonGroups;
    }
}

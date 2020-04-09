package org.prehistoric.engine.core.loaders.obj;

import java.util.ArrayList;
import java.util.HashMap;

public class PolygonGroup
{
    private HashMap<Integer, SmoothingGroup> smoothingGroups = new HashMap<Integer, SmoothingGroup>();
    private ArrayList<Polygon> polygons;
    private String name = "";

    public PolygonGroup()
    {
	smoothingGroups = new HashMap<Integer, SmoothingGroup>();
	polygons = new ArrayList<Polygon>();
    }
    
    public String getName()
    {
	return name;
    }
    
    public void setName(String name)
    {
	this.name = name;
    }
    
    public ArrayList<Polygon> getPolygons()
    {
	return polygons;
    }
    
    public void setPolygons(ArrayList<Polygon> polygons)
    {
	this.polygons = polygons;
    }
    
    public HashMap<Integer, SmoothingGroup> getSmoothingGroups()
    {
	return smoothingGroups;
    }
    
    public void setSmoothingGroups(HashMap<Integer, SmoothingGroup> smoothingGroups)
    {
	this.smoothingGroups = smoothingGroups;
    }
}

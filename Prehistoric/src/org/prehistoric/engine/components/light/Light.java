package org.prehistoric.engine.components.light;

import org.math.vector.Vector3f;
import org.prehistoric.engine.components.Component;
import org.prehistoric.engine.engines.Engine;

public class Light extends Component
{
    private Vector3f colour;
    private Vector3f intensity;

    public Light(Vector3f colour, Vector3f intensity)
    {
	this.colour = colour;
	this.intensity = intensity;
    }
    
    public void PreRender(Engine engine)
    {
	engine.getRenderingEngine().AddLight(this);
    }

    public Vector3f getColour()
    {
	return colour;
    }

    public void setColour(Vector3f colour)
    {
	this.colour = colour;
    }

    public Vector3f getIntensity()
    {
	return intensity;
    }

    public void setIntensity(Vector3f intensity)
    {
	this.intensity = intensity;
    }
}

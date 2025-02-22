package org.prehistoric.engine.components.light;

import org.lwjgl.util.vector.Vector3f;

public class Attenuation
{
    private float constant;
    private float linear;
    private float exponent;
    
    public Attenuation(Vector3f attenuation)
    {
	this.constant = attenuation.x;
	this.linear = attenuation.y;
	this.exponent = attenuation.z;
    }

    public Attenuation(float constant, float linear, float exponent)
    {
	this.constant = constant;
	this.linear = linear;
	this.exponent = exponent;
    }
    
    public float getConstant()
    {
	return constant;
    }
    
    public void setConstant(float constant)
    {
	this.constant = constant;
    }
    
    public float getExponent()
    {
	return exponent;
    }
    
    public void setExponent(float exponent)
    {
	this.exponent = exponent;
    }
    
    public float getLinear()
    {
	return linear;
    }
    
    public void setLinear(float linear)
    {
	this.linear = linear;
    }
}

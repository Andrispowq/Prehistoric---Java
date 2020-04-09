package org.prehistoric.engine.core.model.material;

import java.util.HashMap;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.math.vector.Vector4f;
import org.prehistoric.engine.core.model.texture.Texture;

public class Material
{
    private static final Texture DEFAULT_TEXTURE = new Texture("res/textures/default.png");
    
    private HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private HashMap<String, Vector4f> vector4s = new HashMap<String, Vector4f>();
    private HashMap<String, Vector3f> vector3s = new HashMap<String, Vector3f>();
    private HashMap<String, Vector2f> vector2s = new HashMap<String, Vector2f>();
    private HashMap<String, Float> floats = new HashMap<String, Float>();
    private HashMap<String, Integer> integers = new HashMap<String, Integer>();
    
    private String name;
    
    public Material AddTexture(String name, Texture value)
    {
	textures.put(name, value);
	return this;
    }
    
    public Material AddVector4f(String name, Vector4f value)
    {
	vector4s.put(name, value);
	return this;
    }
    
    public Material AddVector3f(String name, Vector3f value)
    {
	vector3s.put(name, value);
	return this;
    }
    
    public Material AddVector2f(String name, Vector2f value)
    {
	vector2s.put(name, value);
	return this;
    }
    
    public Material AddFloat(String name, float value)
    {
	floats.put(name, value);
	return this;
    }
    
    public Material AddInteger(String name, int value)
    {
	integers.put(name, value);
	return this;
    }
    
    public Texture GetTexture(String name)
    {
	Texture texture = textures.get(name);
	return texture == null ? DEFAULT_TEXTURE : texture;
    }
    
    public Vector4f GetVector4f(String name)
    {
	Vector4f vec = vector4s.get(name);
	return vec == null ? Vector4f.Get(-1) : vec;
    }
    
    public Vector3f GetVector3f(String name)
    {
	Vector3f vec = vector3s.get(name);
	return vec == null ? Vector3f.Get(-1) : vec;
    }
    
    public Vector2f GetVector2f(String name)
    {
	Vector2f vec = vector2s.get(name);
	return vec == null ? Vector2f.Get(-1) : vec;
    }
    
    public float GetFloat(String name)
    {
	Float val = floats.get(name);
	return val == null ? -1 : val;
    }
    
    public int GetInteger(String name)
    {
	Integer val = integers.get(name);
	return val == null ? -1 : val;
    }
    
    public static Texture getDefaultTexture()
    {
	return DEFAULT_TEXTURE;
    }
    
    public String getName()
    {
	return name;
    }
    
    public void setName(String name)
    {
	this.name = name;
    }
}

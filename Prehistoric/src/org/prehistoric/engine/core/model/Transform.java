package org.prehistoric.engine.core.model;

import org.math.matrix.Matrix4f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.movement.Camera;

public class Transform
{
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scaling;
    
    public Transform(Vector3f position, Vector3f rotation, Vector3f scaling)
    {
	this.position = position;
	this.rotation = rotation;
	this.scaling = scaling;
    }
    
    public Transform() 
    {
	position = Vector3f.Get();
	rotation = Vector3f.Get();
	scaling = Vector3f.Get(1);
    }
    
    public static Matrix4f getTransformationMatrix4f(Vector3f position, Vector3f rotation)
    {
	Matrix4f translate = new Matrix4f().Translation(position);
        Matrix4f rotate = new Matrix4f().Rotation(rotation);
        
        return translate.Mul(rotate);
    }
    
    public Matrix4f getTransformationMatrix()
    {
	Matrix4f translate = new Matrix4f().Translation(position);
        Matrix4f rotate = new Matrix4f().Rotation(rotation);
        Matrix4f scale = new Matrix4f().Scaling(scaling);
        
        return translate.Mul(scale.Mul(rotate));
    }
    
    public Matrix4f getModelViewProjectionMatrix(Camera camera)
    {
	Matrix4f viewProjection = camera.getViewProjectionMatrix();
	return viewProjection.Mul(getTransformationMatrix());
    }
    
    public Vector3f getPosition()
    {
	return position;
    }
    
    public void setPosition(Vector3f position)
    {
	this.position = position;
    }
    
    public Vector3f getRotation()
    {
	return rotation;
    }
    
    public void setRotation(Vector3f rotation)
    {
	this.rotation = rotation;
    }
    
    public Vector3f getScaling()
    {
	return scaling;
    }
    
    public void setScaling(Vector3f scaling)
    {
	this.scaling = scaling;
    }
}

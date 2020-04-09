package org.prehistoric.engine.engines.rendering.shader.atmosphere;

import java.util.ArrayList;

import org.math.matrix.Matrix4f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.config.AtmosphereConfig;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class AtmosphericScatteringShader extends Shader
{
    private static AtmosphericScatteringShader instance = null;
    
    public static AtmosphericScatteringShader GetInstance()
    {
	if(instance == null)
	    instance = new AtmosphericScatteringShader();
	
	return instance;
    }
    
    protected AtmosphericScatteringShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/atmosphere/atmosphericScattering_VS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/atmosphere/atmosphericScattering_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_transform");
	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("sunBaseColor");
	AddUniform("sunPosition");
	AddUniform("sunRadius");
	AddUniform("bloom");
	AddUniform("horizontalVerticalShift");
	AddUniform("width");
	AddUniform("height");
	AddUniform("isReflection");
    }
    
    public void UpdateUniforms(GameObject object, ArrayList<Light> lights, Camera camera)
    {	
	SetUniform("m_transform", object.getWorldTransform().getTransformationMatrix());
	SetUniform("m_view", camera.getViewMatrix());
	SetUniform("m_projection", camera.getProjectionMatrix());
	
	SetUniform("sunBaseColor", AtmosphereConfig.getSunColour());
	SetUniform("sunPosition", AtmosphereConfig.getSunPosition());
	SetUniformf("sunRadius", AtmosphereConfig.getSunRadius());
	SetUniformf("bloom", AtmosphereConfig.getBloomFactor());
	SetUniformf("horizontalVerticalShift", AtmosphereConfig.getHorizontalVerticalShift());
	SetUniformi("width", FrameworkConfig.getWidth());
	SetUniformi("height", FrameworkConfig.getHeight());
	SetUniformi("isReflection", 0);
    }
    
    public void UpdateUniforms(Matrix4f view, Matrix4f projection)
    {	
	SetUniform("m_transform", new Matrix4f().Scaling(Vector3f.Get(1)));
	SetUniform("m_view", view);
	SetUniform("m_projection", projection);

	SetUniform("sunBaseColor", AtmosphereConfig.getSunColour());
	SetUniform("sunPosition", AtmosphereConfig.getSunPosition());
	SetUniformf("sunRadius", AtmosphereConfig.getSunRadius());
	SetUniformf("bloom", AtmosphereConfig.getBloomFactor());
	SetUniformf("horizontalVerticalShift", AtmosphereConfig.getHorizontalVerticalShift());
	SetUniformi("width", FrameworkConfig.getWidth());
	SetUniformi("height", FrameworkConfig.getHeight());
	SetUniformi("isReflection", 0);
    }
}

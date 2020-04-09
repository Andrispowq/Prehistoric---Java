package org.prehistoric.engine.engines.rendering.shader.rayTracing;

import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class RayTracePrepareShader extends Shader
{
    private static RayTracePrepareShader instance = null;
    
    public static RayTracePrepareShader getInstance()
    {
	if(instance == null)
	    instance = new RayTracePrepareShader();
	
	return instance;
    }
    
    protected RayTracePrepareShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/rayTracing/rayTracingPrepare_VS.glsl"));
	AddGeometryShader(ResourceLoader.LoadShader("shaders/rayTracing/rayTracingPrepare_GS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/rayTracing/rayTracingPrepare_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_transform");
	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("material." + Consts.ALBEDO_MAP);
	AddUniform("material." + Consts.NORMAL_MAP);
	AddUniform("material." + Consts.DISPLACEMENT_MAP);
	AddUniform("material." + Consts.METALLIC_MAP);
	AddUniform("material." + Consts.ROUGHNESS_MAP);
	AddUniform("material." + Consts.EMISSION_MAP);
	
	AddUniform("material." + Consts.COLOUR);
	AddUniform("material." + "usesNormalMap");
	AddUniform("material." + Consts.HEIGHT_SCALE);
	AddUniform("material." + Consts.METALLIC);
	AddUniform("material." + Consts.ROUGHNESS);
	AddUniform("material." + Consts.EMISSION);
	
	AddUniform("cameraPosition");
	AddUniform("highDetailRange");
    }
    
    public void UpdateUniforms(GameObject object, Camera camera)
    {
        Material material = ((Renderer) object.GetComponent(Consts.RENDERER_COMPONENT)).getMaterial();
        
        SetUniform("m_transform", object.getWorldTransform().getTransformationMatrix());
        SetUniform("m_view", camera.getViewMatrix());
        SetUniform("m_projection", camera.getProjectionMatrix());
        
        material.GetTexture(Consts.ALBEDO_MAP).Bind(0);
	SetUniformi("material." + Consts.ALBEDO_MAP, 0);
	material.GetTexture(Consts.NORMAL_MAP).Bind(1);
	SetUniformi("material." + Consts.NORMAL_MAP, 1);
	material.GetTexture(Consts.DISPLACEMENT_MAP).Bind(2);
	SetUniformi("material." + Consts.DISPLACEMENT_MAP, 2);
	material.GetTexture(Consts.METALLIC_MAP).Bind(3);
	SetUniformi("material." + Consts.METALLIC_MAP, 3);
	material.GetTexture(Consts.ROUGHNESS_MAP).Bind(4);
	SetUniformi("material." + Consts.ROUGHNESS_MAP, 4);
	material.GetTexture(Consts.EMISSION_MAP).Bind(5);
	SetUniformi("material." + Consts.EMISSION_MAP, 5);
	
	SetUniform("material." + Consts.COLOUR, material.GetVector3f(Consts.COLOUR));
	SetUniformi("material." + "usesNormalMap", material.GetTexture(Consts.NORMAL_MAP).getID() != Material.getDefaultTexture().getID() ? 1 : 0);
	SetUniformi("material." + Consts.HEIGHT_SCALE, material.GetTexture(Consts.DISPLACEMENT_MAP).getID() != Material.getDefaultTexture().getID() ? 1 : 0);
	SetUniformf("material." + Consts.METALLIC, material.GetFloat(Consts.METALLIC));
	SetUniformf("material." + Consts.ROUGHNESS, material.GetFloat(Consts.ROUGHNESS));
	SetUniform("material." + Consts.EMISSION, material.GetVector3f(Consts.EMISSION));
	
	SetUniform("cameraPosition", camera.getPosition());
	SetUniformi("highDetailRange", EngineConfig.getHighDetailRange());
    }
}

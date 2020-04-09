package org.prehistoric.engine.engines.rendering.shader.general;

import java.util.ArrayList;

import org.math.vector.Vector3f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.engines.rendering.shader.Shader;
import org.prehistoric.engine.modules.enviromentalMap.DynamicEnviromentalMap;

public class InstanceShader extends Shader
{
    private static InstanceShader instance = null;
    
    public static InstanceShader GetInstance()
    {
	if(instance == null)
	    instance = new InstanceShader();
	
	return instance;
    }
    
    protected InstanceShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/general/general_iVS.glsl"));
	AddGeometryShader(ResourceLoader.LoadShader("shaders/general/general_GS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/general/general_FS.glsl"));
	
	CompileShader();
	
	AddUniform("m_view");
	AddUniform("m_projection");
	
	AddUniform("material." + Consts.ALBEDO_MAP);
	AddUniform("material." + Consts.NORMAL_MAP);
	AddUniform("material." + Consts.DISPLACEMENT_MAP);
	AddUniform("material." + Consts.METALLIC_MAP);
	AddUniform("material." + Consts.ROUGHNESS_MAP);
	AddUniform("material." + Consts.OCCLUSION_MAP);
	AddUniform("material." + Consts.EMISSION_MAP);
	
	AddUniform("material." + Consts.COLOUR);
	AddUniform("material." + "usesNormalMap");
	AddUniform("material." + Consts.HEIGHT_SCALE);
	AddUniform("material." + Consts.METALLIC);
	AddUniform("material." + Consts.ROUGHNESS);
	AddUniform("material." + Consts.OCCLUSION);
	AddUniform("material." + Consts.EMISSION);
	
	AddUniform("cameraPosition");
	AddUniform("highDetailRange");
	
	for(int i = 0; i < EngineConfig.getMax_lights(); i++)
	{
	    AddUniform("lights[" + i + "].position");
	    AddUniform("lights[" + i + "].colour");
	    AddUniform("lights[" + i + "].intensity");
	}
	
	AddUniform("irradianceMap");
	AddUniform("prefilterMap");
	AddUniform("brdfLUT");
	
	AddUniform("gamma");
    }
    
    public void UpdateUniforms(GameObject object, ArrayList<Light> lights, Camera camera)
    {
	Material material = ((Renderer) object.GetComponent("renderer")).getMaterial();
	
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
	material.GetTexture(Consts.OCCLUSION_MAP).Bind(5);
	SetUniformi("material." + Consts.OCCLUSION_MAP, 5);
	material.GetTexture(Consts.EMISSION_MAP).Bind(6);
	SetUniformi("material." + Consts.EMISSION_MAP, 6);
	
	SetUniform("material." + Consts.COLOUR, material.GetVector3f(Consts.COLOUR));
	SetUniformi("material." + "usesNormalMap", material.GetTexture(Consts.NORMAL_MAP).getID() != Material.getDefaultTexture().getID() ? 1 : 0);
	SetUniformf("material." + Consts.HEIGHT_SCALE, material.GetFloat(Consts.HEIGHT_SCALE));
	SetUniformf("material." + Consts.METALLIC, material.GetFloat(Consts.METALLIC));
	SetUniformf("material." + Consts.ROUGHNESS, material.GetFloat(Consts.ROUGHNESS));
	SetUniformf("material." + Consts.OCCLUSION, material.GetFloat(Consts.OCCLUSION));
	SetUniform("material." + Consts.EMISSION, material.GetVector3f(Consts.EMISSION));
	
	SetUniform("cameraPosition", camera.getPosition());
	SetUniformf("highDetailRange", EngineConfig.getHighDetailRange());
	
	for(int i = 0; i < EngineConfig.getMax_lights(); i++)
	{
	    if(i < lights.size())
	    {
		Light light = lights.get(i);
		
		SetUniform("lights[" + i + "].position", light.getParent().getWorldTransform().getPosition());
		SetUniform("lights[" + i + "].colour", light.getColour());
		SetUniform("lights[" + i + "].intensity", light.getIntensity());
	    } else
	    {
		SetUniform("lights[" + i + "].position", Vector3f.Get());
		SetUniform("lights[" + i + "].colour", Vector3f.Get());
		SetUniform("lights[" + i + "].intensity", Vector3f.Get());
	    }
	}
	
	DynamicEnviromentalMap.getIrradianceMap().Bind(7);
	SetUniformi("irradianceMap", 7);
	
	DynamicEnviromentalMap.getPrefilterMap().Bind(8);
	SetUniformi("prefilterMap", 8);
	
	DynamicEnviromentalMap.getBrdfMap().Bind(9);
	SetUniformi("brdfLUT", 9);

	SetUniformf("gamma", EngineConfig.getGamma());
    }
}

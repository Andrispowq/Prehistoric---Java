package org.prehistoric.engine.engines.rendering.shader.terrain;

import java.util.ArrayList;

import org.math.vector.Vector3f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.config.TerrainConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.modules.enviromentalMap.DynamicEnviromentalMap;
import org.prehistoric.engine.modules.terrain.TerrainNode;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class TerrainShader extends Shader
{
    private static TerrainShader instance = null;
    
    public static TerrainShader GetInstance()
    {
	if(instance == null)
	    instance = new TerrainShader();
	
	return instance;
    }
    
    protected TerrainShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_VS.glsl"));
	AddTessellationControlShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_TC.glsl"));
	AddTessellationEvaluationShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_TE.glsl"));
	AddGeometryShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_GS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_FS.glsl"));
	
	CompileShader();
	
	AddUniform("heightmap");
	
	AddUniform("localMatrix");
	AddUniform("worldMatrix");

	AddUniform("cameraPosition");
	AddUniform("location");
	AddUniform("index");
	AddUniform("scaleY");
	AddUniform("gap");
	AddUniform("lod");
	
	for(int i = 0; i < 8; i++)
	{
	    AddUniform("lodMorphArea[" + i + "]");
	}
	
	AddUniform("tessellationFactor");
	AddUniform("tessellationSlope");
	AddUniform("tessellationShift");
	
	AddUniform("viewProjection");
	
	AddUniform("normalmap");
	AddUniform("splatmap");
	
	for(int i = 0; i < 3; i++)
	{
	    AddUniform("materials[" + i + "]." + Consts.ALBEDO_MAP);
	    AddUniform("materials[" + i + "]." + Consts.NORMAL_MAP);
	    AddUniform("materials[" + i + "]." + Consts.DISPLACEMENT_MAP);
	    AddUniform("materials[" + i + "]." + Consts.METALLIC_MAP);
	    AddUniform("materials[" + i + "]." + Consts.ROUGHNESS_MAP);
	    AddUniform("materials[" + i + "]." + Consts.OCCLUSION_MAP);
	    
	    AddUniform("materials[" + i + "]." + Consts.HEIGHT_SCALE);
	    AddUniform("materials[" + i + "]." + Consts.HORIZONTAL_SCALE);
	    AddUniform("materials[" + i + "]." + Consts.METALLIC);
	    AddUniform("materials[" + i + "]." + Consts.ROUGHNESS);
	    AddUniform("materials[" + i + "]." + Consts.OCCLUSION);
	}
	
	for(int i = 0; i < EngineConfig.getMax_lights(); i++)
	{
	    AddUniform("lights[" + i + "].position");
	    AddUniform("lights[" + i + "].colour");
	    AddUniform("lights[" + i + "].intensity");
	}
	
	AddUniform("highDetailRange");
	AddUniform("gamma");
	
	AddUniform("irradianceMap");
	AddUniform("prefilterMap");
	AddUniform("brdfLUT");
    }
    
    public void UpdateUniforms(GameObject object, ArrayList<Light> lights, Camera camera)
    {
	TerrainNode node = (TerrainNode) object;
	
	node.getConfig().getHeightmap().Bind(0);
	SetUniformi("heightmap", 0);
	
	node.getConfig().getNormalmap().Bind(1);
	SetUniformi("normalmap", 1);
	
	node.getConfig().getSplatmap().Bind(2);
	SetUniformi("splatmap", 2);
	
	SetUniform("localMatrix", object.getLocalTransform().getTransformationMatrix());
	SetUniform("worldMatrix", object.getWorldTransform().getTransformationMatrix());
	
	SetUniform("cameraPosition", camera.getPosition());
	SetUniform("location", node.getLocation());
	SetUniform("index", node.getIndex());
	SetUniformf("scaleY", TerrainConfig.getScaleY());
	SetUniformf("gap", node.getGap());
	SetUniformi("lod", node.getLod());
	
	for(int i = 0; i < 8; i++)
	{
	    SetUniformi("lodMorphArea[" + i + "]", TerrainConfig.getLodMorphingArea()[i]);
	}
	
	SetUniformi("tessellationFactor", TerrainConfig.getTerrainTessellationFactor());
	SetUniformf("tessellationSlope", TerrainConfig.getTerrainTessellationSlope());
	SetUniformf("tessellationShift", TerrainConfig.getTerrainTessellationShift());
	
	SetUniform("viewProjection", camera.getViewProjectionMatrix());
	
	int texUnit = 3;
	
	for(int i = 0; i < 3; i++)
	{
	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.ALBEDO_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.ALBEDO_MAP, texUnit);
	    texUnit++;

	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.NORMAL_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.NORMAL_MAP, texUnit);
	    texUnit++;

	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.DISPLACEMENT_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.DISPLACEMENT_MAP, texUnit);
	    texUnit++;

	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.METALLIC_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.METALLIC_MAP, texUnit);
	    texUnit++;

	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.ROUGHNESS_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.ROUGHNESS_MAP, texUnit);
	    texUnit++;

	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.OCCLUSION_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.ROUGHNESS_MAP, texUnit);
	    texUnit++;

	    SetUniformf("materials[" + i + "]." + Consts.HEIGHT_SCALE, TerrainConfig.getMaterials().get(i).GetFloat(Consts.HEIGHT_SCALE));
	    SetUniformf("materials[" + i + "]." + Consts.HORIZONTAL_SCALE, TerrainConfig.getMaterials().get(i).GetFloat(Consts.HORIZONTAL_SCALE));
	    SetUniformf("materials[" + i + "]." + Consts.METALLIC, TerrainConfig.getMaterials().get(i).GetFloat(Consts.METALLIC));
	    SetUniformf("materials[" + i + "]." + Consts.ROUGHNESS, TerrainConfig.getMaterials().get(i).GetFloat(Consts.ROUGHNESS));
	    SetUniformf("materials[" + i + "]." + Consts.OCCLUSION, TerrainConfig.getMaterials().get(i).GetFloat(Consts.OCCLUSION));
	}
	
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

	SetUniformi("highDetailRange", EngineConfig.getHighDetailRange());
	SetUniformf("gamma", EngineConfig.getGamma());
	
	DynamicEnviromentalMap.getIrradianceMap().Bind(texUnit);
	SetUniformi("irradianceMap", texUnit);
	texUnit++;

	DynamicEnviromentalMap.getPrefilterMap().Bind(texUnit);
	SetUniformi("prefilterMap", texUnit);
	texUnit++;

	DynamicEnviromentalMap.getBrdfMap().Bind(texUnit);
	SetUniformi("brdfLUT", texUnit);
	texUnit++;
    }
}

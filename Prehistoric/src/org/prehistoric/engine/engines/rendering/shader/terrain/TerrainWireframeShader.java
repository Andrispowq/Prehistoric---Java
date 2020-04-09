package org.prehistoric.engine.engines.rendering.shader.terrain;

import java.util.ArrayList;

import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.config.TerrainConfig;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.loaders.ResourceLoader;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.modules.terrain.TerrainNode;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class TerrainWireframeShader extends Shader
{
    private static TerrainWireframeShader instance = null;
    
    public static TerrainWireframeShader GetInstance()
    {
	if(instance == null)
	    instance = new TerrainWireframeShader();
	
	return instance;
    }
    
    protected TerrainWireframeShader()
    {
	super();
	
	AddVertexShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_VS.glsl"));
	AddTessellationEvaluationShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_TE.glsl"));
	AddTessellationControlShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_TC.glsl"));
	AddGeometryShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_wGS.glsl"));
	AddFragmentShader(ResourceLoader.LoadShader("shaders/modules/terrain/terrain_wFS.glsl"));
	
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

	AddUniform("splatmap");
	
	AddUniform("highDetailRange");
	
	for(int i = 0; i < 2; i++)
	{
	    AddUniform("materials[" + i + "]." + Consts.DISPLACEMENT_MAP);
	    AddUniform("materials[" + i + "]." + Consts.HEIGHT_SCALE);
	    AddUniform("materials[" + i + "]." + Consts.HORIZONTAL_SCALE);
	}
    }
    
    public void UpdateUniforms(GameObject object, ArrayList<Light> lights, Camera camera)
    {	
	TerrainNode node = (TerrainNode) object;
	
	node.getConfig().getHeightmap().Bind(0);
	SetUniformi("heightmap", 0);
	
	node.getConfig().getSplatmap().Bind(1);
	SetUniformi("splatmap", 1);
	
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
	
	SetUniformi("highDetailRange", EngineConfig.getHighDetailRange());
	
	int texUnit = 2;
	
	for(int i = 0; i < 2; i++)
	{
	    TerrainConfig.getMaterials().get(i).GetTexture(Consts.DISPLACEMENT_MAP).Bind(texUnit);
	    SetUniformi("materials[" + i + "]." + Consts.DISPLACEMENT_MAP, texUnit);
	    texUnit++;
	    
	    SetUniformf("materials[" + i + "]." + Consts.HEIGHT_SCALE, TerrainConfig.getMaterials().get(i).GetFloat(Consts.HEIGHT_SCALE));
	    SetUniformf("materials[" + i + "]." + Consts.HORIZONTAL_SCALE, TerrainConfig.getMaterials().get(i).GetFloat(Consts.HORIZONTAL_SCALE));
	}
    }
}

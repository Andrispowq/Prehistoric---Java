package org.prehistoric.engine.core.util.creators;

import org.math.vector.Vector3f;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.loaders.objLoader .OBJLoader;
import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.core.util.Consts;

public class Fabricator
{    
    public static MeshVBO CreateMesh(String path, String obj, String mtl, int patchSize)
    {
	MeshVBO vbo = new MeshVBO();
	
	Mesh mesh = OBJLoader.loadOBJ(path, obj, mtl).getMesh();
	
	vbo.Allocate(mesh, patchSize > 2, patchSize);
	
	return vbo;
    }
    
    public static Material CreateMaterial(String file, String ext, boolean albedoMap, boolean displacementMap, boolean normalMap, boolean metallicMap, boolean roughnessMap, boolean occlusionMap, boolean emissionMap, Vector3f colour, float metallicness, float roughness, float occlusion, Vector3f emission, float heightScale)
    {
	Material material = new Material();
	
	if(albedoMap)
	    material.AddTexture(Consts.ALBEDO_MAP,  TextureUtil.Texture(file + Consts.ALBEDO_MAP_POSTFIX + "." + ext, SamplerFilter.Anisotropic));
	if(normalMap)
	    material.AddTexture(Consts.NORMAL_MAP,  TextureUtil.Texture(file + Consts.NORMAL_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(displacementMap)
	    material.AddTexture(Consts.DISPLACEMENT_MAP,  TextureUtil.Texture(file + Consts.DISPLACEMENT_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(metallicMap)
	    material.AddTexture(Consts.METALLIC_MAP,  TextureUtil.Texture(file + Consts.METALLIC_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(roughnessMap)
	    material.AddTexture(Consts.ROUGHNESS_MAP,  TextureUtil.Texture(file + Consts.ROUGHNESS_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(occlusionMap)
	    material.AddTexture(Consts.OCCLUSION_MAP,  TextureUtil.Texture(file + Consts.OCCLUSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(emissionMap)
	    material.AddTexture(Consts.EMISSION_MAP,  TextureUtil.Texture(file + Consts.EMISSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));

	material.AddVector3f(Consts.COLOUR, colour);
	material.AddFloat(Consts.METALLIC, metallicness);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddFloat(Consts.OCCLUSION, occlusion);
	material.AddVector3f(Consts.EMISSION, emission);
	material.AddFloat(Consts.HEIGHT_SCALE, heightScale);
	
	return material;
    }
    
    public static Material CreateMaterial(String file, String ext, boolean albedo, boolean metallic, boolean roughnessMap, boolean emission, float metallicness, float roughness)
    {
	Material material = new Material();
	
	if(albedo)
	    material.AddTexture(Consts.ALBEDO_MAP,  TextureUtil.Texture(file + Consts.ALBEDO_MAP_POSTFIX + "." + ext, SamplerFilter.Anisotropic));
	if(metallic)
	    material.AddTexture(Consts.METALLIC_MAP,  TextureUtil.Texture(file + Consts.METALLIC_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(roughnessMap)
	    material.AddTexture(Consts.ROUGHNESS_MAP,  TextureUtil.Texture(file + Consts.ROUGHNESS_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(emission)
	    material.AddTexture(Consts.EMISSION_MAP,  TextureUtil.Texture(file + Consts.EMISSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	
	material.AddFloat(Consts.METALLIC, metallicness);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddVector3f(Consts.EMISSION, Vector3f.Get());
	
	return material;
    }
    
    public static Material CreateMaterial(String file, String ext, boolean albedo, boolean normal, boolean metallic, boolean roughnessMap, boolean occlusionMap, boolean emission, float metallicness, float roughness, float occlusion)
    {
	Material material = new Material();
	
	if(albedo)
	    material.AddTexture(Consts.ALBEDO_MAP,  TextureUtil.Texture(file + Consts.ALBEDO_MAP_POSTFIX + "." + ext, SamplerFilter.Anisotropic));
	if(normal)
	    material.AddTexture(Consts.NORMAL_MAP,  TextureUtil.Texture(file + Consts.NORMAL_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(metallic)
	    material.AddTexture(Consts.METALLIC_MAP,  TextureUtil.Texture(file + Consts.METALLIC_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(roughnessMap)
	    material.AddTexture(Consts.ROUGHNESS_MAP,  TextureUtil.Texture(file + Consts.ROUGHNESS_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(occlusionMap)
	    material.AddTexture(Consts.OCCLUSION_MAP,  TextureUtil.Texture(file + Consts.OCCLUSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(emission)
	    material.AddTexture(Consts.EMISSION_MAP,  TextureUtil.Texture(file + Consts.EMISSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	
	material.AddFloat(Consts.METALLIC, metallicness);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddFloat(Consts.OCCLUSION, occlusion);
	material.AddVector3f(Consts.EMISSION, Vector3f.Get());
	
	return material;
    }
    
    public static Material CreateMaterial(String file, String ext, boolean albedo, boolean displacement, boolean normal, boolean metallic, boolean roughnessMap, boolean occlusionMap, boolean emission, float metallicness, float roughness, float occlusion, float heightScale)
    {
	Material material = new Material();
	
	if(albedo)
	    material.AddTexture(Consts.ALBEDO_MAP,  TextureUtil.Texture(file + Consts.ALBEDO_MAP_POSTFIX + "." + ext, SamplerFilter.Anisotropic));
	if(normal)
	    material.AddTexture(Consts.NORMAL_MAP,  TextureUtil.Texture(file + Consts.NORMAL_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(displacement)
	    material.AddTexture(Consts.DISPLACEMENT_MAP,  TextureUtil.Texture(file + Consts.DISPLACEMENT_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(metallic)
	    material.AddTexture(Consts.METALLIC_MAP,  TextureUtil.Texture(file + Consts.METALLIC_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(roughnessMap)
	    material.AddTexture(Consts.ROUGHNESS_MAP,  TextureUtil.Texture(file + Consts.ROUGHNESS_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(occlusionMap)
	    material.AddTexture(Consts.OCCLUSION_MAP,  TextureUtil.Texture(file + Consts.OCCLUSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(emission)
	    material.AddTexture(Consts.EMISSION_MAP,  TextureUtil.Texture(file + Consts.EMISSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	
	material.AddFloat(Consts.METALLIC, metallicness);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddFloat(Consts.OCCLUSION, occlusion);
	material.AddVector3f(Consts.EMISSION, Vector3f.Get());
	material.AddFloat(Consts.HEIGHT_SCALE, heightScale);
	
	return material;
    }
    
    public static Material CreateMaterial(String file, String ext, Vector3f colour, boolean metallic, boolean roughnessMap, boolean occlusionMap, boolean emission, float metallicness, float roughness, float occlusion)
    {
	Material material = new Material();
	
	if(metallic)
	    material.AddTexture(Consts.METALLIC_MAP,  TextureUtil.Texture(file + Consts.METALLIC_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(roughnessMap)
	    material.AddTexture(Consts.ROUGHNESS_MAP,  TextureUtil.Texture(file + Consts.ROUGHNESS_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(occlusionMap)
	    material.AddTexture(Consts.OCCLUSION_MAP,  TextureUtil.Texture(file + Consts.OCCLUSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	if(emission)
	    material.AddTexture(Consts.EMISSION_MAP,  TextureUtil.Texture(file + Consts.EMISSION_MAP_POSTFIX + "." + ext, SamplerFilter.Trilinear));
	
	material.AddVector3f(Consts.COLOUR, colour);
	
	material.AddFloat(Consts.METALLIC, metallicness);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddFloat(Consts.OCCLUSION, occlusion);
	material.AddVector3f(Consts.EMISSION, Vector3f.Get());
	
	return material;
    }
    
    public static Material CreateMaterial(Vector3f colour, float metallic, float roughness, float occlusion, Vector3f emission)
    {
	Material material = new Material();
	
	material.AddVector3f(Consts.COLOUR, colour);
	material.AddFloat(Consts.METALLIC, metallic);
	material.AddFloat(Consts.ROUGHNESS, roughness);
	material.AddFloat(Consts.OCCLUSION, occlusion);
	material.AddVector3f(Consts.EMISSION, emission);
	
	return material;
    }
}

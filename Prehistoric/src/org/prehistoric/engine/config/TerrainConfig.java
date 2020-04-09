package org.prehistoric.engine.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.Util;
import org.prehistoric.engine.modules.terrain.TerrainQuadtree;

public class TerrainConfig
{
    //Terrain config - size, tessellation parameters
    private static float scaleXZ;
    private static float scaleY;
    
    private static int terrainTessellationFactor;
    private static float terrainTessellationSlope;
    private static float terrainTessellationShift;
    
    private static int[] lodRange = new int[8];
    private static int[] lodMorphingArea = new int[8];
    
    //Terrain config - materials, heightmap
    private static String heightmap_location;
    private static ArrayList<Material> materials = new ArrayList<Material>();
    
    public static void LoadTerrainConfig(String file)
    {
	BufferedReader reader = null;
	
	try
	{
	    reader = new BufferedReader(new FileReader(file));
	    
	    String line;
	    
	    while((line = reader.readLine()) != null)
	    {
		String[] tokens = line.split(" ");
		tokens = Util.RemoveEmptyStrings(tokens);
		
		if(tokens.length == 0)
		{
		    continue;
		}		
		if(tokens[0].equals("scaleY"))
		{
		    scaleY = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("scaleXZ"))
		{
		    scaleXZ = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("heightmap"))
		{
		    heightmap_location = tokens[1];
		}
		if(tokens[0].equals("tessellationFactor"))
		{
		    terrainTessellationFactor = Integer.valueOf(tokens[1]);
		}
		if(tokens[0].equals("tessellationSlope"))
		{
		    terrainTessellationSlope = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("tessellationShift"))
		{
		    terrainTessellationSlope = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("#lod_ranges"))
		{
		    for(int i = 0; i < 8; i++)
		    {
			line = reader.readLine();
			tokens = line.split(" ");
			tokens = Util.RemoveEmptyStrings(tokens);
			
			if(tokens[0].equals("lod" + (i + 1) + "_range"))
			{
			    if(Integer.valueOf(tokens[1]) == 0)
			    {
				lodRange[i] = 0;
				lodMorphingArea[i] = 0;
			    } else
			    {
				SetLodRange(i, Integer.valueOf(tokens[1]));
			    }
			}
		    }
		}
		if(tokens[0].equals("material"))
		{
		    materials.add(new Material());
		}
		if(tokens[0].equals("material" + Consts.ALBEDO_MAP_POSTFIX))
		{
		    Texture diffuse = TextureUtil.Texture(tokens[1], SamplerFilter.Anisotropic);
		    materials.get(materials.size() - 1).AddTexture(Consts.ALBEDO_MAP, diffuse);
		}
		if(tokens[0].equals("material" + Consts.NORMAL_MAP_POSTFIX))
		{
		    Texture normal = TextureUtil.Texture(tokens[1], SamplerFilter.Trilinear);
		    materials.get(materials.size() - 1).AddTexture(Consts.NORMAL_MAP, normal);
		}
		if(tokens[0].equals("material" + Consts.DISPLACEMENT_MAP_POSTFIX))
		{
		    Texture heightMap = TextureUtil.Texture(tokens[1], SamplerFilter.Trilinear);
		    materials.get(materials.size() - 1).AddTexture(Consts.DISPLACEMENT_MAP, heightMap);
		}
		if(tokens[0].equals("material" + Consts.METALLIC_MAP_POSTFIX))
		{
		    Texture metallicMap = TextureUtil.Texture(tokens[1], SamplerFilter.Trilinear);
		    materials.get(materials.size() - 1).AddTexture(Consts.METALLIC_MAP, metallicMap);
		}
		if(tokens[0].equals("material" + Consts.ROUGHNESS_MAP_POSTFIX))
		{
		    Texture roughnessMap = TextureUtil.Texture(tokens[1], SamplerFilter.Trilinear);
		    materials.get(materials.size() - 1).AddTexture(Consts.ROUGHNESS_MAP, roughnessMap);
		}
		if(tokens[0].equals("material" + Consts.OCCLUSION_MAP_POSTFIX))
		{
		    Texture occlusionMap = TextureUtil.Texture(tokens[1], SamplerFilter.Trilinear);
		    materials.get(materials.size() - 1).AddTexture(Consts.OCCLUSION_MAP, occlusionMap);
		}
		if(tokens[0].equals("material" + Consts.HEIGHT_SCALE_POSTFIX))
		{
		    materials.get(materials.size() - 1).AddFloat(Consts.HEIGHT_SCALE, Float.valueOf(tokens[1]));
		}
		if(tokens[0].equals("material" + Consts.HORIZONTAL_SCALE_POSTFIX))
		{
		    materials.get(materials.size() - 1).AddFloat(Consts.HORIZONTAL_SCALE, Float.valueOf(tokens[1]));
		}
		if(tokens[0].equals("material" + Consts.METALLIC_POSTFIX))
		{
		    materials.get(materials.size() - 1).AddFloat(Consts.METALLIC, Float.valueOf(tokens[1]));
		}
		if(tokens[0].equals("material" + Consts.ROUGHNESS_POSTFIX))
		{
		    materials.get(materials.size() - 1).AddFloat(Consts.ROUGHNESS, Float.valueOf(tokens[1]));
		}
		if(tokens[0].equals("material" + Consts.OCCLUSION_POSTFIX))
		{
		    materials.get(materials.size() - 1).AddFloat(Consts.OCCLUSION, Float.valueOf(tokens[1]));
		}
	    }	 
	    
	    reader.close();
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    private static int UpdateMorphingArea(int lod)
    {
	return (int) ((scaleXZ / TerrainQuadtree.rootNodes) / Math.pow(2, lod));
    }
    
    private static void SetLodRange(int index, int lodRange)
    {
	TerrainConfig.lodRange[index] = lodRange;
	lodMorphingArea[index] = lodRange - UpdateMorphingArea(index + 1);
    }

    public static float getScaleXZ()
    {
        return scaleXZ;
    }

    public static void setScaleXZ(float scaleXZ)
    {
        TerrainConfig.scaleXZ = scaleXZ;
    }

    public static float getScaleY()
    {
        return scaleY;
    }

    public static void setScaleY(float scaleY)
    {
        TerrainConfig.scaleY = scaleY;
    }

    public static int getTerrainTessellationFactor()
    {
        return terrainTessellationFactor;
    }

    public static void setTerrainTessellationFactor(int terrainTessellationFactor)
    {
        TerrainConfig.terrainTessellationFactor = terrainTessellationFactor;
    }

    public static float getTerrainTessellationSlope()
    {
        return terrainTessellationSlope;
    }

    public static void setTerrainTessellationSlope(float terrainTessellationSlope)
    {
        TerrainConfig.terrainTessellationSlope = terrainTessellationSlope;
    }

    public static float getTerrainTessellationShift()
    {
        return terrainTessellationShift;
    }

    public static void setTerrainTessellationShift(float terrainTessellationShift)
    {
        TerrainConfig.terrainTessellationShift = terrainTessellationShift;
    }

    public static int[] getLodRange()
    {
        return lodRange;
    }

    public static void setLodRange(int[] lodRange)
    {
        TerrainConfig.lodRange = lodRange;
    }

    public static int[] getLodMorphingArea()
    {
        return lodMorphingArea;
    }

    public static void setLodMorphingArea(int[] lodMorphingArea)
    {
        TerrainConfig.lodMorphingArea = lodMorphingArea;
    }

    public static String getHeightmap_location()
    {
        return heightmap_location;
    }

    public static void setHeightmap_location(String heightmap_location)
    {
        TerrainConfig.heightmap_location = heightmap_location;
    }

    public static ArrayList<Material> getMaterials()
    {
        return materials;
    }

    public static void setMaterials(ArrayList<Material> materials)
    {
        TerrainConfig.materials = materials;
    }
}

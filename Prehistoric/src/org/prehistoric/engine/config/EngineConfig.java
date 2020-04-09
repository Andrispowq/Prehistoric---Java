package org.prehistoric.engine.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EngineConfig
{
    //Engine - rendering values
    private static float fov;
    private static float near_plane;
    private static float far_plane;
    private static boolean backface_culling;
    private static int max_instances;
    
    private static int max_framebuffer_attachments;
    
    //Engine - lighting parameters
    private static int max_lights;
    private static float gamma;
    private static int highDetailRange;
    private static int tessellationFactor;
    private static float tessellationSlope;
    private static float tessellationShift;
    private static boolean rayTracingEnable;
    private static int rayTracingSamples;
    
    //Engine - enviromental map parameters
    private static String enviromentalMapLocation;
    private static int enviromentalMapResolution;
    private static int diffuseIrradianceMapResolution;
    private static int prefilterMapResolution;
    private static int maxMipmapLevels;
    
    public static void InitEngine(String file)
    {
	BufferedReader reader;
	
	try
	{
	    reader = new BufferedReader(new FileReader(file));
	    
	    String line;
	    
	    while((line = reader.readLine()) != null)
	    {
		String[] tokens = line.split(" ");

		if(tokens[0].equals("fov"))
		{
		    fov = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("near_plane"))
		{
		    near_plane = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("far_plane"))
		{
		    far_plane = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("backface_culling"))
		{
		    backface_culling = Integer.parseInt(tokens[1]) != 0;
		}
		if(tokens[0].equals("max_instances"))
		{
		    max_instances = Integer.parseInt(tokens[1]);
		}

		if(tokens[0].equals("max_framebuffer_attachments"))
		{
		    max_framebuffer_attachments = Integer.parseInt(tokens[1]);
		}

		if(tokens[0].equals("max_lights"))
		{
		    max_lights = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("gamma"))
		{
		    gamma = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("highDetailRange"))
		{
		    highDetailRange = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("tessellationFactor"))
		{
		    tessellationFactor = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("tessellationSlope"))
		{
		    tessellationSlope = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("tessellationShift"))
		{
		    tessellationShift = Float.parseFloat(tokens[1]);
		}
		if(tokens[0].equals("rayTracingEnable"))
		{
		    rayTracingEnable = Integer.parseInt(tokens[1]) != 0;
		}
		if(tokens[0].equals("rayTracingSamples"))
		{
		    rayTracingSamples = Integer.parseInt(tokens[1]);
		}
		
		if(tokens[0].equals("enviromentalMapLocation"))
		{
		    enviromentalMapLocation = tokens[1];
		}
		if(tokens[0].equals("enviromentalMapResolution"))
		{
		    enviromentalMapResolution = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("diffuseIrradianceMapResolution"))
		{
		    diffuseIrradianceMapResolution = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("prefilterMapResolution"))
		{
		    prefilterMapResolution = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("maxMipmapLevels"))
		{
		    maxMipmapLevels = Integer.parseInt(tokens[1]);
		}
	    }
	    
	    reader.close();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    public static float getFov()
    {
        return fov;
    }

    public static void setFov(float fov)
    {
        EngineConfig.fov = fov;
    }

    public static float getNear_plane()
    {
        return near_plane;
    }

    public static void setNear_plane(float near_plane)
    {
        EngineConfig.near_plane = near_plane;
    }

    public static float getFar_plane()
    {
        return far_plane;
    }

    public static void setFar_plane(float far_plane)
    {
        EngineConfig.far_plane = far_plane;
    }

    public static boolean isBackface_culling()
    {
        return backface_culling;
    }

    public static void setBackface_culling(boolean backface_culling)
    {
        EngineConfig.backface_culling = backface_culling;
    }

    public static int getMax_instances()
    {
        return max_instances;
    }

    public static void setMax_instances(int max_instances)
    {
        EngineConfig.max_instances = max_instances;
    }

    public static int getMax_framebuffer_attachments()
    {
        return max_framebuffer_attachments;
    }

    public static void setMax_framebuffer_attachments(int max_framebuffer_attachments)
    {
        EngineConfig.max_framebuffer_attachments = max_framebuffer_attachments;
    }

    public static int getMax_lights()
    {
        return max_lights;
    }

    public static void setMax_lights(int max_lights)
    {
        EngineConfig.max_lights = max_lights;
    }

    public static float getGamma()
    {
        return gamma;
    }

    public static void setGamma(float gamma)
    {
        EngineConfig.gamma = gamma;
    }

    public static int getHighDetailRange()
    {
        return highDetailRange;
    }

    public static void setHighDetailRange(int highDetailRange)
    {
        EngineConfig.highDetailRange = highDetailRange;
    }

    public static int getTessellationFactor()
    {
        return tessellationFactor;
    }

    public static void setTessellationFactor(int tessellationFactor)
    {
        EngineConfig.tessellationFactor = tessellationFactor;
    }

    public static float getTessellationSlope()
    {
        return tessellationSlope;
    }

    public static void setTessellationSlope(float tessellationSlope)
    {
        EngineConfig.tessellationSlope = tessellationSlope;
    }

    public static float getTessellationShift()
    {
        return tessellationShift;
    }

    public static void setTessellationShift(float tessellationShift)
    {
        EngineConfig.tessellationShift = tessellationShift;
    }

    public static boolean isRayTracingEnable()
    {
        return rayTracingEnable;
    }

    public static void setRayTracingEnable(boolean rayTracingEnable)
    {
        EngineConfig.rayTracingEnable = rayTracingEnable;
    }

    public static int getRayTracingSamples()
    {
        return rayTracingSamples;
    }

    public static void setRayTracingSamples(int rayTracingSamples)
    {
        EngineConfig.rayTracingSamples = rayTracingSamples;
    }

    public static String getEnviromentalMapLocation()
    {
        return enviromentalMapLocation;
    }

    public static void setEnviromentalMapLocation(String enviromentalMapLocation)
    {
        EngineConfig.enviromentalMapLocation = enviromentalMapLocation;
    }

    public static int getEnviromentalMapResolution()
    {
        return enviromentalMapResolution;
    }

    public static void setEnviromentalMapResolution(int enviromentalMapResolution)
    {
        EngineConfig.enviromentalMapResolution = enviromentalMapResolution;
    }

    public static int getDiffuseIrradianceMapResolution()
    {
        return diffuseIrradianceMapResolution;
    }

    public static void setDiffuseIrradianceMapResolution(int diffuseIrradianceMapResolution)
    {
        EngineConfig.diffuseIrradianceMapResolution = diffuseIrradianceMapResolution;
    }

    public static int getPrefilterMapResolution()
    {
        return prefilterMapResolution;
    }

    public static void setPrefilterMapResolution(int prefilterMapResolution)
    {
        EngineConfig.prefilterMapResolution = prefilterMapResolution;
    }

    public static int getMaxMipmapLevels()
    {
        return maxMipmapLevels;
    }

    public static void setMaxMipmapLevels(int maxMipmapLevels)
    {
        EngineConfig.maxMipmapLevels = maxMipmapLevels;
    }
}

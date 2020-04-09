package org.prehistoric.engine.config;

import java.io.BufferedReader;
import java.io.FileReader;

import org.math.vector.Vector3f;
import org.prehistoric.engine.core.util.Util;

public class AtmosphereConfig
{
    //Atmosphere config - sun, fog, atmosphere values
    private static float sunRadius;
    private static Vector3f sunPosition;
    private static Vector3f sunColour;
    private static float sunIntensity;

    private static Vector3f fogColour;
    private static float fogBrightness;
    private static float sightRange;

    private static float ambient;
    private static float horizontalVerticalShift;
    private static float bloomFactor;
    private static boolean enableScattering;
    
    public static void LoadAtmosphereConfig(String file)
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
		
		if(tokens[0].equals("sun.radius"))
		{
		    sunRadius = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("sun.position"))
		{
		    sunPosition = Vector3f.Get(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
		}
		if(tokens[0].equals("sun.color"))
		{
		    sunColour = Vector3f.Get(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
		}
		if(tokens[0].equals("sun.intensity"))
		{
		    sunIntensity = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("sightRange"))
		{
		    sightRange = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("fog.color"))
		{
		    fogColour = Vector3f.Get(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
		}
		if(tokens[0].equals("fog.brightness"))
		{
		    fogBrightness = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("ambient"))
		{
		    ambient = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("horizon.verticalShift"))
		{
		    horizontalVerticalShift = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("atmosphere.bloom.factor"))
		{
		    bloomFactor = Float.valueOf(tokens[1]);
		}
		if(tokens[0].equals("atmosphere.scattering.enable"))
		{
		    enableScattering = Boolean.valueOf(tokens[1]);
		}
	    }
	    
	    reader.close();
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    public static float getSunRadius()
    {
        return sunRadius;
    }

    public static void setSunRadius(float sunRadius)
    {
        AtmosphereConfig.sunRadius = sunRadius;
    }

    public static Vector3f getSunPosition()
    {
        return sunPosition;
    }

    public static void setSunPosition(Vector3f sunPosition)
    {
        AtmosphereConfig.sunPosition = sunPosition;
    }

    public static Vector3f getSunColour()
    {
        return sunColour;
    }

    public static void setSunColour(Vector3f sunColour)
    {
        AtmosphereConfig.sunColour = sunColour;
    }

    public static float getSunIntensity()
    {
        return sunIntensity;
    }

    public static void setSunIntensity(float sunIntensity)
    {
        AtmosphereConfig.sunIntensity = sunIntensity;
    }

    public static Vector3f getFogColour()
    {
        return fogColour;
    }

    public static void setFogColour(Vector3f fogColour)
    {
        AtmosphereConfig.fogColour = fogColour;
    }

    public static float getFogBrightness()
    {
        return fogBrightness;
    }

    public static void setFogBrightness(float fogBrightness)
    {
        AtmosphereConfig.fogBrightness = fogBrightness;
    }

    public static float getSightRange()
    {
        return sightRange;
    }

    public static void setSightRange(float sightRange)
    {
        AtmosphereConfig.sightRange = sightRange;
    }

    public static float getAmbient()
    {
        return ambient;
    }

    public static void setAmbient(float ambient)
    {
        AtmosphereConfig.ambient = ambient;
    }

    public static float getHorizontalVerticalShift()
    {
        return horizontalVerticalShift;
    }

    public static void setHorizontalVerticalShift(float horizontalVerticalShift)
    {
        AtmosphereConfig.horizontalVerticalShift = horizontalVerticalShift;
    }

    public static float getBloomFactor()
    {
        return bloomFactor;
    }

    public static void setBloomFactor(float bloomFactor)
    {
        AtmosphereConfig.bloomFactor = bloomFactor;
    }

    public static boolean isEnableScattering()
    {
        return enableScattering;
    }

    public static void setEnableScattering(boolean enableScattering)
    {
        AtmosphereConfig.enableScattering = enableScattering;
    }
}

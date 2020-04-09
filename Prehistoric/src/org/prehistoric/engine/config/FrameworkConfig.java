package org.prehistoric.engine.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.math.vector.Vector3f;

public class FrameworkConfig
{
    //Framework - window config
    private static int width;
    private static int height;
    private static boolean fullscreen;
    private static String title = "";
    private static int multisample_samples;
    
    //Framework - opengl values
    private static Vector3f ogl_version;
    
    //Framework - coreengine values
    private static int max_fps;

    public static void InitFramework(String file)
    {
	BufferedReader reader;
	
	try
	{
	    reader = new BufferedReader(new FileReader(file));
	    
	    String line;
	    
	    while((line = reader.readLine()) != null)
	    {
		String[] tokens = line.split(" ");

		if(tokens[0].equals("width"))
		{
		    width = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("height"))
		{
		    height = Integer.parseInt(tokens[1]);
		}
		if(tokens[0].equals("fullscreen"))
		{
		    fullscreen = Integer.parseInt(tokens[1]) != 0;
		}
		if(tokens[0].equals("title"))
		{
		    for(int i = 1; i < tokens.length; i++)
		    {
			title += tokens[i] + " ";
		    }
		}
		if(tokens[0].equals("multisample_samples"))
		{
		    multisample_samples = Integer.parseInt(tokens[1]);
		}

		if(tokens[0].equals("ogl_version"))
		{
		    ogl_version = Vector3f.Get(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}

		if(tokens[0].equals("max_fps"))
		{
		    max_fps = Integer.parseInt(tokens[1]);
		}
	    }
	    
	    reader.close();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    public static int getWidth()
    {
        return width;
    }

    public static void setWidth(int width)
    {
        FrameworkConfig.width = width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static void setHeight(int height)
    {
        FrameworkConfig.height = height;
    }

    public static boolean isFullscreen()
    {
        return fullscreen;
    }

    public static void setFullscreen(boolean fullscreen)
    {
        FrameworkConfig.fullscreen = fullscreen;
    }

    public static String getTitle()
    {
        return title;
    }

    public static void setTitle(String title)
    {
        FrameworkConfig.title = title;
    }

    public static int getMultisample_samples()
    {
        return multisample_samples;
    }

    public static void setMultisample_samples(int multisample_samples)
    {
        FrameworkConfig.multisample_samples = multisample_samples;
    }

    public static Vector3f getOgl_version()
    {
        return ogl_version;
    }

    public static void setOgl_version(Vector3f ogl_version)
    {
        FrameworkConfig.ogl_version = ogl_version;
    }

    public static int getMax_fps()
    {
        return max_fps;
    }

    public static void setMax_fps(int max_fps)
    {
        FrameworkConfig.max_fps = max_fps;
    }
}

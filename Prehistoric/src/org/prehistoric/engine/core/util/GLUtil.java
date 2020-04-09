package org.prehistoric.engine.core.util;

import static org.lwjgl.opengl.GL11.*;

public class GLUtil
{
    public static void DepthTest(boolean enable)
    {
	if(enable)
	    glEnable(GL_DEPTH_TEST);
	else
	    glDisable(GL_DEPTH_TEST);
    }

    public static void Blending(boolean enable)
    {
	if(enable)
	    glEnable(GL_BLEND);
	else
	    glDisable(GL_BLEND);
    }
    
    public static void LequalDepthFunction(boolean enable)
    {
	if(enable)
	    glDepthFunc(GL_LEQUAL);
	else
	    glDepthFunc(GL_LESS);
    }
    
    public static void CullBackFace(boolean enable)
    {
	if(enable)
	{
	    glEnable(GL_CULL_FACE);
	    glCullFace(GL_BACK);
	} else
	{
	    glDisable(GL_CULL_FACE);
	}
    }
}

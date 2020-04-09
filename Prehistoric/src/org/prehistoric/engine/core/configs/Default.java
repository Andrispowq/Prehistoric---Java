package org.prehistoric.engine.core.configs;

import static org.lwjgl.opengl.GL11.*;

public class Default implements RenderConfig
{
    public void Enable() {}

    public void Disable() {}

    public static void Init()
    {
	glFrontFace(GL_CW);
	glEnable(GL_CULL_FACE);
	glCullFace(GL_BACK);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_TEXTURE_2D);
	//glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public static void ClearScreen()
    {
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	glClearDepth(1.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}

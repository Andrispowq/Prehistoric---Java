package org.prehistoric.engine.core.configs;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.glFrontFace;

public class CCW implements RenderConfig
{
    public void Enable()
    {
	glFrontFace(GL_CCW);
    }

    public void Disable()
    {
	glFrontFace(GL_CW);
    }
}
